package app.adapters.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.adapters.owner.entity.OwnerEntity;
import app.adapters.owner.repository.OwnerRepository;
import app.adapters.pet.entity.PetEntity;
import app.adapters.pet.repository.PetRepository;
import app.domain.models.Person;
import app.domain.models.Pet;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OwnerAdapter {
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private PetRepository petRepository;
    
    public boolean existsOwner(Long document){
        return ownerRepository.existsByDocument(document);        
    }

    public void savePet(Pet pet) throws Exception {
        log.info("Buscando owner con documento: {}", pet.getOwnersId());
        OwnerEntity owner = ownerRepository.findByDocument(pet.getOwnersId());
        log.info("Resultado de búsqueda de owner: {}", owner);
        if (owner == null) {
            throw new Exception("ID de dueño inválido");
        }
        PetEntity petEntity = new PetEntity(pet);
        petEntity.setOwnersId(owner);
        petRepository.save(petEntity);
    }
    
    public void saveOwner(Person person){
        log.info("Guardando owner: {}", person);
        ownerRepository.save(new OwnerEntity(person));
        log.info("Owner guardado en la base de datos");
    }
    
    public Person findByDocument(Long document){
        OwnerEntity entity = ownerRepository.findByDocument(document);
        return entity != null ? entity.toDomain() : null;
    }
    
    
    public List<Person> findAllOwners() {
        return ownerRepository.findAll()
                .stream()
                .map(OwnerEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    
    public void deleteByDocument(Long document) {
        log.info("Eliminando owner con documento: {}", document);
        ownerRepository.deleteByDocument(document);
    }
}

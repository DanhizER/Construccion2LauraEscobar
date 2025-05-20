package app.adapters.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.adapters.owner.entity.OwnerEntity;
import app.adapters.owner.repository.OwnerRepository;
import app.domain.models.Person;

import java.util.List;

@Component
public class OwnerAdapter {
    @Autowired
    private OwnerRepository ownerRepository;
    
    @Override
    public boolean existsOwner(Long document){
        return ownerRepository.existsByDocument(document);        
    }
    
    @Override
    public void saveOwner(Person person){
        person.setRole(Role.USER);
        ownerRepository.save(new OwnerEntity(person));
    }
    
    @Override
    public Person findByDocument(Long document){
        return ownerRepository.findByDocument(document)
                .map(OwnerEntity::toDomain)
                .orElse(null);
    }
    
    @Override
    public List<Person> findAllOwners() {
        return ownerRepository.findAllOwners()
                .stream()
                .map(OwnerEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    private Person adapterOwner(OwnerEntity ownerEntity) {
        if (ownerEntity == null) {
            return null;
        }
        
        Person person = new Person();
        person.setDocument(ownerEntity.getDocument());
        person.setName(ownerEntity.getName());
        person.setAge(ownerEntity.getAge());
        person.setRole(Role.USER);
        return person;
    }

    @Override
    public void deleteByDocument(Long document) {
        OwnerEntity ownerEntity = ownerRepository.findByDocument(document);
        if (ownerEntity != null) {
            ownerRepository.deleteById(ownerEntity);
        }
    }
}

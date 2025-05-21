package app.adapters.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.adapters.owner.entity.OwnerEntity;
import app.adapters.owner.repository.OwnerRepository;
import app.domain.models.Person;
import app.domain.types.Role;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OwnerAdapter {
    @Autowired
    private OwnerRepository ownerRepository;
    
    public boolean existsOwner(Long document){
        return ownerRepository.existsByDocument(document);        
    }
    
    public void saveOwner(Person person){
        person.setRole(Role.USER);
        ownerRepository.save(new OwnerEntity(person));
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
        ownerRepository.deleteById(document);
    }
}

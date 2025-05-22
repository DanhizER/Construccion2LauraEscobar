package app.adapters.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.adapters.person.entity.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long>{
	
	boolean existsByDocument(Long document);
	PersonEntity findByDocument(Long document);
}

package app.adapters.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.adapters.person.entity.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long>{
	void deleteByDocument(Long document);
	boolean existsByDocument(Long document);
	PersonEntity findByDocument(Long document);
}

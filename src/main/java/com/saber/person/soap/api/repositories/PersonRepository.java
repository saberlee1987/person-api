package com.saber.person.soap.api.repositories;

import com.saber.person.soap.api.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PersonRepository extends JpaRepository<PersonEntity,Integer> {
    Optional<PersonEntity> findByNationalCode(String nationalCode);
}

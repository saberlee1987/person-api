package com.saber.person.soap.api.services.impl;

import com.saber.person.soap.api.dto.PersonDto;
import com.saber.person.soap.api.dto.PersonResponse;
import com.saber.person.soap.api.dto.ResponseDto;
import com.saber.person.soap.api.entity.PersonEntity;
import com.saber.person.soap.api.exceptions.ResourceDuplicationException;
import com.saber.person.soap.api.exceptions.ResourceNotFoundException;
import com.saber.person.soap.api.repositories.PersonRepository;
import com.saber.person.soap.api.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    @Transactional
    public ResponseDto<PersonEntity> addPerson(PersonDto dto) {
        if (this.personRepository.findByNationalCode(dto.getNationalCode()).isPresent()) {
            throw new ResourceDuplicationException(String.format("Person with nationalCode %s exist"
                    , dto.getNationalCode()));
        }
        PersonEntity entity = creatEntity(dto);
        return new ResponseDto<>(this.personRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<PersonResponse> findAll() {
        List<PersonEntity> persons = this.personRepository.findAll();
        PersonResponse personResponse = new PersonResponse();
        personResponse.setPersons(persons);
        return  new ResponseDto<>(personResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<PersonEntity> findByNationalCode(String nationalCode) {
        Optional<PersonEntity> optionalPersonEntity = this.personRepository.findByNationalCode(nationalCode);
        if (optionalPersonEntity.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Person with nationalCode %s does not exist"
                    , nationalCode));
        }
        return new ResponseDto<>(optionalPersonEntity.get());
    }

    private PersonEntity creatEntity(PersonDto dto) {
        PersonEntity entity = new PersonEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setNationalCode(dto.getNationalCode());
        entity.setAge(dto.getAge());
        return entity;
    }
}

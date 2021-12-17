package com.saber.person.soap.api.services;

import com.saber.person.soap.api.dto.DeletePersonDto;
import com.saber.person.soap.api.dto.PersonDto;
import com.saber.person.soap.api.dto.PersonResponse;
import com.saber.person.soap.api.entity.PersonEntity;

public interface PersonService {
    PersonEntity addPerson(PersonDto dto);
   PersonResponse findAll();
    PersonEntity findByNationalCode(String nationalCode);
    PersonEntity updatePersonByNationalCode(String nationalCode,PersonDto dto);
    DeletePersonDto deletePersonByNationalCode(String nationalCode);
}

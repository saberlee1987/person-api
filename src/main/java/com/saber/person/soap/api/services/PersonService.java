package com.saber.person.soap.api.services;

import com.saber.person.soap.api.dto.PersonDto;
import com.saber.person.soap.api.dto.PersonResponse;
import com.saber.person.soap.api.dto.ResponseDto;
import com.saber.person.soap.api.entity.PersonEntity;

public interface PersonService {
    ResponseDto<PersonEntity> addPerson(PersonDto dto);
    ResponseDto<PersonResponse> findAll();
    ResponseDto<PersonEntity> findByNationalCode(String nationalCode);
}

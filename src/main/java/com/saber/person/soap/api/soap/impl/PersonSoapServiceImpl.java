package com.saber.person.soap.api.soap.impl;

import com.saber.person.soap.api.dto.*;
import com.saber.person.soap.api.entity.PersonEntity;
import com.saber.person.soap.api.services.PersonService;
import com.saber.person.soap.api.soap.PersonSoapService;
import com.saber.person.soap.api.soap.dto.PersonAllResponseDto;
import com.saber.person.soap.api.soap.dto.PersonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PersonSoapServiceImpl implements PersonSoapService {

    private final PersonService personService;
    private final SpringValidatorAdapter validatorAdapter;

    @Override
    public PersonResponseDto addPerson(com.saber.person.soap.api.soap.dto.PersonDto dto) {

        Set<ConstraintViolation<com.saber.person.soap.api.soap.dto.PersonDto>> errorValidation = validatorAdapter.validate(dto);
        if (errorValidation.size() > 0) {
            return new PersonResponseDto(getErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(),
                     getValidation(errorValidation)));
        } else {
            PersonDto personDto = createDto(dto);
            return new PersonResponseDto(createSoapEntity(this.personService.addPerson(personDto).getResponse()));
        }
    }

    @Override
    public PersonResponseDto findByNationalCode(String nationalCode) {
        ResponseDto<PersonEntity> responseDto = new ResponseDto<>();
        if (nationalCode == null || nationalCode.trim().length() < 10 || !nationalCode.trim().matches("\\d+")) {
            return  new PersonResponseDto(getErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString()
                    ,  getValidation("nationalCode", "nationalCode invalid")));
        }
        return new PersonResponseDto(createSoapEntity(this.personService.findByNationalCode(nationalCode).getResponse()));
}

    @Override
    public PersonAllResponseDto findAll() {
        return new PersonAllResponseDto(createPersons(this.personService.findAll().getResponse().getPersons()));
    }

    private List<com.saber.person.soap.api.soap.dto.ValidationDto> getValidation(Set<ConstraintViolation<com.saber.person.soap.api.soap.dto.PersonDto>> errorValidation) {
        List<com.saber.person.soap.api.soap.dto.ValidationDto> validations = new ArrayList<>();
        for (ConstraintViolation<com.saber.person.soap.api.soap.dto.PersonDto> v : errorValidation) {
            com.saber.person.soap.api.soap.dto.ValidationDto validationDto = new com.saber.person.soap.api.soap.dto.ValidationDto();
            validationDto.setFieldName(v.getPropertyPath().toString());
            validationDto.setDetailMessage(v.getMessage());
            validations.add(validationDto);
        }
        return validations;
    }

    private List<com.saber.person.soap.api.soap.dto.ValidationDto> getValidation(String fieldName, String message) {
        List<com.saber.person.soap.api.soap.dto.ValidationDto> validations = new ArrayList<>();
        com.saber.person.soap.api.soap.dto.ValidationDto validationDto = new com.saber.person.soap.api.soap.dto.ValidationDto();
        validationDto.setFieldName(fieldName);
        validationDto.setDetailMessage(message);
        validations.add(validationDto);
        return validations;
    }

    private com.saber.person.soap.api.soap.dto.ErrorResponse getErrorResponse(Integer code, String message, List<com.saber.person.soap.api.soap.dto.ValidationDto> validationDtoList) {
        com.saber.person.soap.api.soap.dto.ErrorResponse  errorResponse = new com.saber.person.soap.api.soap.dto.ErrorResponse ();
        errorResponse.setCode(code);
        errorResponse.setMessage(message);
        errorResponse.setValidations(validationDtoList);
        return errorResponse;
    }

    private PersonDto createDto(com.saber.person.soap.api.soap.dto.PersonDto personDto){
        PersonDto dto = new PersonDto();
        dto.setFirstName(personDto.getFirstName());
        dto.setLastName(personDto.getLastName());
        dto.setNationalCode(personDto.getNationalCode());
        dto.setEmail(personDto.getEmail());
        dto.setAge(personDto.getAge());
        return dto;
    }

    private com.saber.person.soap.api.soap.dto.PersonEntity createSoapEntity(PersonEntity entity){
        com.saber.person.soap.api.soap.dto.PersonEntity  personEntity = new com.saber.person.soap.api.soap.dto.PersonEntity ();
        personEntity.setId(entity.getId());
        personEntity.setFirstName(entity.getFirstName());
        personEntity.setLastName(entity.getLastName());
        personEntity.setNationalCode(entity.getNationalCode());
        personEntity.setEmail(entity.getEmail());
        personEntity.setAge(entity.getAge());
        return personEntity;
    }

    private List<com.saber.person.soap.api.soap.dto.PersonEntity> createPersons(List<PersonEntity> entities){
        List<com.saber.person.soap.api.soap.dto.PersonEntity> persons = new ArrayList<>();
        for (PersonEntity entity : entities) {
            persons.add(createSoapEntity(entity));
        }
        return persons;
    }
}

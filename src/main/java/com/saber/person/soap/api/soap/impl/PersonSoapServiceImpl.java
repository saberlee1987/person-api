package com.saber.person.soap.api.soap.impl;

import com.saber.person.soap.api.dto.PersonDto;
import com.saber.person.soap.api.dto.ResponseDto;
import com.saber.person.soap.api.entity.PersonEntity;
import com.saber.person.soap.api.services.PersonService;
import com.saber.person.soap.api.soap.PersonSoapService;
import com.saber.person.soap.api.soap.dto.*;
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
    public PersonSoapResponseDto addPerson(PersonSoapDto dto) {

        Set<ConstraintViolation<PersonSoapDto>> errorValidation = validatorAdapter.validate(dto);
        if (errorValidation.size() > 0) {
            return new PersonSoapResponseDto(getErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(),
                     getValidation(errorValidation)));
        } else {
            PersonDto personDto = creatRestDto(dto);
            return new PersonSoapResponseDto(createSoapEntity(this.personService.addPerson(personDto).getResponse()));
        }
    }

    @Override
    public PersonSoapResponseDto findByNationalCode(String nationalCode) {
        ResponseDto<PersonSoapEntity> responseDto = new ResponseDto<>();
        if (nationalCode == null || nationalCode.trim().length() < 10 || !nationalCode.trim().matches("\\d+")) {
            return  new PersonSoapResponseDto(getErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString()
                    ,  getValidation("nationalCode", "nationalCode invalid")));
        }
        return new PersonSoapResponseDto(createSoapEntity(this.personService.findByNationalCode(nationalCode).getResponse()));
}

    @Override
    public PersonAllResponseDto findAll() {
        return new PersonAllResponseDto(createPersons(this.personService.findAll().getResponse().getPersons()));
    }

    private List<ValidationSoapDto> getValidation(Set<ConstraintViolation<PersonSoapDto>> errorValidation) {
        List<ValidationSoapDto> validations = new ArrayList<>();
        for (ConstraintViolation<PersonSoapDto> v : errorValidation) {
            ValidationSoapDto validationSoapDto = new ValidationSoapDto();
            validationSoapDto.setFieldName(v.getPropertyPath().toString());
            validationSoapDto.setDetailMessage(v.getMessage());
            validations.add(validationSoapDto);
        }
        return validations;
    }

    private List<ValidationSoapDto> getValidation(String fieldName, String message) {
        List<ValidationSoapDto> validations = new ArrayList<>();
        ValidationSoapDto validationSoapDto = new ValidationSoapDto();
        validationSoapDto.setFieldName(fieldName);
        validationSoapDto.setDetailMessage(message);
        validations.add(validationSoapDto);
        return validations;
    }

    private ErrorSoapResponse getErrorResponse(Integer code, String message, List<ValidationSoapDto> validationSoapDtoList) {
        ErrorSoapResponse errorSoapResponse = new ErrorSoapResponse();
        errorSoapResponse.setCode(code);
        errorSoapResponse.setMessage(message);
        errorSoapResponse.setValidations(validationSoapDtoList);
        return errorSoapResponse;
    }

    private  PersonDto creatRestDto(PersonSoapDto personSoapDto){
        PersonDto dto = new PersonDto();
        dto.setFirstName(personSoapDto.getFirstName());
        dto.setLastName(personSoapDto.getLastName());
        dto.setNationalCode(personSoapDto.getNationalCode());
        dto.setEmail(personSoapDto.getEmail());
        dto.setAge(personSoapDto.getAge());
        return dto;
    }

    private PersonSoapEntity createSoapEntity(PersonEntity entity){
        PersonSoapEntity personSoapEntity = new PersonSoapEntity();
        personSoapEntity.setId(entity.getId());
        personSoapEntity.setFirstName(entity.getFirstName());
        personSoapEntity.setLastName(entity.getLastName());
        personSoapEntity.setNationalCode(entity.getNationalCode());
        personSoapEntity.setEmail(entity.getEmail());
        personSoapEntity.setAge(entity.getAge());
        return personSoapEntity;
    }

    private List<PersonSoapEntity> createPersons(List<PersonEntity> entities){
        List<PersonSoapEntity> persons = new ArrayList<>();
        for (PersonEntity entity : entities) {
            persons.add(createSoapEntity(entity));
        }
        return persons;
    }
}

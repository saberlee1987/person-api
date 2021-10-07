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
    public PersonResponseDto addPerson(PersonDto dto) {

        Set<ConstraintViolation<PersonDto>> errorValidation = validatorAdapter.validate(dto);
        if (errorValidation.size() > 0) {
            return new PersonResponseDto(getErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(),
                    null, getValidation(errorValidation)));
        } else {
            try {
                return new PersonResponseDto(this.personService.addPerson(dto).getResponse());
            } catch (Exception exception) {
               return new PersonResponseDto(getErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.toString(), exception.getMessage(), null));
            }
        }
    }

    @Override
    public PersonResponseDto findByNationalCode(String nationalCode) {
        ResponseDto<PersonEntity> responseDto = new ResponseDto<>();
        if (nationalCode == null || nationalCode.trim().length() < 10 || !nationalCode.trim().matches("\\d+")) {
            return  new PersonResponseDto(getErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString()
                    , null, getValidation("nationalCode", "nationalCode invalid")));
        }
        try {
            return new PersonResponseDto(this.personService.findByNationalCode(nationalCode).getResponse());
        } catch (Exception ex) {
            return  new PersonResponseDto((getErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(),
                    HttpStatus.NOT_ACCEPTABLE.toString(), ex.getMessage(), null)));
        }
    }

    @Override
    public PersonAllResponseDto findAll() {
        return new PersonAllResponseDto(this.personService.findAll().getResponse().getPersons());
    }

    private List<ValidationDto> getValidation(Set<ConstraintViolation<PersonDto>> errorValidation) {
        List<ValidationDto> validations = new ArrayList<>();
        for (ConstraintViolation<PersonDto> v : errorValidation) {
            ValidationDto validationDto = new ValidationDto();
            validationDto.setFieldName(v.getPropertyPath().toString());
            validationDto.setDetailMessage(v.getMessage());
            validations.add(validationDto);
        }
        return validations;
    }

    private List<ValidationDto> getValidation(String fieldName, String message) {
        List<ValidationDto> validations = new ArrayList<>();
        ValidationDto validationDto = new ValidationDto();
        validationDto.setFieldName(fieldName);
        validationDto.setDetailMessage(message);
        validations.add(validationDto);
        return validations;
    }

    private ErrorResponse getErrorResponse(Integer code, String message, String originalMessage, List<ValidationDto> validationDtoList) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(code);
        errorResponse.setMessage(message);
        errorResponse.setOriginalMessage(originalMessage);
        errorResponse.setValidations(validationDtoList);
        return errorResponse;
    }
}

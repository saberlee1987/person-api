package com.saber.person.soap.api.soap.dto;

import com.saber.person.soap.api.dto.ErrorResponse;
import com.saber.person.soap.api.entity.PersonEntity;
import lombok.Data;

import java.util.List;

@Data
public class PersonAllResponseDto {
    private List<PersonEntity> persons;
    private ErrorResponse error;

    public PersonAllResponseDto() {
    }

    public PersonAllResponseDto(List<PersonEntity> persons) {
        this.persons = persons;
    }

    public PersonAllResponseDto(ErrorResponse error) {
        this.error = error;
    }

    public PersonAllResponseDto(List<PersonEntity> persons, ErrorResponse error) {
        this.persons = persons;
        this.error = error;
    }
}

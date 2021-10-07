package com.saber.person.soap.api.soap.dto;

import com.saber.person.soap.api.dto.ErrorResponse;
import com.saber.person.soap.api.entity.PersonEntity;
import lombok.Data;

@Data
public class PersonResponseDto {
    private PersonEntity response;
    private ErrorResponse error;

    public PersonResponseDto() {
    }

    public PersonResponseDto(PersonEntity response) {
        this.response = response;
    }

    public PersonResponseDto(PersonEntity response, ErrorResponse error) {
        this.response = response;
        this.error = error;
    }

    public PersonResponseDto(ErrorResponse error) {
        this.error = error;
    }
}

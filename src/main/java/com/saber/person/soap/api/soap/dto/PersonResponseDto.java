package com.saber.person.soap.api.soap.dto;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonResponseDto",propOrder = {
        "response",
        "error"
})
@Data
public class PersonResponseDto {
    private PersonEntity response;
    private ErrorResponse  error;

    public PersonResponseDto() {
    }

    public PersonResponseDto(PersonEntity response) {
        this.response = response;
    }

    public PersonResponseDto(PersonEntity response, ErrorResponse  error) {
        this.response = response;
        this.error = error;
    }

    public PersonResponseDto(ErrorResponse error) {
        this.error = error;
    }
}

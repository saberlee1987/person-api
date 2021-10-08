package com.saber.person.soap.api.soap.dto;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonSoapResponseDto",propOrder = {
        "response",
        "error"
})
@Data
public class PersonSoapResponseDto {
    private PersonSoapEntity response;
    private ErrorSoapResponse error;

    public PersonSoapResponseDto() {
    }

    public PersonSoapResponseDto(PersonSoapEntity response) {
        this.response = response;
    }

    public PersonSoapResponseDto(PersonSoapEntity response, ErrorSoapResponse error) {
        this.response = response;
        this.error = error;
    }

    public PersonSoapResponseDto(ErrorSoapResponse error) {
        this.error = error;
    }
}

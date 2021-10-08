package com.saber.person.soap.api.soap.dto;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonAllResponseDto",propOrder = {
        "persons",
        "error"
})
public class PersonAllResponseDto {
    private List<PersonSoapEntity> persons;
    private ErrorSoapResponse error;

    public PersonAllResponseDto() {
    }

    public PersonAllResponseDto(List<PersonSoapEntity> persons) {
        this.persons = persons;
    }

    public PersonAllResponseDto(ErrorSoapResponse error) {
        this.error = error;
    }

    public PersonAllResponseDto(List<PersonSoapEntity> persons, ErrorSoapResponse error) {
        this.persons = persons;
        this.error = error;
    }
}

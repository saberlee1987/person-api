package com.saber.person.soap.api.soap.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonSoapResponse",propOrder = {
        "error",
        "person",
        "persons",
        "deleteSoapPersonDto",
})
@Data
public class PersonSoapResponse {
    private ErrorSoapResponse error;
    private PersonSoapEntity person;
    private List<PersonSoapEntity> persons;
    private DeleteSoapPersonDto deleteSoapPersonDto;

    public PersonSoapResponse() {
    }

    public PersonSoapResponse(ErrorSoapResponse error) {
        this.error = error;
    }

    public PersonSoapResponse(PersonSoapEntity person) {
        this.person = person;
    }

    public PersonSoapResponse(List<PersonSoapEntity> persons) {
        this.persons = persons;
    }

    public PersonSoapResponse(DeleteSoapPersonDto deleteSoapPersonDto) {
        this.deleteSoapPersonDto = deleteSoapPersonDto;
    }
}

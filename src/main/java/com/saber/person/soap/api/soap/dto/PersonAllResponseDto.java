package com.saber.person.soap.api.soap.dto;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonAllResponseDto",propOrder = {
        "persons",
        "error"
})
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

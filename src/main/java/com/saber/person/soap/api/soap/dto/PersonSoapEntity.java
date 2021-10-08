package com.saber.person.soap.api.soap.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonSoapEntity",propOrder = {
        "id",
        "firstName",
        "lastName",
        "nationalCode",
        "age",
        "email",
})
@Data
public class PersonSoapEntity {

    private Integer id;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private Integer age;
    private String email;
}

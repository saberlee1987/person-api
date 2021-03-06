package com.saber.person.soap.api.soap.dto;

import lombok.Data;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonSoapDto",propOrder = {
        "firstName",
        "lastName",
        "nationalCode",
        "age",
        "email",
})
public class PersonSoapDto {
    @NotBlank(message = "firstName is Required")
    @XmlElement(name = "firstName",defaultValue = "")
    private String firstName;

    @XmlElement(name = "lastName",defaultValue = "")
    @NotBlank(message = "lastName is Required")
    private String lastName;

    @XmlElement(name = "nationalCode",defaultValue = "")
    @NotBlank(message = "nationalCode is Required")
    @Size(min = 10,max = 10,message = "nationalCode must be 10 digit")
    @Pattern(regexp = "\\d+",message = "Please Enter correct nationalCode")
    private String nationalCode;

    @XmlElement(name = "age",defaultValue = "0")
    @NotNull(message = "age is Required")
    @Positive(message = "age must be > 0")
    private Integer age;

    @XmlElement(name = "email",defaultValue = "")
    @NotBlank(message = "email is Required")
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
            ,message = "please enter valid email")
    private String email;
}

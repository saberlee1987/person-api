package com.saber.person.soap.api.soap.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorSoapResponse",propOrder = {
        "code",
        "message",
        "originalMessage",
        "validations",

})
@Data
public class ErrorSoapResponse {
    private Integer code;
    private String message;
    private String originalMessage;
    private List<ValidationSoapDto> validations;
}

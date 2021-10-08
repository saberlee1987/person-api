package com.saber.person.soap.api.soap.dto;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorResponse",propOrder = {
        "code",
        "message",
        "originalMessage",
        "validations",

})
@Data
public class ErrorResponse {
    private Integer code;
    private String message;
    private String originalMessage;
    private List<ValidationDto> validations;
}

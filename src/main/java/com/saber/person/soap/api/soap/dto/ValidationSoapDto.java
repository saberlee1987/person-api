package com.saber.person.soap.api.soap.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlType;

@Data
@XmlType(name = "ValidationSoapDto",propOrder = {
        "fieldName",
        "detailMessage"
})
public class ValidationSoapDto {
    private String fieldName;
    private String detailMessage;
}

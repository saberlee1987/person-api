package com.saber.person.soap.api.soap.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlType(name = "ValidationDto",propOrder = {
        "fieldName",
        "detailMessage"
})
public class ValidationDto {
    private String fieldName;
    private String detailMessage;
}

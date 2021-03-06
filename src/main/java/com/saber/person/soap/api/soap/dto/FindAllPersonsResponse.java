package com.saber.person.soap.api.soap.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindAllPersonsResponse",propOrder = {
        "response",
        "error",

})
@Data
public class FindAllPersonsResponse {
    private List<PersonSoapEntity> response;
    private ErrorSoapResponse error;

    public FindAllPersonsResponse() {
    }

    public FindAllPersonsResponse(List<PersonSoapEntity> response) {
        this.response = response;
    }

    public FindAllPersonsResponse(ErrorSoapResponse error) {
        this.error = error;
    }

    public FindAllPersonsResponse(List<PersonSoapEntity> response, ErrorSoapResponse error) {
        this.response = response;
        this.error = error;
    }
}

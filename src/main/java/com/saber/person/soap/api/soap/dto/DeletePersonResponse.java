package com.saber.person.soap.api.soap.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeletePersonResponse",propOrder = {
        "response",
        "error",

})
@Data
public class DeletePersonResponse {
    private DeleteSoapPersonDto response;
    private ErrorSoapResponse error;

    public DeletePersonResponse() {
    }

    public DeletePersonResponse(DeleteSoapPersonDto response) {
        this.response = response;
    }

    public DeletePersonResponse(ErrorSoapResponse error) {
        this.error = error;
    }

    public DeletePersonResponse(DeleteSoapPersonDto response, ErrorSoapResponse error) {
        this.response = response;
        this.error = error;
    }
}

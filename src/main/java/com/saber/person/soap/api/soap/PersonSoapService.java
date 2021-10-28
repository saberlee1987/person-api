package com.saber.person.soap.api.soap;

import com.saber.person.soap.api.soap.dto.*;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlElement;

@WebService(serviceName = "PersonSoapService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface PersonSoapService {

    @WebMethod(operationName = "AddPerson",action = "AddPerson")
    @WebResult(name = "PersonSoapResponseDto")
    PersonSoapResponse addPerson(@WebParam(name = "personDto") @XmlElement(required = true) PersonSoapDto dto);

    @WebMethod(operationName = "FindByNationalCode",action = "FindByNationalCode")
    @WebResult(name = "PersonSoapResponseDto")
    PersonSoapResponse findByNationalCode(@WebParam(name = "nationalCode") @XmlElement(required = true,defaultValue = "") String nationalCode);


    @WebMethod(operationName = "FindAll",action = "FindAll")
    @WebResult(name = "PersonAllResponseDto")
    PersonSoapResponse findAll();

    @WebMethod(operationName = "UpdatePersonByNationalCode",action = "UpdatePersonByNationalCode")
    @WebResult(name = "PersonSoapResponseDto")
    PersonSoapResponse updatePersonByNationalCode(@WebParam(name = "nationalCode") @XmlElement(required = true,defaultValue = "")String nationalCode,@WebParam(name = "personDto") @XmlElement(required = true) PersonSoapDto dto);

    @WebMethod(operationName = "DeletePersonByNationalCode",action = "DeletePersonByNationalCode")
    @WebResult(name = "DeletePersonSoapResponseDto")
    PersonSoapResponse deletePersonByNationalCode(@WebParam(name = "nationalCode") @XmlElement(required = true,defaultValue = "")String nationalCode);
}

package com.saber.person.soap.api.soap;

import com.saber.person.soap.api.dto.PersonDto;
import com.saber.person.soap.api.soap.dto.PersonAllResponseDto;
import com.saber.person.soap.api.soap.dto.PersonResponseDto;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.ResponseWrapper;

@WebService(serviceName = "PersonSoapService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface PersonSoapService {

    @WebMethod(operationName = "AddPerson",action = "AddPerson")
    @ResponseWrapper(localName = "AddPersonResponse",className = "AddPersonResponse")
    @WebResult(name = "PersonResponseDto")
    PersonResponseDto addPerson(@WebParam(name = "personDto") @XmlElement(required = true) PersonDto dto);

    @WebMethod(operationName = "FindByNationalCode",action = "FindByNationalCode")
    @ResponseWrapper(localName = "FindByNationalCodeResponse",className = "FindByNationalCodeResponse")
    @WebResult(name = "PersonResponseDto")
    PersonResponseDto findByNationalCode(@WebParam(name = "nationalCode") @XmlElement(required = true,defaultValue = "") String nationalCode);


    @WebMethod(operationName = "FindAll",action = "FindAll")
    @ResponseWrapper(localName = "FindAllResponse",className = "FindAllResponse")
    @WebResult(name = "PersonAllResponseDto")
    PersonAllResponseDto findAll();

}

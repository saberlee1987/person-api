package com.saber.person.soap.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saber.person.soap.api.soap.PersonSoapRoute;
import com.saber.person.soap.api.soap.PersonSoapService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SoapConfig {

//    @Value(value = "${cxf.path}")
//    private String cxfPath;
    @Value(value = "${cxf.endpoint}")
    private String endpoint;
//    @Value(value = "${cxf.host}")
//    private String host;
//    @Value(value = "${cxf.soapGatewayPort}")
//    private int soapGatewayPort;

    private final PersonSoapService personSoapService;
    private final ObjectMapper mapper;
    @Bean
    public PersonSoapRoute personSoapRoute(){
        PersonSoapRoute soapRoute = new PersonSoapRoute(personSoapService,mapper);
        soapRoute.setUrl(String.format("cxf:%s?serviceClass=%s",
                 endpoint,PersonSoapService.class.getName()));
        return soapRoute;
    }
}

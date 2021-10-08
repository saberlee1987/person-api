package com.saber.person.soap.api.soap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saber.person.soap.api.soap.dto.PersonAllResponseDto;
import com.saber.person.soap.api.soap.dto.PersonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonSoapRoute extends AbstractRestRoute {
    private final String url;

    private final PersonSoapService personSoapService;
    private final ObjectMapper mapper;

    public PersonSoapRoute(PersonSoapService personSoapService, ObjectMapper mapper) {
        this.url = String.format("cxf:/personSoap?serviceClass=%s", PersonSoapService.class.getName());
        this.personSoapService = personSoapService;
        this.mapper = mapper;
    }

    @Override
    public void configure() throws Exception {

        super.configure();

        from(url)
                .choice()
                .when(header(CxfConstants.OPERATION_NAME).isEqualTo("AddPerson"))
                .removeHeaders("*")
                .process(exchange -> {
                    com.saber.person.soap.api.soap.dto.PersonDto dto = exchange.getIn().getBody(com.saber.person.soap.api.soap.dto.PersonDto.class);
                    log.info("Request for addPerson ====> {}", mapper.writeValueAsString(dto));

                    PersonResponseDto response = personSoapService.addPerson(dto);
                    if (response.getResponse() != null) {
                        log.info("Response  for addPerson with statusCode {} ====> {}"
                                , HttpStatus.OK.value()
                                , mapper.writeValueAsString(response));
                        exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.OK.value());
                    } else {
                        log.error("Error  for addPerson with statusCode {} ====> {}"
                                , response.getError().getCode()
                                , mapper.writeValueAsString(response.getError()));
                        exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, response.getError().getCode());
                    }
                    exchange.getMessage().setBody(response);
                })
                .when(header(CxfConstants.OPERATION_NAME).isEqualTo("FindByNationalCode"))
                .removeHeaders("*")
                .process(exchange -> {
                    String nationalCode = exchange.getIn().getBody(String.class);
                    log.info("Request for FindByNationalCode ====> {}", nationalCode);
                    PersonResponseDto response = personSoapService.findByNationalCode(nationalCode);
                    if (response.getResponse() != null) {
                        log.info("Response  for FindByNationalCode with statusCode {} ====> {}"
                                , HttpStatus.OK.value()
                                , mapper.writeValueAsString(response));
                        exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.OK.value());
                    } else {
                        log.error("Error  for FindByNationalCode with statusCode {} ====> {}"
                                , response.getError().getCode()
                                , mapper.writeValueAsString(response));
                        exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, response.getError().getCode());
                    }
                    exchange.getMessage().setBody(response);
                })
                .when(header(CxfConstants.OPERATION_NAME).isEqualTo("FindAll"))
                .removeHeaders("*")
                .process(exchange -> {
                    PersonAllResponseDto response = this.personSoapService.findAll();
                    log.info("Response  for FindAll with statusCode {} ====> {}"
                            , HttpStatus.OK.value()
                            , mapper.writeValueAsString(response));
                    exchange.getMessage().setBody(response);
                    exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
                })
                .endChoice()
                .end()
                .log("Service called .............. ");
    }
}

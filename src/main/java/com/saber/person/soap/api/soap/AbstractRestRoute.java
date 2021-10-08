package com.saber.person.soap.api.soap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saber.person.soap.api.exceptions.ResourceDuplicationException;
import com.saber.person.soap.api.exceptions.ResourceNotFoundException;
import com.saber.person.soap.api.soap.dto.PersonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AbstractRestRoute extends RouteBuilder {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void configure() throws Exception {

        onException(ResourceDuplicationException.class)
                .maximumRedeliveries(0)
                .handled(true)
                .process(exchange -> {
                    ResourceDuplicationException ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, ResourceDuplicationException.class);
                    log.error("Error ResourceDuplicationException ====> {}", ex.getMessage());
                    exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.BAD_REQUEST.value());
                    PersonResponseDto responseDto = new PersonResponseDto((getErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.toString(), ex.getMessage())));
                    log.error("Error for ResourceDuplicationException ===> {}", mapper.writeValueAsString(responseDto));
                    exchange.getMessage().setBody(responseDto);
                });


        onException(ResourceNotFoundException.class)
                .maximumRedeliveries(0)
                .handled(true)
                .process(exchange -> {
                    ResourceNotFoundException ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, ResourceNotFoundException.class);
                    log.error("Error ResourceNotFoundException ====> {}", ex.getMessage());
                    exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.NOT_ACCEPTABLE.value());
                    PersonResponseDto responseDto = new PersonResponseDto((getErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(),
                            HttpStatus.NOT_ACCEPTABLE.toString(), ex.getMessage())));
                    log.error("Error for ResourceNotFoundException ===> {}", mapper.writeValueAsString(responseDto));
                    exchange.getMessage().setBody(responseDto);
                });


    }

    private com.saber.person.soap.api.soap.dto.ErrorResponse getErrorResponse(Integer code, String message, String originalMessage) {
        com.saber.person.soap.api.soap.dto.ErrorResponse errorResponse = new com.saber.person.soap.api.soap.dto.ErrorResponse();
        errorResponse.setCode(code);
        errorResponse.setMessage(message);
        errorResponse.setOriginalMessage(originalMessage);
        return errorResponse;
    }

}

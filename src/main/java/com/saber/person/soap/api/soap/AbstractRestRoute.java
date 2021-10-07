package com.saber.person.soap.api.soap;

import com.saber.person.soap.api.dto.ErrorResponse;
import com.saber.person.soap.api.dto.ValidationDto;
import com.saber.person.soap.api.exceptions.ResourceDuplicationException;
import com.saber.person.soap.api.exceptions.ResourceNotFoundException;
import com.saber.person.soap.api.soap.dto.PersonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Slf4j
public class AbstractRestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        onException(ResourceDuplicationException.class)
                .maximumRedeliveries(0)
                .handled(true)
                .process(exchange -> {
                    ResourceDuplicationException ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, ResourceDuplicationException.class);
                    log.error("Error ResourceDuplicationException ====> {}", ex.getMessage());
                    exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.BAD_REQUEST.value());
                    exchange.getMessage().setBody(new PersonResponseDto( (getErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), null))));
                });


        onException(ResourceNotFoundException.class)
                .maximumRedeliveries(0)
                .handled(true)
                .process(exchange -> {
                    ResourceNotFoundException ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, ResourceNotFoundException.class);
                    log.error("Error ResourceNotFoundException ====> {}", ex.getMessage());
                    exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.NOT_ACCEPTABLE.value());
                    exchange.getMessage().setBody(new PersonResponseDto((getErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(),
                            HttpStatus.NOT_ACCEPTABLE.toString(), ex.getMessage(), null))));
                });

    }

    private ErrorResponse getErrorResponse(Integer code, String message, String originalMessage, List<ValidationDto> validationDtoList) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(code);
        errorResponse.setMessage(message);
        errorResponse.setOriginalMessage(originalMessage);
        errorResponse.setValidations(validationDtoList);
        return errorResponse;
    }
}

package com.saber.person.soap.api.adviser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saber.person.soap.api.dto.ErrorResponse;
import com.saber.person.soap.api.dto.ValidationDto;
import com.saber.person.soap.api.exceptions.ResourceDuplicationException;
import com.saber.person.soap.api.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class PersonControllerAdviser extends ResponseEntityExceptionHandler {

    private final ObjectMapper mapper;

    @ExceptionHandler(value = ResourceDuplicationException.class)
    public ResponseEntity<?> resourceDuplicationException(ResourceDuplicationException exception) {
        log.error("ResourceDuplicationException error ====> {}", exception.getMessage());
         ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setOriginalMessage(String.format("{\"code\":%d,\"message\":\"%s\"}",
                HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
        try {
            log.error("ResourceDuplicationException error ====> {}", mapper.writeValueAsString(errorResponse));
        } catch (Exception ex) {
            log.error("ResourceDuplicationException error ====> {}", errorResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException exception) {
        log.error("ResourceNotFoundException error ====> {}", exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.NOT_ACCEPTABLE.value());
        errorResponse.setMessage(HttpStatus.NOT_ACCEPTABLE.toString());
        errorResponse.setOriginalMessage(String.format("{\"code\":%d,\"message\":\"%s\"}",
                HttpStatus.NOT_ACCEPTABLE.value(), exception.getMessage()));
        try {
            log.error("ResourceNotFoundException error ====> {}", mapper.writeValueAsString(errorResponse));
        } catch (Exception ex) {
            log.error("ResourceNotFoundException error ====> {}", errorResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error("handleMissingPathVariable error ====> {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(HttpStatus.BAD_REQUEST.toString());

        List<ValidationDto> validationDtoList = new ArrayList<>();
        ValidationDto validationDto = new ValidationDto();
        validationDto.setFieldName(ex.getVariableName());
        validationDto.setDetailMessage(ex.getParameter().toString());
        validationDtoList.add(validationDto);

        errorResponse.setValidations(validationDtoList);
        try {
            log.error("handleMissingPathVariable error ====> {}", mapper.writeValueAsString(errorResponse));
        } catch (Exception e) {
            log.error("handleMissingPathVariable error ====> {}", errorResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleBindException error ====> {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(HttpStatus.BAD_REQUEST.toString());

        List<ValidationDto> validationDtoList = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            ValidationDto validationDto = new ValidationDto();
            validationDto.setFieldName(fieldError.getField());
            validationDto.setDetailMessage(fieldError.getDefaultMessage());
            validationDtoList.add(validationDto);
        }
        errorResponse.setValidations(validationDtoList);
        try {
            log.error("handleBindException error ====> {}", mapper.writeValueAsString(errorResponse));
        } catch (Exception e) {
            log.error("handleBindException error ====> {}", errorResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleMissingServletRequestParameter error ====> {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setOriginalMessage(String.format("{\"code\":%d,\"message\":\"%s\"}",
                HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        try {
            log.error("handleMissingServletRequestParameter error ====> {}", mapper.writeValueAsString(errorResponse));
        } catch (Exception e) {
            log.error("handleMissingServletRequestParameter error ====> {}", errorResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleMethodArgumentNotValid error ====> {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(HttpStatus.BAD_REQUEST.toString());

        List<ValidationDto> validationDtoList = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            ValidationDto validationDto = new ValidationDto();
            validationDto.setFieldName(fieldError.getField());
            validationDto.setDetailMessage(fieldError.getDefaultMessage());
            validationDtoList.add(validationDto);
        }
        errorResponse.setValidations(validationDtoList);
        try {
            log.error("handleMethodArgumentNotValid error ====> {}", mapper.writeValueAsString(errorResponse));
        } catch (Exception e) {
            log.error("handleMethodArgumentNotValid error ====> {}", errorResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {

        log.error("handleConstraintViolationException error ====> {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(HttpStatus.BAD_REQUEST.toString());

        List<ValidationDto> validationDtoList = new ArrayList<>();
        ValidationDto validationDto = new ValidationDto();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        for (ConstraintViolation<?> v : constraintViolations) {
            ValidationDto validationDtoClone = validationDto.clone();
            validationDtoClone.setFieldName(v.getPropertyPath().toString());
            validationDtoClone.setDetailMessage(v.getMessage());
            validationDtoList.add(validationDtoClone);
        }

        errorResponse.setValidations(validationDtoList);
        try {
            log.error("handleMissingPathVariable error ====> {}", mapper.writeValueAsString(errorResponse));
        } catch (Exception e) {
            log.error("handleMissingPathVariable error ====> {}", errorResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}

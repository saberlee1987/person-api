package com.saber.person.soap.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saber.person.soap.api.dto.*;
import com.saber.person.soap.api.entity.PersonEntity;
import com.saber.person.soap.api.services.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(value = "${service.api.path}", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(value = "PersonController", nickname = "PersonController", produces = "application/json")
public class PersonController {
    private final PersonService personService;
    private final ObjectMapper mapper;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "addPerson", nickname = "addPerson", produces = "application/json", consumes = "application/json", httpMethod = "POST")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success", response = PersonEntity.class),
                    @ApiResponse(code = 400, message = "BAD_REQUEST", response = ErrorResponse.class),
                    @ApiResponse(code = 406, message = "NOT_ACCEPTABLE", response = ErrorResponse.class)
            }
    )
    public ResponseEntity<PersonEntity> addPerson(@RequestBody @Valid PersonDto personDto) {
        try {
            log.info("Request for addPerson  ====> {}", mapper.writeValueAsString(personDto));
        } catch (Exception ex) {
            log.info("Request for addPerson  ====> {}", personDto);
        }
        PersonEntity response = this.personService.addPerson(personDto);
        try {
            log.info("Response for addPerson  ====> {}", mapper.writeValueAsString(response));
        } catch (Exception ex) {
            log.info("Response for addPerson  ====> {}", response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/findByNationalCode/{nationalCode}")
    @ApiOperation(value = "findByNationalCode", nickname = "findByNationalCode", produces = "application/json", httpMethod = "GET")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success", response = PersonEntity.class),
                    @ApiResponse(code = 400, message = "BAD_REQUEST", response = ErrorResponse.class),
                    @ApiResponse(code = 406, message = "NOT_ACCEPTABLE", response = ErrorResponse.class)
            }
    )
    public ResponseEntity<PersonEntity> findByNationalCode(@PathVariable(name = "nationalCode")
                                                           @NotBlank(message = "nationalCode is Required")
                                                           @Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
                                                           @Pattern(regexp = "\\d+", message = "Please Enter correct nationalCode")
                                                           @Valid
                                                           @ApiParam(name = "nationalCode", value = "nationalCode", example = "0079028748", required = true)
                                                                   String nationalCode) {
        log.info("Request for findByNationalCode  ====> {}", nationalCode);

        PersonEntity response = this.personService.findByNationalCode(nationalCode);
        try {
            log.info("Response for findByNationalCode  ====> {}", mapper.writeValueAsString(response));
        } catch (Exception ex) {
            log.info("Response for findByNationalCode  ====> {}", response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/findAll")
    @ApiOperation(value = "findAll", nickname = "findAll", produces = "application/json", httpMethod = "GET")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success", response = PersonResponse.class),
                    @ApiResponse(code = 400, message = "BAD_REQUEST", response = ErrorResponse.class),
                    @ApiResponse(code = 406, message = "NOT_ACCEPTABLE", response = ErrorResponse.class)
            }
    )
    public ResponseEntity<PersonResponse> findAllPerson() {
        PersonResponse response = this.personService.findAll();
        try {
            log.info("Response for findAllPerson  ====> {}", mapper.writeValueAsString(response));
        } catch (Exception ex) {
            log.info("Response for findAllPerson  ====> {}", response);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/updateByNationalCode/{nationalCode}")
    @ApiOperation(value = "updateByNationalCode", nickname = "updateByNationalCode", produces = "application/json", httpMethod = "PUT")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success", response = PersonEntity.class),
                    @ApiResponse(code = 400, message = "BAD_REQUEST", response = ErrorResponse.class),
                    @ApiResponse(code = 406, message = "NOT_ACCEPTABLE", response = ErrorResponse.class)
            }
    )
    public ResponseEntity<PersonEntity> updatePersonByNationalCode(@PathVariable(name = "nationalCode")
                                                                   @NotBlank(message = "nationalCode is Required")
                                                                   @Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
                                                                   @Pattern(regexp = "\\d+", message = "Please Enter correct nationalCode")
                                                                   @Valid
                                                                   @ApiParam(name = "nationalCode", value = "nationalCode", example = "0079028748", required = true) String nationalCode,
                                                                   @RequestBody @Valid
                                                                           PersonDto dto) {
        try {
            log.info("Request for updatePersonByNationalCode by  nationalCode {} ====> {}", nationalCode, mapper.writeValueAsString(dto));
        } catch (Exception ex) {
            log.info("Request for updatePersonByNationalCode by  nationalCode {} ====> {}", nationalCode, dto);
        }
        PersonEntity response = this.personService.updatePersonByNationalCode(nationalCode, dto);
        try {
            log.info("Response for updatePersonByNationalCode by nationalCode {}  ====> {}", nationalCode, mapper.writeValueAsString(response));
        } catch (Exception ex) {
            log.info("Response for updatePersonByNationalCode by  nationalCode {} ====> {}", nationalCode, response);
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/deleteByNationalCode/{nationalCode}")
    @ApiOperation(value = "deleteByNationalCode", nickname = "deleteByNationalCode", produces = "application/json", httpMethod = "DELETE")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success", response = DeletePersonDto.class),
                    @ApiResponse(code = 400, message = "BAD_REQUEST", response = ErrorResponse.class),
                    @ApiResponse(code = 406, message = "NOT_ACCEPTABLE", response = ErrorResponse.class)
            }
    )
    public ResponseEntity<DeletePersonDto> deletePersonByNationalCode(@PathVariable(name = "nationalCode")
                                                                      @NotBlank(message = "nationalCode is Required")
                                                                      @Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
                                                                      @Pattern(regexp = "\\d+", message = "Please Enter correct nationalCode")
                                                                      @Valid
                                                                      @ApiParam(name = "nationalCode", value = "nationalCode", example = "0079028748", required = true) String nationalCode) {
        log.info("Request for deletePersonByNationalCode by  nationalCode {} ", nationalCode);

        DeletePersonDto response = this.personService.deletePersonByNationalCode(nationalCode);
        try {
            log.info("Response for deletePersonByNationalCode by nationalCode {}  ====> {}", nationalCode, mapper.writeValueAsString(response));
        } catch (Exception ex) {
            log.info("Response for deletePersonByNationalCode by  nationalCode {} ====> {}", nationalCode, response);
        }

        return ResponseEntity.ok(response);
    }
}

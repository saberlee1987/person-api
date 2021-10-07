package com.saber.person.soap.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saber.person.soap.api.dto.PersonDto;
import com.saber.person.soap.api.dto.PersonResponse;
import com.saber.person.soap.api.dto.ResponseDto;
import com.saber.person.soap.api.entity.PersonEntity;
import com.saber.person.soap.api.services.PersonService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/services/person", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(value = "PersonController", nickname = "PersonController", produces = "application/json")
public class PersonController {
    private final PersonService personService;
    private final ObjectMapper mapper;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "addPerson", nickname = "addPerson", produces = "application/json", consumes = "application/json", httpMethod = "POST")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success", response = ResponseDto.class),
                    @ApiResponse(code = 400, message = "BAD_REQUEST", response = ResponseDto.class),
                    @ApiResponse(code = 406, message = "NOT_ACCEPTABLE", response = ResponseDto.class)
            }
    )
    public ResponseEntity<ResponseDto<PersonEntity>> addPerson(@RequestBody @Valid PersonDto personDto) {
        try {
            log.info("Request for addPerson  ====> {}", mapper.writeValueAsString(personDto));
        } catch (Exception ex) {
            log.info("Request for addPerson  ====> {}", personDto);
        }
        ResponseDto<PersonEntity> response = this.personService.addPerson(personDto);
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
                    @ApiResponse(code = 200, message = "Success", response = ResponseDto.class),
                    @ApiResponse(code = 400, message = "BAD_REQUEST", response = ResponseDto.class),
                    @ApiResponse(code = 406, message = "NOT_ACCEPTABLE", response = ResponseDto.class)
            }
    )
    public ResponseEntity<ResponseDto<PersonEntity>> findByNationalCode(@PathVariable(name = "nationalCode")
                                                                        @NotBlank(message = "nationalCode is Required")
                                                                        @Size(min = 10, max = 10, message = "nationalCode must be 10 digit")
                                                                        @Pattern(regexp = "\\d+", message = "Please Enter correct nationalCode")
                                                                        @Valid
                                                                        @ApiParam(name = "nationalCode", value = "nationalCode", example = "0079028748", required = true)
                                                                                String nationalCode) {
        log.info("Request for findByNationalCode  ====> {}", nationalCode);

        ResponseDto<PersonEntity> response = this.personService.findByNationalCode(nationalCode);
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
                    @ApiResponse(code = 200, message = "Success", response = ResponseDto.class),
                    @ApiResponse(code = 400, message = "BAD_REQUEST", response = ResponseDto.class),
                    @ApiResponse(code = 406, message = "NOT_ACCEPTABLE", response = ResponseDto.class)
            }
    )
    public ResponseEntity<ResponseDto<PersonResponse>> findAllPerson() {
        ResponseDto<PersonResponse> response = this.personService.findAll();
        try {
            log.info("Response for findAllPerson  ====> {}", mapper.writeValueAsString(response));
        } catch (Exception ex) {
            log.info("Response for findAllPerson  ====> {}", response);
        }
        return ResponseEntity.ok(response);
    }
}

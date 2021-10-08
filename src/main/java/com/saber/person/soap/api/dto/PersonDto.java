package com.saber.person.soap.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.*;

@Data
@ApiModel(value = "personDto")
public class PersonDto {

    @NotBlank(message = "firstName is Required")
    @ApiModelProperty(value = "firstName",name = "firstName",example = "saber",position = 1,required = true)
    private String firstName;

    @NotBlank(message = "lastName is Required")
    @ApiModelProperty(value = "lastName",name = "lastName",example = "Azizi",position = 2,required = true)
    private String lastName;

    @NotBlank(message = "nationalCode is Required")
    @Size(min = 10,max = 10,message = "nationalCode must be 10 digit")
    @Pattern(regexp = "\\d+",message = "Please Enter correct nationalCode")
    @ApiModelProperty(value = "nationalCode",name = "nationalCode",example = "0079028748",position = 3,required = true)
    private String nationalCode;


    @NotNull(message = "age is Required")
    @Positive(message = "age must be > 0")
    @ApiModelProperty(value = "age",name = "age",example = "33",position = 4,required = true)
    private Integer age;


    @NotBlank(message = "email is Required")
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
            ,message = "please enter valid email")
    @ApiModelProperty(value = "email",name = "email",example = "saberazizi66@yahoo.com",position = 5,required = true)
    private String email;
}

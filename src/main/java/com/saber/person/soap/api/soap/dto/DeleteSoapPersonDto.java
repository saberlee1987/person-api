package com.saber.person.soap.api.soap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotBlank
public class DeleteSoapPersonDto {
    private Integer code;
    private String text;
}

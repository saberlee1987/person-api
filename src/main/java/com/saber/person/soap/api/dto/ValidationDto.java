package com.saber.person.soap.api.dto;

import lombok.Data;

@Data
public class ValidationDto {
    private String fieldName;
    private String detailMessage;
}

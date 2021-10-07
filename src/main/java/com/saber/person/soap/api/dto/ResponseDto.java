package com.saber.person.soap.api.dto;

import lombok.Data;

@Data
public class ResponseDto<R> {
    private R response;
    private ErrorResponse error;

    public ResponseDto() {
        this(null, null);
    }

    public ResponseDto(R response) {
        this(response, null);
    }

    public ResponseDto(R response, ErrorResponse error) {
        this.response = response;
        this.error = error;
    }
}
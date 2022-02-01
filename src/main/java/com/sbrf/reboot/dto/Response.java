package com.sbrf.reboot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Response {

    @Getter
    private String statusCode;

    public Response(String response) {
        this.statusCode = response;
    }
}

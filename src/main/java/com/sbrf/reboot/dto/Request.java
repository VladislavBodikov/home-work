package com.sbrf.reboot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Request {

    @Getter
    private String atmNumber;

    public Request(String value) {
        this.atmNumber = value;
    }
}

package com.sbrf.reboot.serialize.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class User implements Serializable {
    private long id;
    private String name;
    private Set<Score> scores = new HashSet<>();
    // key - cardNumber value - score equal cardNumber
    private Map<String, Score> cards = new HashMap<>();

    public Set<Score> getScores() {
        return new HashSet<>(scores);
    }

}

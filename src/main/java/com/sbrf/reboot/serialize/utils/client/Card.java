package com.sbrf.reboot.serialize.utils.client;

import com.sbrf.reboot.serialize.utils.Score;
import lombok.Data;

@Data
public class Card {
    private String cardHolderName;
    private long number;
    private int cvv;
    private int endMonth;
    private int endYear;
    private Score score;
}

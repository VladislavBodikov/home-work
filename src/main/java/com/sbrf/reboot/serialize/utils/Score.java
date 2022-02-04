package com.sbrf.reboot.serialize.utils;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Score implements Serializable {
    private long numberPaymentAccount;
    private long cardNumber;
    private BigDecimal amount;

    public void sendMoney(String value, Score scoreTo) {
        BigDecimal valueDecimal = new BigDecimal(value);
        BigDecimal amountTo = scoreTo.getAmount();
        int comparing = amount.compareTo(valueDecimal);
        if (comparing > -1) {
            amount = amount.subtract(valueDecimal);
            amountTo = amountTo.add(valueDecimal);
            System.out.println("Сумма : " + value + "\n" + "Переведена на номер карты: " + scoreTo.cardNumber);
        }
        System.out.println("Недостаточно средств для перевода");
    }

    public int compareAmount(double amountToCompare){
        BigDecimal toCompare = new BigDecimal(amountToCompare);
        return amount.compareTo(toCompare);
    }
}

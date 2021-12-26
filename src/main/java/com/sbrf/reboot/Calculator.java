package com.sbrf.reboot;

public class Calculator {

    public int getAddition(int num1, int num2) {
        return num1 + num2;
    }

    public int getSubtraction(int num1, int num2) {
        return num1 - num2;
    }

    public int getMultiplication(int num1, int num2) {
        return num1 * num2;
    }

    public int getDivision(int num1, int num2) {
        return num1 / num2;
    }

    public int getExponentiation(int num1, int num2){
        return (int)Math.pow(num1,num2);
    }
    public int getSquareRoot(int num){
        return (int)Math.sqrt(num);
    }
    public double getSin(int angle){
        return Math.round(Math.sin(Math.toRadians(angle)) * 100)/100.0;
    }
}


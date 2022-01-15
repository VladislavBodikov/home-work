package com.sbrf.reboot.atm.cassettes;

import com.sbrf.reboot.atm.cassettes.genmethod.GenMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class CassetteTest {

    class OneHundred extends Banknote {
    }

    class OneThousand extends Banknote {
    }

    @Test
    void getCountBanknotes() {
        OneHundred oneHundred = new OneHundred();

        Cassette<OneHundred> cassette = new Cassette<>(new ArrayList<OneHundred>() {{
            add(oneHundred);
//            add(new OneThousand()); //it will not compile
//            add(new Banknote()); //it will not compile
        }});

        Assertions.assertEquals(1, cassette.getCountBanknotes());
    }
    @Test
    @DisplayName("Имя есть в массиве")
    void testStringTrueGenMethod(){
        String[] names = {"Vlad","Alex","Erick"};
        Assertions.assertTrue(GenMethod.isIn("Vlad",names));
    }
    @Test
    @DisplayName("Имени нет в массиве")
    void testStringFalseGenMethod(){
        String[] names = {"Vlad","Alex","Erick"};
        Assertions.assertFalse(GenMethod.isIn("John",names));
    }
    @Test
    @DisplayName("Целое число есть в массиве")
    void testIntTrueGenMethod(){
        Integer[] numbers = {1,2,3};
        Assertions.assertTrue(GenMethod.isIn(3,numbers));
    }
    @Test
    @DisplayName("Числа с плавающей точкой нет в массиве")
    void testDoubleFalseGenMethod(){
        Double[] numbers = {1.0,2.0,3.0};
        Assertions.assertFalse(GenMethod.isIn(5.0,numbers));
    }
}
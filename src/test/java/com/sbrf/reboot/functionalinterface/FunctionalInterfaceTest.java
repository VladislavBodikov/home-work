package com.sbrf.reboot.functionalinterface;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FunctionalInterfaceTest {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class SomeObject {
        private String objectName;
    }

    @FunctionalInterface
    interface ObjectToJsonFunction<T> {
        String applyAsJson(T t) throws JsonProcessingException;
    }

    static class ListConverter<T> {
        public List<String> toJsonsList(@NonNull List<T> someObjects, ObjectToJsonFunction<T> objectToJsonFunction) {
            List<String> result = new ArrayList<>();
            if (someObjects.isEmpty())
                throw new IllegalArgumentException("The list is empty");
            //add code here...
            someObjects.forEach((obj) -> {
                try {
                    result.add(objectToJsonFunction.applyAsJson(obj));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            return result;
        }
    }

    @Test
    public void successCallFunctionalInterface() {
        ListConverter<SomeObject> ListConverter = new ListConverter<>();

        ObjectToJsonFunction<SomeObject> objectToJsonFunction = someObject -> {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(someObject);
        };

        List<String> strings = ListConverter.toJsonsList(
                Arrays.asList(
                        new SomeObject("Object-1"),
                        new SomeObject("Object-2")
                ),
                objectToJsonFunction
        );

        Assertions.assertTrue(strings.contains("{\"objectName\":\"Object-1\"}"));
        Assertions.assertTrue(strings.contains("{\"objectName\":\"Object-2\"}"));
    }

    @FunctionalInterface
    interface StringOp {
        String func(String s);
    }
    static String doWithString(StringOp stringOp, String s){
        return stringOp.func(s);
    }

    @Test
    @DisplayName("Удалить каждую четную букву")
    void removeOddTest(){
        StringOp stringOp = (s)->{
            String result = "";
            for (int i = 0; i < s.length(); i++){
                result += (i % 2 == 0) ? "" : s.charAt(i);
            }
            return result;
        };

        Assertions.assertEquals(doWithString(stringOp,"собака"),"оаа");
    }
    @Test
    @DisplayName("Перевернуть строку")
    void reverseStringTest(){
        StringOp stringOp = (s)->{
            String result = "";
            for (int i = s.length() - 1; i >= 0 ; i--){
                result += s.charAt(i);
            }
            return result;
        };

        Assertions.assertEquals(doWithString(stringOp,"собака"),"акабос");
    }

    @FunctionalInterface
    interface CustomCheck<T>{
        boolean func(T t);
    }
    static boolean makeOperation(CustomCheck customCheck, Integer t){
        return customCheck.func(t);
    }
    @Test
    @DisplayName("Является ли четным")
    void isOdd(){
        CustomCheck<Integer> customCheck = (x)-> (x % 2 == 0);
        Assertions.assertTrue(makeOperation(customCheck,100));
    }
    @Test
    @DisplayName("Является ли квадратом")
    void isSquare(){
        CustomCheck<Integer> customCheck = (x)-> {
            for (int i = 0; i < x; i++){
                if (i * i == x)
                    return true;
            }
            return false;
        };
        Assertions.assertAll(
                ()->Assertions.assertTrue(makeOperation(customCheck,100)),
                ()->Assertions.assertFalse(makeOperation(customCheck,99)));

    }

}

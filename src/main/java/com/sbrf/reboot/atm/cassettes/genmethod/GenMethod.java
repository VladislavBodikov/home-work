package com.sbrf.reboot.atm.cassettes.genmethod;

public class GenMethod {
    // входит ли элемент в массив
    public static <T extends Comparable<T>, V extends T> boolean isIn(T x, V[] array){
        for (int i = 0; i < array.length; i++){
            if (x.equals(array[i])) return true;
        }
        return false;
    }
}

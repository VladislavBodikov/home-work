package com.sbrf.reboot.atm.cassettes;

import java.util.List;

public class Cassette<T extends Banknote> {
    List<T> banknote;
    Cassette(List<T> banknote){
        this.banknote = banknote;
    }
    public void showType(){
        System.out.println("Типом Т является: " + banknote.getClass().getName());
    }
    public int getCountBanknotes(){
        return banknote.size();
    }

}

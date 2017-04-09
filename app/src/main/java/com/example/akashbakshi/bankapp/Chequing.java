package com.example.akashbakshi.bankapp;

/**
 * Created by akashbakshi on 2017-04-07.
 */

public class Chequing extends BankAccount {
    private Double fee;

    public Chequing(Double f){
        fee = f;
    }

    @Override
    boolean monthlyUpdate() {
        boolean enough = true;

        if(fee > balance)
            enough = false;

        else{
            balance -= fee;

        }
         return enough;
    }
}

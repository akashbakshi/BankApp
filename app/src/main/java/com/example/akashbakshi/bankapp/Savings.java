package com.example.akashbakshi.bankapp;

/**
 * Created by akashbakshi on 2017-04-07.
 */

public class Savings extends BankAccount {

    private Double minBalance;
    private Double interestRate;

    public Savings(Double mBal, Double iRate){
        minBalance = mBal;
        interestRate = iRate;
    }

    @Override
    boolean monthlyUpdate(){
        boolean enough = true;

        if(balance < minBalance)
            enough = false;

        else
            balance *= interestRate;

        return enough;
    }
}

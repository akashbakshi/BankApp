package com.example.akashbakshi.bankapp;

/**
 * Created by akashbakshi on 2017-04-07.
 */

public abstract class BankAccount {
    protected int accNum;
    protected Person accHolder;
    protected Double balance;


    abstract boolean monthlyUpdate();
}

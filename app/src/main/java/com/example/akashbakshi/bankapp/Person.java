package com.example.akashbakshi.bankapp;

/**
 * Created by akashbakshi on 2017-04-07.
 */

public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private long phoneNum;

    Person(String fName,String lName,String email, long pNum){
        firstName = fName;
        lastName = lName;
        this.email = email;
        phoneNum = pNum;
    }

    public String getFirstName(){

        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getEmail(){
        return email;
    }

    public String getPhoneNum(){
        return Long.toString(phoneNum);
    }

}

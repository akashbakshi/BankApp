package com.example.akashbakshi.bankapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

/**
 * Created by akashbakshi on 2017-04-05.
 */

public class SignUp extends AppCompatActivity {

    private EditText tfFirstName;
    private EditText tfLastName;
    private EditText tfPhoneNum;
    private EditText tfEmail;
    private EditText tfOpeningBal;
    private EditText tfWildcard;
    private EditText tfWildcard2;

    private RadioButton rbCheq;
    private RadioButton rbSav;

    private Button btnCreate;

    private TextView accountNumber;
    private int accSel = -1;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        tfFirstName = (EditText)findViewById(R.id.tvFIrstName);
        tfLastName = (EditText)findViewById(R.id.tvLastName);
        tfPhoneNum = (EditText)findViewById(R.id.phoneNum);
        tfEmail = (EditText)findViewById(R.id.tvEmail);
        tfOpeningBal = (EditText)findViewById(R.id.tvOpening);
        tfWildcard = (EditText) findViewById(R.id.tfWC1);
        tfWildcard2 = (EditText) findViewById(R.id.tfWC2);


        tfWildcard.setVisibility(View.GONE);
        tfWildcard2.setVisibility(View.GONE);
        tfWildcard.setEnabled(false);
        tfWildcard2.setEnabled(false);

        rbCheq = (RadioButton) findViewById(R.id.rbChequings);
        rbSav = (RadioButton) findViewById(R.id.rbSavings);

        accountNumber = (TextView)findViewById(R.id.tvAccNumber);
        accountNumber.setText("Account Number: "+MainActivity.accNumber);

        btnCreate = (Button)findViewById(R.id.btnCreate);



        rbCheq.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        tfWildcard.setVisibility(View.VISIBLE);

                        tfWildcard2.setVisibility(View.GONE);
                        tfWildcard2.setEnabled(false);

                        tfWildcard.setEnabled(true);
                        tfWildcard.setHint("Monthly Fee");
                        accSel = 0;
                    }
                }
        );

        rbSav.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        tfWildcard.setVisibility(View.VISIBLE);
                        tfWildcard2.setVisibility(View.VISIBLE);

                        tfWildcard.setEnabled(true);
                        tfWildcard.setHint("Minimum Balance");

                        tfWildcard2.setEnabled(true);
                        tfWildcard2.setHint("Interest Rate");
                        accSel = 1;
                    }
                }
        );

        btnCreate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        BankAccount acc = new Chequing(0.0);
                        String fName = tfFirstName.getText().toString();
                        String lName = tfLastName.getText().toString();


                        long pNum = Long.parseLong(tfPhoneNum.getText().toString());
                        String email = tfEmail.getText().toString();
                        Double balance = Double.parseDouble(tfOpeningBal.getText().toString());
                        if (accSel == 0){
                            Double fee = Double.parseDouble(tfWildcard.getText().toString());

                            acc = new Chequing(fee);
                            acc.accNum = MainActivity.accNumber;
                            acc.accHolder = new Person(fName,lName,email,pNum);
                            acc.balance = balance;
                            MainActivity.accounts.add(acc);

                            MainActivity.myRef.child("Users").child(Integer.toString(acc.accNum)).child("details").push().child("AccType").setValue("c");
                            MainActivity.myRef.child("Users").child(Integer.toString(acc.accNum)).child("details").push().child("FirstName").setValue(fName);
                            MainActivity.myRef.child("Users").child(Integer.toString(acc.accNum)).child("details").push().child("LastName").setValue(lName);
                            MainActivity.myRef.child("Users").child(Integer.toString(acc.accNum)).child("details").push().child("PhoneNumber").setValue(Long.toString(pNum));
                            MainActivity.myRef.child("Users").child(Integer.toString(acc.accNum)).child("details").push().child("Email").setValue(email);
                            MainActivity.myRef.child("Users").child(Integer.toString(acc.accNum)).child("details").push().child("Balance").setValue(balance);
                            MainActivity.myRef.child("Users").child(Integer.toString(acc.accNum)).child("details").push().child("Fee").setValue(Double.toString(fee));

                        }
                        else if (accSel == 1){
                            Double minBal = Double.parseDouble(tfWildcard.getText().toString());
                            Double interestRate = Double.parseDouble(tfWildcard2.getText().toString());

                            acc = new Savings(minBal,interestRate);
                            acc.accNum = MainActivity.accNumber;
                            acc.balance = balance;
                            acc.accHolder = new Person(fName,lName,email,pNum);
                            MainActivity.accounts.add(acc);

                            MainActivity.myRef.child("Users").child(Integer.toString(acc.accNum)).child("details").push().child("AccType").setValue("s");
                            MainActivity.myRef.child("Users").child(Integer.toString(acc.accNum)).child("details").push().child("FirstName").setValue(fName);
                            MainActivity.myRef.child("Users").child(Integer.toString(acc.accNum)).child("details").push().child("LastName").setValue(lName);
                            MainActivity.myRef.child("Users").child(Integer.toString(acc.accNum)).child("details").push().child("PhoneNumber").setValue(Long.toString(pNum));
                            MainActivity.myRef.child("Users").child(Integer.toString(acc.accNum)).child("details").push().child("Email").setValue(email);
                            MainActivity.myRef.child("Users").child(Integer.toString(acc.accNum)).child("details").push().child("Balance").setValue(Double.toString(balance));
                            MainActivity.myRef.child("Users").child(Integer.toString(acc.accNum)).child("details").push().child("MinBalance").setValue(Double.toString(minBal));
                            MainActivity.myRef.child("Users").child(Integer.toString(acc.accNum)).child("details").push().child("InterestRate").setValue(Double.toString(interestRate));
                        }

                        MainActivity.accNumber++;
                        MainActivity.numAccounts++;
                        MainActivity.myRef.child("numberAccount").setValue(MainActivity.numAccounts);
                        finish();

                    }
                }
        );


    }
}

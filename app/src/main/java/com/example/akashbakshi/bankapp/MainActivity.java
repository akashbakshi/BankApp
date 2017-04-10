package com.example.akashbakshi.bankapp;

import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<BankAccount> accounts = new ArrayList<BankAccount>(1000);
    private EditText tfAccNum;
    private Button signUp;
    private Button signIn;
    public static int numAccounts = 0;
    public static int accNumber = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tfAccNum = (EditText)findViewById(R.id.tfAcc);
        signUp = (Button)findViewById(R.id.btnSignUp);
        signIn = (Button)findViewById(R.id.btnSignIn);

        signUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent signIntent = new Intent(MainActivity.this,SignUp.class);
                        startActivity(signIntent);
                    }
                }
        );

        signIn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int accInd = findAccount(Integer.parseInt(tfAccNum.getText().toString()));

                        if(accInd != -1){
                            Toast t = Toast.makeText(getApplicationContext(),accounts.get(accInd).accHolder.toString(),Toast.LENGTH_LONG);
                            t.show();
                        }

                    }
                }
        );
    }

    private int findAccount(int acc){
        int index = -1;

        for (int i =0;i<(numAccounts);i++){
            Log.d("acc entered",Integer.toString(acc));
            Log.d("acc found",Integer.toString(accounts.get(i).accNum));
            if(acc == accounts.get(i).accNum)
                index = i;
        }
        Log.d("index:",Integer.toString(index));
        return index;
    }
}

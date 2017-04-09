package com.example.akashbakshi.bankapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<BankAccount> accounts = new ArrayList<BankAccount>(1000);

    private Button signUp;
    private Button signIn;

    public static int accNumber = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        Toast t = Toast.makeText(getApplicationContext(),accounts.get(0).accHolder.toString(),Toast.LENGTH_LONG);
                        t.show();
                    }
                }
        );
    }
}

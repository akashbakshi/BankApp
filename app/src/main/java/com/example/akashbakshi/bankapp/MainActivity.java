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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<BankAccount> accounts = new ArrayList<BankAccount>(1000);
    private EditText tfAccNum;
    private Button signUp;
    private Button signIn;
    public static int numAccounts = 0;
    public static int accNumber = 1000;

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static final DatabaseReference myRef = database.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tfAccNum = (EditText)findViewById(R.id.tfAcc);
        signUp = (Button)findViewById(R.id.btnSignUp);
        signIn = (Button)findViewById(R.id.btnSignIn);


        MainActivity.myRef.addValueEventListener(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("test","123");
                        numAccounts = Integer.parseInt(dataSnapshot.child("numberAccount").getValue().toString());
                        DataSnapshot userinfo = dataSnapshot.child("Users").child("1000").child("details");
                        Log.d("value",userinfo.child("AccType").toString());
                        generateAccountsFromDb(numAccounts,dataSnapshot);
                        accNumber+=numAccounts;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

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
                            Intent Dashboardintent = new Intent(MainActivity.this,Dashboard.class);
                            Dashboardintent.putExtra("Account Index",accInd);
                            startActivity(Dashboardintent);
                        }
                        else{
                            Toast t = Toast.makeText(getApplicationContext(),"No Account Found!",Toast.LENGTH_LONG);
                            t.show();
                        }

                    }
                }
        );
    }

    private void generateAccountsFromDb(int accInd,DataSnapshot data){

        for(int i =0;i<accInd;i++){
            BankAccount acc = null;
            accNumber+=i;

            String type = data.child("Users").child("1000").child("details").child("AccType").getValue().toString();
            Log.d("acctype",type);
            String first = data.child("Users").child(Integer.toString(acc.accNum)).child("details").child("FirstName").getValue().toString();
            String last = data.child("Users").child(Integer.toString(accNumber)).child("details").child("LastName").getValue().toString();
            long Phone = Long.parseLong(data.child("Users").child(Integer.toString(accNumber)).child("details").child("PhoneNumber").getValue().toString());
            String email = data.child("Users").child(Integer.toString(accNumber)).child("details").child("Email").getValue().toString();
            double Bal = Double.parseDouble(data.child("Users").child(Integer.toString(accNumber)).child("details").child("Balance").getValue().toString());

            if(type == "s") {
                double minBal = Double.parseDouble(data.child("Users").child(Integer.toString(accNumber)).child("details").child("MinBalance").getValue().toString());
                double interestRate = Double.parseDouble(data.child("Users").child(Integer.toString(accNumber)).child("details").child("InterestRate").getValue().toString());
                acc = new Savings(minBal,interestRate);
                acc.accNum = accNumber;
                acc.balance = Bal;
                acc.accHolder = new Person(first,last,email,Phone);
                accounts.add(acc);
            }
            else if (type == "c"){
                double fee = Double.parseDouble(data.child("Users").child(Integer.toString(accNumber)).child("details").child("Fee").getValue().toString());
                acc = new Chequing(fee);
                acc.accNum = accNumber;
                acc.balance = Bal;
                acc.accHolder = new Person(first,last,email,Phone);
                accounts.add(acc);
            }
        }
    }
    private int findAccount(int acc){
        int index = -1;

        for (int i =0;i<(numAccounts);i++){
            if(acc == accounts.get(i).accNum)
                index = i;
        }
        Log.d("index:",Integer.toString(index));
        return index;
    }
}

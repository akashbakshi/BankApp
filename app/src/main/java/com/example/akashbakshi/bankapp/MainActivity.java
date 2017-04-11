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

        database.getReference("Users").keepSynced(true);
        tfAccNum = (EditText)findViewById(R.id.tfAcc);
        signUp = (Button)findViewById(R.id.btnSignUp);
        signIn = (Button)findViewById(R.id.btnSignIn);

        MainActivity.myRef.addValueEventListener(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        numAccounts = Integer.parseInt(dataSnapshot.child("numberAccount").getValue().toString());
                        generateAccountsFromDb(numAccounts,dataSnapshot);
                        Log.d("numacc",Integer.toString(numAccounts));

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

        accNumber+=numAccounts;
    }

    private void generateAccountsFromDb(int accInd, final DataSnapshot data){
        Log.d("numacc",Integer.toString(numAccounts));
        for(int i =0;i<numAccounts;i++){

            accNumber+=i;
            Log.d("numacc",Integer.toString(accNumber));
            DatabaseReference userinfo = myRef.child("Users").child(Integer.toString(accNumber)).child("details");
            userinfo.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                             BankAccount acc = new Chequing(0.0);
                            String type = null,first = null,last= null,phone= null,email= null,bal = null,fee= null,minBal = null,intRate = null;
                            for (DataSnapshot details : dataSnapshot.getChildren()) {
                                 type = details.child("AccType").getValue(String.class);
                                 first = details.child("FirstName").getValue(String.class);
                                 last = details.child("LastName").getValue(String.class);
                                 phone = details.child("PhoneNumber").getValue(String.class);
                                 email = details.child("Email").getValue(String.class);
                                 bal = details.child("Balance").getValue(String.class);

                            }
                            // type being set to null
                            type = dataSnapshot.child("AccType").getValue(String.class);
                            Log.d("acct",type);
                            if(type.equals("s"))
                            {
                                Log.d("types","sav");
                                minBal = dataSnapshot.child("MinBalance").getValue(String.class);
                                intRate = dataSnapshot.child("InterestRate").getValue(String.class);
                            }
                            if (type.equals("c")) {
                                Log.d("types","chq");
                                fee = dataSnapshot.child("Fee").getValue(String.class);
                                System.out.print(fee);
                            }
                            if(type.equals("s")){
                                Log.d("types","sav2");
                                acc = new Savings(Double.parseDouble(minBal),Double.parseDouble(intRate));
                                acc.accNum = accNumber;
                                acc.balance = Double.parseDouble(bal);
                                acc.accHolder = new Person(first,last,email,Long.parseLong(phone));
                                accounts.add(acc);
                            }else if (type.equals("c")){
                                Log.d("types","chq1");
                                acc = new Chequing(Double.parseDouble(fee));
                                acc.accNum = accNumber;
                                acc.balance = Double.parseDouble(bal);
                                acc.accHolder = new Person(first,last,email,Long.parseLong(phone));
                                accounts.add(acc);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    }
            );

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

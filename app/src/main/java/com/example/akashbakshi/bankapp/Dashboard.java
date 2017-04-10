package com.example.akashbakshi.bankapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by akashbakshi on 2017-04-09.
 */

public class Dashboard extends AppCompatActivity{

    private int acc = -1;
    private TextView firstName,lastName,phoneNum,email,bal;

    private Button btnWithdraw,btnDeposit,btnMonthly;
    private EditText deposit,withdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        acc = getIntent().getExtras().getInt("Account Index");
        Log.d("index",Integer.toString(acc));
        firstName = (TextView) findViewById(R.id.tfFirName);
        lastName = (TextView)findViewById(R.id.tfLastName);
        phoneNum = (TextView)findViewById(R.id.tfPhone);
        email = (TextView)findViewById(R.id.tfEmail);
        bal = (TextView)findViewById(R.id.tfBal);

        //

        btnDeposit = (Button)findViewById(R.id.btnDeposit);
        btnMonthly = (Button)findViewById(R.id.btnRunMonthly);
        btnWithdraw = (Button)findViewById(R.id.btnWithdraw);

        firstName.setText(MainActivity.accounts.get(acc).accHolder.getFirstName().toString());
        lastName.setText(MainActivity.accounts.get(acc).accHolder.getLastName().toString());
        phoneNum.setText(MainActivity.accounts.get(acc).accHolder.getPhoneNum().toString());
        email.setText(MainActivity.accounts.get(acc).accHolder.getEmail().toString());
        bal.setText(MainActivity.accounts.get(acc).balance.toString().toString());


        deposit = (EditText)findViewById(R.id.tfDepositAmnt);
        withdraw = (EditText)findViewById(R.id.tfWidthdrawAmnt);
        btnDeposit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.accounts.get(acc).balance += Integer.parseInt(deposit.getText().toString());
                        bal.setText(MainActivity.accounts.get(acc).balance.toString().toString());
                        deposit.setText(null);
                    }
                }
        );

        btnWithdraw.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int amnt = Integer.parseInt(withdraw.getText().toString());
                        if(amnt<=MainActivity.accounts.get(acc).balance){
                            MainActivity.accounts.get(acc).balance -= amnt;
                            bal.setText(MainActivity.accounts.get(acc).balance.toString().toString());
                            withdraw.setText(null);

                        }else{
                            Toast t = Toast.makeText(getApplicationContext(),"Not Enough Funds!",Toast.LENGTH_LONG);
                            t.show();

                        }
                    }
                }
        );

        btnMonthly.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!MainActivity.accounts.get(acc).monthlyUpdate())
                        {
                            Toast t = Toast.makeText(getApplicationContext(),"Not Enough Funds to run monthly update",Toast.LENGTH_LONG);
                            t.show();
                        }
                        bal.setText(MainActivity.accounts.get(acc).balance.toString().toString());

                    }
                }
        );
    }
}

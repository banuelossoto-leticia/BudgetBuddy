package com.example.cecs448;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class TransactionUpdatedConfirmationActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_updated_confirmation);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                //this method will be executed once the timer is over
                //this is for the splash screen, start app with HomeScreenActivity
                Intent i = new Intent(TransactionUpdatedConfirmationActivity.this, TransactionListActivity.class);
                startActivity(i);

                //closes this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

        //this is the button for the HomeScreenActivity from TransactionUpdatedConfirmationActivity
        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton8);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TransactionUpdatedConfirmationActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });

        //this the button for the PieChartActivity from TransactionUpdatedConfirmationActivity
        ImageButton pieChartButton = (ImageButton) findViewById(R.id.pieChartButton8);
        pieChartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TransactionUpdatedConfirmationActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });

        //this the button for the Transaction list from TransactionUpdatedConfirmationActivity
        ImageButton transactionList = (ImageButton) findViewById(R.id.transactionListButton8);
        transactionList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TransactionUpdatedConfirmationActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });
    }
}
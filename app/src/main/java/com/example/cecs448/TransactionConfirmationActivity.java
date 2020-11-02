package com.example.cecs448;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class TransactionConfirmationActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_confirmation);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                //this method will be executed once the timer is over
                //this is for the splash screen, start app with HomeScreenActivity
                Intent i = new Intent(TransactionConfirmationActivity.this, HomeScreenActivity.class);
                startActivity(i);

                //closes this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

        //this is the button for the pieChartActivity from AddTransactionActivity
        ImageButton pieChartButton = (ImageButton) findViewById(R.id.pieChartButton5);
        pieChartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TransactionConfirmationActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });

        //this the button for the transactionListActivity from AddTransactionActivity
        ImageButton transactionListButton = (ImageButton) findViewById(R.id.transactionListButton5);
        transactionListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TransactionConfirmationActivity.this, TransactionListActivity.class);
                startActivity(intent);
            }
        });
    }
}

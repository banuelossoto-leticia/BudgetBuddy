package com.example.cecs448;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TransactionListActivity extends AppCompatActivity {

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        //this is the button for the HomeScreenActivity from transactionListActivity
        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton3);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TransactionListActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });

        //this the button for the pieChartActivity from transactionListActivity
        ImageButton pieChartButton = (ImageButton) findViewById(R.id.pieChartButton3);
        pieChartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TransactionListActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });
    }
}
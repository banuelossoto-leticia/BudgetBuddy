package com.example.cecs448;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class HomeScreenActivity extends AppCompatActivity {
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //this is the button for the pieChartActivity from HomeScreenActivity
        ImageButton pieChartButton = (ImageButton) findViewById(R.id.pieChartButton);
        pieChartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HomeScreenActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });

        //this the button for the transactionListActivity from HomeScreenActivity
        ImageButton transactionListButton = (ImageButton) findViewById(R.id.transactionListButton);
        transactionListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HomeScreenActivity.this, TransactionListActivity.class);
                startActivity(intent);
            }
        });

        //this is the button for the AddTransactionActivity from HomeScreenActivity
        ImageButton addTransactionButton = (ImageButton) findViewById(R.id.addTransactionButton);
        addTransactionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HomeScreenActivity.this, AddTransactionActivity.class);
                startActivity(intent);
            }
        });
    }
}

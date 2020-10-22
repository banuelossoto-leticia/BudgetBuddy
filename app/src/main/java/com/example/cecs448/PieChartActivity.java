package com.example.cecs448;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class PieChartActivity extends AppCompatActivity {

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        //this is the button for the HomeScreenActivity from pieChartActivity
        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton2);
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PieChartActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });

        //this the button for the transactionListActivity from pieChartActivity
        ImageButton transactionListButton = (ImageButton) findViewById(R.id.transactionListButton2);
        transactionListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PieChartActivity.this, TransactionListActivity.class);
                startActivity(intent);
            }
        });
    }
}

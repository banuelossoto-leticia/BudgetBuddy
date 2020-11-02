package com.example.cecs448;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class TransactionListActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView transList;
    ImageButton homeBtn, pieBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);
        transList = findViewById(R.id.listOfTrans);
        homeBtn = findViewById(R.id.homeButton3);
        pieBtn = findViewById(R.id.pieChartButton3);

        transList.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        pieBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.listOfTrans:
                startActivity(new Intent(getApplicationContext(), TransactionDetails.class));
                finish();
                break;
            case R.id.homeButton3:
                startActivity(new Intent(getApplicationContext(), HomeScreenActivity.class));
                finish();
                break;
            case R.id.pieChartButton3:
                startActivity(new Intent(getApplicationContext(), PieChartActivity.class));
                finish();
                break;
        }
    }

}
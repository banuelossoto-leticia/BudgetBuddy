package com.example.cecs448;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class TransactionDetails extends AppCompatActivity implements View.OnClickListener {
    ImageView homeBtn, pieBtn, transList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
        transList = findViewById(R.id.transListBtn);
        homeBtn = findViewById(R.id.homeBtn);
        pieBtn = findViewById(R.id.pieBtn);

        transList.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        pieBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.transListBtn:
                startActivity(new Intent(getApplicationContext(), TransactionListActivity.class));
                finish();
                break;
            case R.id.homeBtn:
                startActivity(new Intent(getApplicationContext(), HomeScreenActivity.class));
                finish();
                break;
            case R.id.pieBtn:
                startActivity(new Intent(getApplicationContext(), PieChartActivity.class));
                finish();
                break;
        }
    }
}
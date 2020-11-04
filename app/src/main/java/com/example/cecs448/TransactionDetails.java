package com.example.cecs448;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class TransactionDetails extends AppCompatActivity implements View.OnClickListener {
    ImageView homeBtn, pieBtn;
    TextView totalTextView, actualCategoryTextView, actualNoteTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle bundle = getIntent().getExtras();
        final int transactionIndex = bundle.getInt("positionOfTransactionList");

        DecimalFormat df = new DecimalFormat("0.00");

        setContentView(R.layout.activity_transaction_details);
        homeBtn = findViewById(R.id.homeButton6);
        pieBtn = findViewById(R.id.pieChartButton6);
        totalTextView = findViewById(R.id.totalTextView);
        actualCategoryTextView = findViewById(R.id.actualCategoryTextView);
        actualNoteTextView2 = findViewById(R.id.actualNoteTextView2);

        //changing the text view totalTextView to the transaction that was clicked
        totalTextView.setText("$" + df.format(TransactionListActivity.
                transactions.get(transactionIndex).getAmount()));
        //changing the category name of the transaction that was clicked
        actualCategoryTextView.setText(TransactionListActivity.transactions.get(transactionIndex).getCategory());
        actualNoteTextView2.setText(TransactionListActivity.transactions.get(transactionIndex).getNote());

        homeBtn.setOnClickListener(this);
        pieBtn.setOnClickListener(this);

        //this is the back button, takes from TransactionDetails to TransactionListActivity
        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(TransactionDetails.this, TransactionListActivity.class);
                startActivity(intent);
            }
        });

        //this is the edit button, takes from TransactionDetails to EditTransactionActivity
        ImageButton editButton = (ImageButton) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionDetails.this, EditTransactionActivity.class);
                //attaching the transaction index to be deleted.
                intent.putExtra("indexOfRemovedTransaction", transactionIndex);
                startActivity(intent);
            }
        });

        //this is the delete button, will first delete the transaction out of the transaction list and takes
        //from TransactionDetails to TransactionListActivity
        ImageButton deleteButton = (ImageButton) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //deletes the current transaction from the transaction array list
                TransactionListActivity.transactions.remove(transactionIndex);
                Intent intent = new Intent(TransactionDetails.this, TransactionListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.homeButton6:
                startActivity(new Intent(getApplicationContext(), HomeScreenActivity.class));
                finish();
                break;
            case R.id.pieChartButton6:
                startActivity(new Intent(getApplicationContext(), PieChartActivity.class));
                finish();
                break;
        }
    }
}
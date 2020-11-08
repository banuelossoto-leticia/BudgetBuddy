package com.example.cecs448;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.text.DecimalFormat;

public class TransactionListActivity extends AppCompatActivity implements View.OnClickListener, TransactionsAdapter.OnTransactionListener {
    ImageButton homeBtn, pieBtn;
    RecyclerView rvTransaction;
    TextView actualTotalSpentTextView;
    double totalSpent = 0;

    TransactionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        homeBtn = findViewById(R.id.homeButton3);
        pieBtn = findViewById(R.id.pieChartButton3);

        homeBtn.setOnClickListener(this);
        pieBtn.setOnClickListener(this);

        //for the two place decimal
        DecimalFormat df = new DecimalFormat("0.00");

        //creating recycler view
        adapter = new TransactionsAdapter(HomeScreenActivity.filteredTransactions, getApplicationContext(),this);

        rvTransaction = (RecyclerView) findViewById(R.id.listRecycleView);
        rvTransaction.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvTransaction.setLayoutManager(manager);
        rvTransaction.setAdapter(adapter);

        //cvTransaction = (CardView) findViewById(R.id.cvTransaction);

        for(int i = 0; i < HomeScreenActivity.filteredTransactions.size(); i++){
            totalSpent = totalSpent + HomeScreenActivity.filteredTransactions.get(i).getAmount();
        }

        actualTotalSpentTextView = (TextView) findViewById(R.id.actualTotalSpentTextView);
        actualTotalSpentTextView.setText("$" + String.valueOf(df.format(totalSpent)));

        //this is the clickable filter transaction
        TextView newCategory = (TextView) findViewById(R.id.filterTextView);
        newCategory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), FilterTransactionPopUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
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

    @Override
    public void onTransactionClick(int position) {
        HomeScreenActivity.filteredTransactions.get(position);
        Intent intent = new Intent(TransactionListActivity.this, TransactionDetails.class);

        //attaching
        intent.putExtra("positionOfTransactionList", position);
        startActivity(intent);
    }
}
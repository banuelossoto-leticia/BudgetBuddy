package com.example.cecs448;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TransactionListActivity extends AppCompatActivity implements View.OnClickListener, TransactionsAdapter.OnTransactionListener {
    ImageButton homeBtn, pieBtn;
    RecyclerView rvTransaction;
    TextView actualTotalSpentTextView;
    double totalSpent = 0;
    ArrayList<Transaction> transactions;
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
        DecimalFormat df = new DecimalFormat("#.##");

        //creating dummy transactions
        transactions = new ArrayList<Transaction>();

        transactions.add(new Transaction("BILLS",45.34, "This is electricity bill"));
        transactions.add(new Transaction("BILLS",400.00, "this is rent"));
        transactions.add(new Transaction("FUN",23.23,"go-kart"));

        //creating recycler view
        //if it dosen't work try getApplicationContext()
        adapter = new TransactionsAdapter(transactions, getApplicationContext(),this);

        rvTransaction = (RecyclerView) findViewById(R.id.listRecycleView);
        rvTransaction.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvTransaction.setLayoutManager(manager);
        rvTransaction.setAdapter(adapter);

        //cvTransaction = (CardView) findViewById(R.id.cvTransaction);

        //sets the actualTotalSpentTextView to be total
        //loops through the transaction list and adds up the total

        for(int i = 0; i < transactions.size(); i++){
            totalSpent = totalSpent + transactions.get(i).getAmount();
        }

        actualTotalSpentTextView = (TextView) findViewById(R.id.actualTotalSpentTextView);
        actualTotalSpentTextView.setText("$" + String.valueOf(df.format(totalSpent)));


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
        transactions.get(position);
        Intent intent = new Intent(this, TransactionDetails.class);
        //attaching
        intent.putExtra("positionOfTransactionList",transactions.get(position));
        startActivity(intent);
    }
}
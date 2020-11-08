package com.example.cecs448;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileWriter;
import java.text.DecimalFormat;

public class TransactionDetails extends AppCompatActivity implements View.OnClickListener {
    ImageView homeBtn, pieBtn;
    TextView totalTextView, actualCategoryTextView, actualNoteTextView2, actualDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        final Bundle bundle = getIntent().getExtras();
        final int transactionIndex = bundle.getInt("positionOfTransactionList");

        DecimalFormat df = new DecimalFormat("0.00");

        homeBtn = findViewById(R.id.homeButton6);
        pieBtn = findViewById(R.id.pieChartButton6);
        totalTextView = findViewById(R.id.totalTextView);
        actualCategoryTextView = findViewById(R.id.actualCategoryTextView);
        actualNoteTextView2 = findViewById(R.id.actualNoteTextView2);
        actualDateTextView = findViewById(R.id.actualDateTextView);

        //changing the text view totalTextView to the transaction that was clicked
        totalTextView.setText("$" + df.format(HomeScreenActivity.transactions.get(transactionIndex).getAmount()));
        //changing the category name of the transaction that was clicked
        actualCategoryTextView.setText(HomeScreenActivity.transactions.get(transactionIndex).getCategory());
        actualNoteTextView2.setText(HomeScreenActivity.transactions.get(transactionIndex).getNote());
        actualDateTextView.setText(HomeScreenActivity.transactions.get(transactionIndex).getDateToString());

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
                //attaching the transaction index to be edited.
                intent.putExtra("indexToBeEdited", transactionIndex);
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
                HomeScreenActivity.transactions.remove(transactionIndex);

                //TODO: AFTER DELETING SAVE THE NEW TRANSACTION LIST INTO TEXT FILE
                rewriteExpenseTextFile();

                Intent intent = new Intent(TransactionDetails.this, TransactionListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void rewriteExpenseTextFile() {
        try {
            //define the file to save
            FileWriter file = new FileWriter(getFilesDir()+"expenses.txt");

            //write expense info. Placing category and note inside quotes because we are using a comma as delimiter (a note or category might contain a comma?)
            for (Transaction transaction : HomeScreenActivity.transactions){
                file.write(transaction.getAmount() + "," + transaction.getCategory() + "," + transaction.getDate() + "," + transaction.getNote() + "\n");
            }

            //closes the file
            file.close();
            //successful write toast
            Toast.makeText(getApplicationContext(),"Transaction was deleted!"+ getFilesDir(),Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            //do something if an IOException occurs.
            Toast.makeText(getApplicationContext(),"ERROR - Transaction could't be deleted",Toast.LENGTH_LONG).show();
        }
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
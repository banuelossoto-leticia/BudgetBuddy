package com.example.cecs448;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class FilterTransactionPopUpActivity extends Activity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_transaction_pop_up);

        //creates a hashSet of years that is available based of transaction history
        ArrayList<String> years = new ArrayList<String>();

        //loops through the transactions to add the years that are available to see in filter
        for(Transaction transaction : HomeScreenActivity.transactions){
            if(!years.contains(transaction.getDate().substring(0,4))){
                years.add(transaction.getDate().substring(0,4));
            }
        }

        ArrayList<String> months = new ArrayList<String>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        //creating the drop down menu in order to use it in code.
        final Spinner yearSpinner = (Spinner) findViewById(R.id.yearMenuPop);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),  R.layout.spinner_dropdown_layout, (List<String>) years);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        yearSpinner.setAdapter(adapter);
        yearSpinner.setOnItemSelectedListener(this);

        //creating the drop down menu in order to use it in code.
        final Spinner monthSpinner = (Spinner) findViewById(R.id.monthMenuPop);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(),  R.layout.spinner_dropdown_layout, (List<String>) months);
        adapter2.setDropDownViewResource( R.layout.spinner_dropdown_layout);
        monthSpinner.setAdapter(adapter2);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7), (int)(height*.25));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        //this is the button for the back button in popup menu
        ImageButton submitButton = (ImageButton) findViewById(R.id.submitButtonPop2);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int monthSelected = monthSpinner.getSelectedItemPosition() + 1;
                String yearSelected = yearSpinner.getItemAtPosition(yearSpinner.getSelectedItemPosition()).toString();

                //TODO: CALL getFilteredTransactions here
                HomeScreenActivity.filteredTransactions = HomeScreenActivity.getFilteredTransactions(String.valueOf(monthSelected), yearSelected);
                startActivity(new Intent(getApplicationContext(), TransactionListActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
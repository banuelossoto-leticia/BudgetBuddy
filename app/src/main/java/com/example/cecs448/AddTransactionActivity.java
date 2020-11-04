package com.example.cecs448;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class AddTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        //creating the drop down menu in order to use it in code.
        Spinner categoriesDropDownMenu = (Spinner) findViewById(R.id.categoryDropDownMenu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, HomeScreenActivity.categories);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        categoriesDropDownMenu.setAdapter(adapter);

        //creating the text views and edit texts to show if the user does not input all information needed to submit a transaction
        final TextView invalidInputAmountText = (TextView) findViewById(R.id.invalidInputAmountText);
        final TextView oppsText = (TextView) findViewById(R.id.oppsText);
        final TextView noNoteInputText = (TextView) findViewById(R.id.noNoteInputText);
        final EditText noteTextField = (EditText) findViewById(R.id.noteTextField);
        final EditText amountTextField = (EditText) findViewById(R.id.amountTextField);

        //this is the button for the pieChartActivity from AddTransactionActivity
        ImageButton pieChartButton = (ImageButton) findViewById(R.id.pieChartButton4);
        pieChartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AddTransactionActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });

        //this the button for the transactionListActivity from AddTransactionActivity
        ImageButton transactionListButton = (ImageButton) findViewById(R.id.transactionListButton4);
        transactionListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AddTransactionActivity.this, TransactionListActivity.class);
                startActivity(intent);
            }
        });

        //this is the button for the back_Button
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AddTransactionActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });

        //this is the submit_button
        ImageButton submitButton = (ImageButton) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                oppsText.setVisibility(View.INVISIBLE);
                noNoteInputText.setVisibility(View.INVISIBLE);
                invalidInputAmountText.setVisibility(View.INVISIBLE);

                //checking to see that edit text is filled out.
                if((TextUtils.isEmpty(noteTextField.getText().toString())) || (TextUtils.isEmpty(amountTextField.getText().toString()))){
                    //oppsText becomes visible because a user did not meet requirements.
                    oppsText.setVisibility(View.VISIBLE);
                    //checks to see which text field was invalid
                    if(TextUtils.isEmpty(noteTextField.getText().toString())){
                        noNoteInputText.setVisibility(View.VISIBLE);
                    }
                    if((TextUtils.isEmpty(amountTextField.getText().toString()))){
                        invalidInputAmountText.setVisibility(View.VISIBLE);
                    }

                }else{
                    //changes all errors to be invisible again
                    oppsText.setVisibility(View.INVISIBLE);
                    noNoteInputText.setVisibility(View.INVISIBLE);
                    invalidInputAmountText.setVisibility(View.INVISIBLE);

                    //moves onto the next page
                    Intent intent = new Intent(AddTransactionActivity.this, TransactionConfirmationActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}

package com.example.cecs448;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class AddTransactionActivity extends AppCompatActivity
{
    //save to .txt file on device. To see it, go to "Device file explorer" on the bottom right corner of the IDE, then data/user/0/com.example.cecs448/files
    private void saveExpense(String amount, String note, String category)
    {
        try
        {
            //create a timestamp for the entry
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH").format(new Date());
            //define the file to save
            File file=new File(getFilesDir()+"expenses.txt");
            //true indicates to append instead of overwrite
            FileOutputStream fileout=new FileOutputStream(file, true);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            //write expense info. Placing category and note inside quotes because we are using a comma as delimiter (a note or category might contain a comma?)
            outputWriter.write(amount+","+"\""+category+"\""+","+timeStamp+","+"\""+note+"\""+"\n");
            outputWriter.close();
            //successful write toast
            Toast.makeText(getApplicationContext(),"Text file Saved to!"+ getFilesDir(),Toast.LENGTH_LONG).show();
        }

        catch (java.io.IOException e)
        {
            //do something if an IOException occurs.
            Toast.makeText(getApplicationContext(),"ERROR - Text could't be added",Toast.LENGTH_LONG).show();
        }
    }

    //fills the file with dummy data
    public void generateRandomData()
    {

        for (int i=0;i<100;i++)
        {
            try
            {
                //generating a random amount to be saved
                float randomAmount = new Random().nextInt(300) + 1;

                //generating a random month for a timestamp
                int randomMonth = new Random().nextInt(12) + 1;

                //create a timestamp for the entry
                String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH").format(new Date(2020-1900,randomMonth,12));

                //define the file to save
                File file = new File(getFilesDir() + "budget.txt");
                //true indicates to append instead of overwrite
                FileOutputStream fileout = new FileOutputStream(file, true);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                //write expense info. Placing category and note inside quotes because we are using a comma as delimiter (a note or category might contain a comma?)
                outputWriter.write(randomAmount+","+"\""+"FOOD"+"\""+","+timeStamp+","+"\""+"TEST"+"\""+"\n");
                outputWriter.close();
            } catch (java.io.IOException e) {
                //do something if an IOException occurs.
                Toast.makeText(getApplicationContext(), "ERROR - Text could't be added", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        generateRandomData();

        //dummy data for the categories list
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("BILLS");
        categories.add("CLOTHES");
        categories.add("FOOD");
        categories.add("FUN");
        categories.add("OTHER");
        categories.add("ADD NEW CATEGORY");

        //creating the drop down menu in order to use it in code.
        final Spinner categoriesDropDownMenu = (Spinner) findViewById(R.id.categoryDropDownMenu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, categories);
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
        pieChartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddTransactionActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });

        //this the button for the transactionListActivity from AddTransactionActivity
        ImageButton transactionListButton = (ImageButton) findViewById(R.id.transactionListButton4);
        transactionListButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddTransactionActivity.this, TransactionListActivity.class);
                startActivity(intent);
            }
        });

        //this the button for the HomeScreenActivity from addBudgetActivity
        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton4);
        homeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddTransactionActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });


        //this is the button for the back_Button
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddTransactionActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });

        //this is the submit_button
        ImageButton submitButton = (ImageButton) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                oppsText.setVisibility(View.INVISIBLE);
                noNoteInputText.setVisibility(View.INVISIBLE);
                invalidInputAmountText.setVisibility(View.INVISIBLE);

                //checking to see that edit text is filled out.
                if((TextUtils.isEmpty(noteTextField.getText().toString())) || (TextUtils.isEmpty(amountTextField.getText().toString())))
                {
                    //oppsText becomes visible because a user did not meet requirements.
                    oppsText.setVisibility(View.VISIBLE);
                    //checks to see which text field was invalid
                    if(TextUtils.isEmpty(noteTextField.getText().toString()))
                    {
                        noNoteInputText.setVisibility(View.VISIBLE);
                    }
                    if((TextUtils.isEmpty(amountTextField.getText().toString())))
                    {
                        invalidInputAmountText.setVisibility(View.VISIBLE);
                    }
                }

                else {
                    //changes all errors to be invisible again
                    oppsText.setVisibility(View.INVISIBLE);
                    noNoteInputText.setVisibility(View.INVISIBLE);
                    invalidInputAmountText.setVisibility(View.INVISIBLE);

                    //save expense
                    saveExpense(amountTextField.getText().toString(),noteTextField.getText().toString(),categoriesDropDownMenu.getItemAtPosition(categoriesDropDownMenu.getSelectedItemPosition()).toString());

                    //moves onto the next page
                    Intent intent = new Intent(AddTransactionActivity.this, TransactionConfirmationActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}

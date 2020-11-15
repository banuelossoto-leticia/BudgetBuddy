package com.example.cecs448;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateGoalActivity extends AppCompatActivity
{
    private TextView limit, newCategory;
    private Spinner category;
    private ImageButton back, submit;

    private void saveGoal()
    {
        try
        {
            //create a timestamp for the entry
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH").format(new Date());
            //define the file to save
            File file = new File(getFilesDir() + "goals.txt");
            //true indicates to append instead of overwrite
            FileOutputStream fileout = new FileOutputStream(file, true);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            //write goal info. Placing the goal name and limit, separated by a comma as a delimiter
            outputWriter.write(limit.getText().toString() + "," + category.getItemAtPosition(category.getSelectedItemPosition()) + "," + timeStamp +"\n");
            outputWriter.close();
            //successful write toast
            Toast.makeText(getApplicationContext(), "Goal saved!" + getFilesDir(), Toast.LENGTH_LONG).show();
        }
        catch (java.io.IOException e)
        {
            //do something if an IOException occurs.
            Toast.makeText(getApplicationContext(),"ERROR - goal could't be added",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);

        back=findViewById(R.id.back_button2);
        submit=findViewById(R.id.submit_button2);
        limit=findViewById(R.id.goal_amount);
        category=findViewById(R.id.category);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_dropdown_layout, HomeScreenActivity.categories);
        adapter.setDropDownViewResource( R.layout.spinner_dropdown_layout);
        category.setAdapter(adapter);

        //this is the clickable create new category
        newCategory = (TextView) findViewById(R.id.createNewCategoryTextView2);
        newCategory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), CreateNewCategoryPopUpActivity.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(limit.length()>0)
                {
                    saveGoal();
                    startActivity(new Intent(getApplicationContext(), GoalConfirmation.class));
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), GoalsActivity.class));
            }
        });

        //this is the button for the pieChartActivity from AddTransactionActivity
        ImageButton pieChartButton = (ImageButton) findViewById(R.id.pieChartButton6);
        pieChartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CreateGoalActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });

        //this the button for the transactionListActivity from AddTransactionActivity
        ImageButton transactionListButton = (ImageButton) findViewById(R.id.transactionListButton6);
        transactionListButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CreateGoalActivity.this, TransactionListActivity.class);
                startActivity(intent);
            }
        });

        //this the button for the HomeScreenActivity from addBudgetActivity
        ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton6);
        homeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CreateGoalActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });
    }
}

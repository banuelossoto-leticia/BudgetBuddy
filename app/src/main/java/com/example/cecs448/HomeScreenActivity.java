package com.example.cecs448;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;


public class HomeScreenActivity extends AppCompatActivity
{
    private Spinner monthDropDown,yearDropDown;
    private ImageView barGraph;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //binding xml elements
        monthDropDown=findViewById(R.id.yearMonthDropDownMenu);
        barGraph=findViewById(R.id.bar_graph);

        //initial image for bar graph
        barGraph.setImageDrawable(getResources().getDrawable(R.drawable.bar_graph_jan));

        //array holding all the options for the spinner
        String[] months = new String[]{"January", "February","March","April","May","June","July","August","September","October","November","December"};
        //creates an adapter with the months
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, months);

        //changes the image depending on the month selected
        monthDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Object item = adapterView.getItemAtPosition(i);
                if(item.toString()=="January")
                {
                    barGraph.setImageDrawable(getResources().getDrawable(R.drawable.bar_graph_jan));
                }
                if(item.toString()=="February")
                {
                    barGraph.setImageDrawable(getResources().getDrawable(R.drawable.bar_graph_feb));
                }
                if(item.toString()=="March")
                {
                    barGraph.setImageDrawable(getResources().getDrawable(R.drawable.bar_graph_march));
                }
                if(item.toString()=="April")
                {
                    barGraph.setImageDrawable(getResources().getDrawable(R.drawable.bar_graph_april));
                }
                if(item.toString()=="May")
                {
                    barGraph.setImageDrawable(getResources().getDrawable(R.drawable.bar_graph_may));
                }
                if(item.toString()=="June")
                {
                    barGraph.setImageDrawable(getResources().getDrawable(R.drawable.bar_graph_june));
                }
                if(item.toString()=="July")
                {
                    barGraph.setImageDrawable(getResources().getDrawable(R.drawable.bar_graph_july));
                }
                if(item.toString()=="August")
                {
                    barGraph.setImageDrawable(getResources().getDrawable(R.drawable.bar_graph_august));
                }
                if(item.toString()=="September")
                {
                    barGraph.setImageDrawable(getResources().getDrawable(R.drawable.bar_graph_sept));
                }
                if(item.toString()=="October")
                {
                    barGraph.setImageDrawable(getResources().getDrawable(R.drawable.bar_graph_october));
                }
                if(item.toString()=="November")
                {
                    barGraph.setImageDrawable(getResources().getDrawable(R.drawable.bar_graph_nov));
                }
                if(item.toString()=="December")
                {
                    barGraph.setImageDrawable(getResources().getDrawable(R.drawable.bar_graph_dec));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
            }
        });

        //provides the adapter for the spinner
        monthDropDown.setAdapter(adapter);

        //this is the button for the pieChartActivity from HomeScreenActivity
        ImageButton pieChartButton = (ImageButton) findViewById(R.id.pieChartButton);
        pieChartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HomeScreenActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });

        //this the button for the transactionListActivity from HomeScreenActivity
        ImageButton transactionListButton = (ImageButton) findViewById(R.id.transactionListButton);
        transactionListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HomeScreenActivity.this, TransactionListActivity.class);
                startActivity(intent);
            }
        });

        //this is the button for the AddTransactionActivity from HomeScreenActivity
        ImageButton addTransactionButton = (ImageButton) findViewById(R.id.addTransactionButton);
        addTransactionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HomeScreenActivity.this, AddTransactionActivity.class);
                startActivity(intent);
            }
        });
    }
}

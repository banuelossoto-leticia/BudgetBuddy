package com.example.cecs448;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner monthDropDown,yearDropDown;
    private String[] months, years; //array holding all the options for the spinner
    private int curMonthSelected, curYearSelected; //variables for user selected time on dropdown
    private String curMonth, curYear; // today month and year
    private BarChart barChart;
    private ArrayList<BarEntry> barEntries; //to store all spending entries for bar graph
    private float budget, spending;  //for bar graph
    private TextView budgetLabel, spentLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //get time for NOW
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH").format(new Date());
        curMonth = timeStamp.substring(5,7);
        curYear = timeStamp.substring(0,4);

        curMonthSelected = -1;
        curYearSelected = -1;
        budget = -1;
        spending = -1;

        //binding xml elements
        monthDropDown=findViewById(R.id.yearMonthDropDownMenu);
        yearDropDown=findViewById(R.id.specificYearMonthDropDownMenu);
        years=new String[]{"2020"};
        //creates an adapter with the years
        ArrayAdapter<String> adapterYears = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
        months = new String[]{"January", "February","March","April","May","June","July","August","September","October","November","December"};
        //creates an adapter with the months
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, months);

        //provides the adapter for the spinner
        monthDropDown.setAdapter(adapter);
        yearDropDown.setAdapter(adapterYears);

        // linking class variables to Views on layout files
        barChart = (BarChart)findViewById(R.id.bar_graph);
        budgetLabel = findViewById(R.id.budgetLabel);
        spentLabel = findViewById(R.id.spentLabel);
        ImageButton pieChartButton = (ImageButton) findViewById(R.id.pieChartButton);
        ImageButton transactionListButton = (ImageButton) findViewById(R.id.transactionListButton);
        ImageButton addTransactionButton = (ImageButton) findViewById(R.id.addTransactionButton);

        //adding listener to buttons
        pieChartButton.setOnClickListener(this);
        transactionListButton.setOnClickListener(this);
        addTransactionButton.setOnClickListener(this);

        //don't show view unless there is data inputted
        setBarGraphVisibility(View.INVISIBLE);

    }

    @Override
    protected void onStart() {
        super.onStart();

        monthDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setBarGraphVisibility(View.INVISIBLE);   //don't show view unless there is data inputted
                Object item = parent.getItemAtPosition(position);
                monthListener(item); // user selected a new month
                displayBarGraph();
                barChart.invalidate(); // refreshing chart after changes
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {} //default override
        });

        setTime(); // set month and year drop down adapter to the current month and year
        displayBarGraph();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pieChartButton:
                startActivity(new Intent(getApplicationContext(), PieChartActivity.class));
                finish();
                break;
            case R.id.transactionListButton:
                startActivity(new Intent(getApplicationContext(), TransactionListActivity.class));
                finish();
                break;
            case R.id.addTransactionButton:
                startActivity(new Intent(getApplicationContext(), AddTransactionActivity.class));
                finish();
                break;
        }
    }

    private void displayBarGraph(){
        calculateSpending();
        barEntries = new ArrayList<>();

        //bar graph entries
        barEntries.add(new BarEntry(0,budget));
        barEntries.add(new BarEntry(1,spending));

        //bar graph settings
        BarDataSet barDataSet = new BarDataSet(barEntries,"");
        barDataSet.setColors(new int[]{Color.parseColor("#9cd9a9"),Color.parseColor("#f0a3a3")});
        barChart.getLegend().setEnabled(false);
        Description d = new Description();
        d.setText("");
        barChart.setDescription(d); // hiding default description by passing in empty string
        BarData data = new BarData(barDataSet);
        data.setValueTextSize(25f);
        barChart.setData(data);

        // X Axis settings
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new ArrayList<String>())); // hiding x axis labels by giving it an empty arrayList
        xAxis.setCenterAxisLabels(true);

        // Y Axis settings
        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);

    }
    private void calculateSpending(){
        Scanner read = null;
        double totalSpent = 0;

        try{
            read = new Scanner(new File(getFilesDir()+"expenses.txt"));
        }catch (Exception e){
            System.out.println("");
        }

        while(read.hasNext()){
            String cur = read.nextLine();
            String curArr[] = cur.split(",");

            String year = curArr[2].substring(0,4);
            String month = curArr[2].substring(5,7);
            String amountSpent = curArr[0];
            //see if there is data for user selected month and year
            if(year.equals(String.valueOf(curYearSelected)) && month.equals(String.valueOf(curMonthSelected))){
                totalSpent += Double.valueOf(amountSpent);
            }
        }
        if(totalSpent != 0) {
            spending = (float) totalSpent;
            budget = 500.0f;
            setBarGraphVisibility(View.VISIBLE); //show barGraph
        }

    }
    private void monthListener(Object o){
        String month = o.toString();
        curYearSelected = 2020;

        if(month.equals("January")){curMonthSelected = 1;}
        else if (month.equals("February")){ curMonthSelected = 2;}
        else if (month.equals("March")){ curMonthSelected = 3;}
        else if (month.equals("April")){ curMonthSelected = 4;}
        else if (month.equals("May")){ curMonthSelected = 5;}
        else if (month.equals("June")){ curMonthSelected = 6;}
        else if (month.equals("July")){ curMonthSelected = 7;}
        else if (month.equals("August")){ curMonthSelected = 8;}
        else if (month.equals("September")){ curMonthSelected = 9;}
        else if (month.equals("October")){ curMonthSelected = 10;}
        else if (month.equals("November")){ curMonthSelected = 11;}
        else if (month.equals("December")){ curMonthSelected = 12;}
    }
    private void setTime(){
        if(curMonth.equals("01")){monthDropDown.setSelection(0);}
        else if (curMonth.equals("02")){ monthDropDown.setSelection(1);}
        else if (curMonth.equals("03")){ monthDropDown.setSelection(2);}
        else if (curMonth.equals("04")){ monthDropDown.setSelection(3);}
        else if (curMonth.equals("05")){ monthDropDown.setSelection(4);}
        else if (curMonth.equals("06")){ monthDropDown.setSelection(5);}
        else if (curMonth.equals("07")){ monthDropDown.setSelection(6);}
        else if (curMonth.equals("08")){ monthDropDown.setSelection(7);}
        else if (curMonth.equals("09")){ monthDropDown.setSelection(8);}
        else if (curMonth.equals("10")){ monthDropDown.setSelection(9);}
        else if (curMonth.equals("11")){ monthDropDown.setSelection(10);}
        else if (curMonth.equals("12")){ monthDropDown.setSelection(11);}
    }

    private void setBarGraphVisibility(int visibility){
        budgetLabel.setVisibility(visibility);
        spentLabel.setVisibility(visibility);
        barChart.setVisibility(visibility);
    }
}



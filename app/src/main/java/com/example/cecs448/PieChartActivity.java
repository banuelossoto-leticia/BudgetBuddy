package com.example.cecs448;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class PieChartActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton homeButton, transactionListButton;
    private PieChart pieChart;
    private TextView monthLabel;
    private String[] months;
    private HashMap<String, Double> expenseEntries;
    private String curMonth, curYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        expenseEntries = new HashMap<>();

        //get time for NOW
        months = new String[]{"January", "February","March","April","May","June","July","August","September","October","November","December"};
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH").format(new Date());
        curMonth = timeStamp.substring(5,7);
        curYear = timeStamp.substring(0,4);

        // linking class variables to Views on layout files (xml files)
        homeButton = findViewById(R.id.homeButton2);
        transactionListButton = findViewById(R.id.transactionListButton2);
        pieChart = findViewById(R.id.pie_chart);
        monthLabel = findViewById(R.id.monthLabel);

        // add listener
        homeButton.setOnClickListener(this);
        transactionListButton.setOnClickListener(this);

        pieChart.setVisibility(View.INVISIBLE);
        monthLabel.setText(months[Integer.valueOf(curMonth)-1]);

        displayPieChart();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeButton2:
                startActivity(new Intent(getApplicationContext(), HomeScreenActivity.class));
                finish();
                break;
            case R.id.transactionListButton2:
                startActivity(new Intent(getApplicationContext(), TransactionListActivity.class));
                finish();
                break;
        }
    }

    private void displayPieChart() {
        calculateSpending();

        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(58f);
        pieChart.setEntryLabelColor(Color.BLACK);

        ArrayList<PieEntry> entries = new ArrayList<>();

        if(!expenseEntries.isEmpty()){
            for(String name: expenseEntries.keySet()) {
                String key = name.toString();
                double value = expenseEntries.get(name);
                entries.add(new PieEntry((float)value, key));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries,": CATEGORY");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextColor(Color.parseColor("#1800fc"));
        data.setValueTextSize(15f);

        pieChart.setData(data);

        if(!entries.isEmpty()){
            pieChart.setVisibility(View.VISIBLE);
        }

    }

    private void calculateSpending() {
        Scanner read = null;

        try {
            read = new Scanner(new File(getFilesDir() + "expenses.txt"));

            while (read.hasNext()) {
                String cur = read.nextLine();
                String curArr[] = cur.split(",");

                String year = curArr[2].substring(0, 4);
                String month = curArr[2].substring(5, 7);

                //if month is 06 then make it just 6
                if (month.substring(0, 1).equals("0")) {
                    month = month.substring(1, 2);
                }

                String amountSpent = curArr[0];
                String category = curArr[1];

                //getting rid for the " "
                if(category.substring(0,1).equals("\"")){
                    category = category.substring(1, category.length()-1);
                }

                //see if there is data for user selected month and year
                if (year.equals(curYear) && month.equals(curMonth)) {
                        //if there is already an entry for this category
                        if(expenseEntries.containsKey(category)){
                            Double curAmount = expenseEntries.get(category);
                            curAmount += Double.valueOf(amountSpent);
                            expenseEntries.put(category,curAmount);

                        }else{
                            expenseEntries.put(category,Double.valueOf(amountSpent));
                        }
                }
            }
            }catch(Exception e) {
                    System.out.println("/\nexpenses.txt doesn't exist");
            }
        }

}




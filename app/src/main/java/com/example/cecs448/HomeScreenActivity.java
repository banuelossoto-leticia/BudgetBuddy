package com.example.cecs448;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner monthDropDown,yearDropDown;
    private String[] months, years; //array holding all the options for the spinner
    private int curMonthSelected, curYearSelected; //variables for user selected time on dropdown
    private String curMonth, curYear; // today month and year
    private BarChart barChart;
    private ArrayList<BarEntry> barEntries; //to store all spending entries for bar graph
    private float income, spending;  //for bar graph
    private TextView budgetLabel, spentLabel;

    //this is for that transaction list is available throughout app
    static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    //this is for categories to be available throughout app
    static ArrayList<String> categories = new ArrayList<String>();
    //this is for filtered transactions to show on transaction list //used in transaction list activity
    static ArrayList<Transaction> filteredTransactions = new ArrayList<Transaction>();

    //checks to see if categories file exists or not
    private void openCategoryFile()  {
        try {
            //define the file to save
            File file = new File(getFilesDir()+"categories.txt");

            boolean exist = file.exists();
            //boolean exist = file.createNewFile();

            /**checks to see if the file categories.txt file exists. if it does
            not then it will create it. if it exists then it will open a file*/
            if(exist){
                Scanner reader = new Scanner(file);

                //while there is a category in the text file it will while loop
                while(reader.hasNext()){
                    String category = reader.nextLine();

                    if(!categories.contains(category)){
                        categories.add(category);
                    }
                }

                //closes file
                reader.close();
            }else{

                //true indicates to append instead of overwrite
                FileOutputStream fileOut = new FileOutputStream(file, true);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileOut);

                //writes new categories into the file
                outputWriter.write("BILLS\n");
                outputWriter.write("CLOTHES\n");
                outputWriter.write("FOOD\n");
                outputWriter.write("FUN\n");
                outputWriter.write("OTHER\n");
                outputWriter.write("MISC\n");

                //closes the file
                outputWriter.close();

                //successful write toast
                Toast.makeText(getApplicationContext(),"Text file Saved to!"+ getFilesDir(),Toast.LENGTH_LONG).show();

                //adds default to categories
                if(!categories.contains("BILLS")){
                    categories.add("BILLS");
                }
                if(!categories.contains("CLOTHES")){
                    categories.add("CLOTHES");
                }
                if(!categories.contains("FOOD")){
                    categories.add("FOOD");
                }
                if(!categories.contains("FUN")){
                    categories.add("FUN");
                }
                if(!categories.contains("MISC")){
                    categories.add("MISC");
                }
            }
        } catch (java.io.IOException e) {
            //do something if an IOException occurs.
            Toast.makeText(getApplicationContext(),"ERROR with categories.txt",Toast.LENGTH_LONG).show();
        }
    }

    //put it here so that it only opens it once
    private void openExpenseFile(){

        Scanner read = null;

        try {
            read = new Scanner(new File(getFilesDir()+"expenses.txt"));
        }catch (Exception e) {
            System.out.println("");
        }

        while(read.hasNext()) {
            String cur = read.nextLine();
            String curArr[] = cur.split(",");

            String amountSpentString = curArr[0];
            String category = curArr[1];
            String date = curArr[2];
            String note = curArr[3];

            Double amountSpent = Double.parseDouble(amountSpentString);

            //saves transactions from text file into transactions object
            Transaction transaction = new Transaction(category, amountSpent, note, date);

            //adds the transaction into the arrayList transactions
            transactions.add(transaction);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //this opens the file and saves the information into transactions
        if(SplashScreenActivity.appIsOpenFirstTime){
            //openExpenseFile();
            SplashScreenActivity.appIsOpenFirstTime = false;
        }

        //opens the categories.txt file
        openCategoryFile();

        //get time for NOW
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH").format(new Date());
        curMonth = timeStamp.substring(5,7);
        curYear = timeStamp.substring(0,4);

        curMonthSelected = -1;
        curYearSelected = 2020;
        income = -1;
        spending = -1;

        //sets the filteredTransactions
        filteredTransactions = getFilteredTransactions(curMonth, curYear);

        //binding xml elements
        monthDropDown=findViewById(R.id.yearMonthDropDownMenu);
        yearDropDown=findViewById(R.id.specificYearMonthDropDownMenu);
        years=new String[]{"2020"};
        //creates an adapter with the years
        ArrayAdapter<String> adapterYears = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
        months = new String[]{"January", "February","March","April","May","June","July","August","September","October","November","December"};
        //creates an adapter with the months
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, months);

        // linking class variables to Views on layout files (xml files)
        monthDropDown=findViewById(R.id.yearMonthDropDownMenu);
        yearDropDown=findViewById(R.id.specificYearMonthDropDownMenu);
        barChart = (BarChart)findViewById(R.id.bar_graph);
        budgetLabel = findViewById(R.id.budgetLabel);
        spentLabel = findViewById(R.id.spentLabel);
        ImageButton pieChartButton = (ImageButton) findViewById(R.id.pieChartButton);
        ImageButton transactionListButton = (ImageButton) findViewById(R.id.transactionListButton);
        ImageButton addTransactionButton = (ImageButton) findViewById(R.id.addTransactionButton);
        ImageButton addIncomeButton = (ImageButton) findViewById(R.id.addIncomeButton);
        ImageView goalsButton=(ImageView) findViewById(R.id.goals);

        //provides the adapter for the spinner
        monthDropDown.setAdapter(adapter);
        yearDropDown.setAdapter(adapterYears);

        //adding listener to buttons
        pieChartButton.setOnClickListener(this);
        transactionListButton.setOnClickListener(this);
        addTransactionButton.setOnClickListener(this);
        addIncomeButton.setOnClickListener(this);
        goalsButton.setOnClickListener(this);

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
                spending = 0;
                income = 0;
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
            case R.id.addIncomeButton:
                startActivity(new Intent(getApplicationContext(), AddBudgetActivity.class));
                finish();
                break;
            case R.id.goals:
                startActivity(new Intent(getApplicationContext(), GoalsActivity.class));
                break;
        }
    }

    private void displayBarGraph() {
        calculateSpending();
        calculateIncome();

        barEntries = new ArrayList<>();

        //bar graph entries
        barEntries.add(new BarEntry(0,income));
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
        xAxis.setEnabled(false);

        // Y Axis settings
        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);
        leftAxis.setEnabled(false);
    }

    private void calculateIncome(){
        Scanner read = null;
        double totalIncome = 0;

        try{
            read = new Scanner(new File(getFilesDir()+"budget.txt"));

            while (read.hasNext()) {
                String cur = read.nextLine();
                String curArr[] = cur.split(",");

                String year = curArr[1].substring(0, 4);
                String month = curArr[1].substring(5, 7);

                //if month is 06 then make it just 6
                if (month.substring(0, 1).equals("0")) {
                    month = month.substring(1, 2);
                }

                String amountIncome = curArr[0];

                //see if there is data for user selected month and year
                if (year.equals(String.valueOf(curYearSelected)) && month.equals(String.valueOf(curMonthSelected))) {
                    totalIncome += Double.valueOf(amountIncome);
                }
            }
            if (totalIncome != 0) {
                income = (float) totalIncome;
                setBarGraphVisibility(View.VISIBLE); //show barGraph
            }


        }catch (Exception e){
            System.out.println("\nbudget.txt doesn't exist");
        }
    }

    private void calculateSpending() {
        Scanner read = null;
        double totalSpent = 0;

        try {
            read = new Scanner(new File(getFilesDir()+"expenses.txt"));

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

                //see if there is data for user selected month and year
                if (year.equals(String.valueOf(curYearSelected)) && month.equals(String.valueOf(curMonthSelected))) {
                    totalSpent += Double.valueOf(amountSpent);
                }
            }
            if (totalSpent != 0) {
                spending = (float) totalSpent;
                setBarGraphVisibility(View.VISIBLE); //show barGraph
            }
        }catch (Exception e){
            System.out.println("\nexpenses.txt doesn't exist");
        }
    }

    private void monthListener(Object o) {
        String month = o.toString();

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


    public static ArrayList<Transaction> getFilteredTransactions(String month, String year){

        //will carry the filtered ArrayList
        ArrayList<Transaction> tempTransactions = new ArrayList<Transaction>();

        //will loop through the transactions arrayList and only get the transactions with the required month and year
        for(Transaction transaction: HomeScreenActivity.transactions){
            if(transaction.getDate().substring(0,4).equals(year) && transaction.getDate().substring(5,7).equals(month)){
                tempTransactions.add(transaction);
            }
        }

        return tempTransactions;
    }
}
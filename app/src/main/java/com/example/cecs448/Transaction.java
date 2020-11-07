package com.example.cecs448;

import androidx.annotation.NonNull;

import java.io.Serializable;

//implements serializable because it will get use in multiple activities
public class Transaction implements Serializable {
    //private classes
    private String category;
    private Double amount;
    private String note;
    private String date;

    public Transaction(String cate, Double amoun, String not, String dat){
        this.category = cate;
        this.amount = amoun;
        this.note = not;
        this.date = dat;
    }

    public String getCategory(){
        return this.category;
    }

    public Double getAmount(){
        return this.amount;
    }

    public String getNote(){
        return this.note;
    }

    public String getDate(){
        return this.date;
    }

    public String getDateToString(){

        String monthNumber = this.date.substring(5,7);
        String month;

        //depending on the month number, it will display the month
        switch(monthNumber){
            case "1":
                month = "January ";
                break;
            case "2":
                month = "February ";
                break;
            case "3":
                month = "March ";
                break;
            case "4":
                month = "April ";
                break;
            case "5":
                month = "May ";
                break;
            case "6":
                month = "June ";
                break;
            case "7":
                month = "July ";
                break;
            case "8":
                month = "August ";
                break;
            case "9":
                month = "September ";
                break;
            case "10":
                month = "October ";
                break;
            case "11":
                month = "November ";
                break;
            case "12":
                month = "December ";
                break;
            default:
                month = "Error";
        }
        if(month.equals("error")){
            return month;
        }else{
            return month + this.date.substring(8,10) + ", " + this.date.substring(0,4);
        }
    }

    public void setCategory(String newCategory){
        this.category = newCategory;
    }

    public void setAmount(double newAmount){
        this.amount = newAmount;
    }

    public void setNote(String newNote){
        this.note = newNote;
    }

    public void setDate(String newDate){
        this.date = newDate;
    }
}
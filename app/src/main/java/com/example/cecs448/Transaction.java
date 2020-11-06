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
    private String dateString;

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
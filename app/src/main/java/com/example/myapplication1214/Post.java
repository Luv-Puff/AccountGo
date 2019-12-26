package com.example.myapplication1214;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Post implements Serializable {
    @Exclude private String id;
    private String item_name,type,user_id,date;
    private boolean income;
    private double amount;

    public Post(){

    }

    public Post(String item_name, String type, String user_id, String date, boolean income, double amount) {
        this.item_name = item_name;
        this.type = type;
        this.user_id = user_id;
        this.date = date;
        this.income = income;
        this.amount = amount;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getType() {
        return type;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getDate() {
        return date;
    }

    public boolean isIncome() {
        return income;
    }

    public double getAmount() {
        return amount;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

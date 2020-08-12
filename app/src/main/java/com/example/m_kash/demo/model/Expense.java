package com.example.m_kash.demo.model;

import android.text.TextUtils;

public class Expense extends RecyclerViewItem {
    public String id;
    public String category;
    public String month;
    public String amount;


    public Expense(String id,String category, String month, String amount) {
        this.id=id;
        this.amount = TextUtils.isEmpty(amount) ? "0" : amount;
        this.category = category;
        this.month = month;
    }


}

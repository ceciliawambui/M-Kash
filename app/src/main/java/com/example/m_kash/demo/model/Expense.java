package com.example.m_kash.demo.model;

import android.text.TextUtils;

public class Expense extends RecyclerViewItem {
    public String category;
    public String month;
    public String amount;


    public Expense(String category, String month, String amount) {
        this.amount = TextUtils.isEmpty(amount) ? "0" : amount;
        this.category = category;
        this.month = month;
    }


}

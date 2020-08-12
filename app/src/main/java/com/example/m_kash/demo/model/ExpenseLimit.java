package com.example.m_kash.demo.model;

public class ExpenseLimit {
   public String id;
    public  String limit;
    public String month;

    public ExpenseLimit(String id, String limit, String month) {
        this.id = id;
        this.limit = limit==null?"0":limit;
        this.month = month;
    }

    public ExpenseLimit() {
    }

}

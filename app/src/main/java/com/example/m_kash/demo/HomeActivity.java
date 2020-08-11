package com.example.m_kash.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.m_kash.R;
import com.example.m_kash.database.myDbAdapter;
import com.example.m_kash.demo.model.Expense;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    LinearLayout expenses, income;

    Spinner month;
    double totalExpenses = 0, totalIncome = 0, balance = 0;

    TextView incomeTv, expensesTv, balanceTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        expenses = findViewById(R.id.expenses);
//        budget = findViewById(R.id.budget);
        income = findViewById(R.id.income);

        month = findViewById(R.id.period);
        expensesTv = findViewById(R.id.total_expenses);
        incomeTv = findViewById(R.id.total_income);
        balanceTv = findViewById(R.id.balance);


        expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ExpensesActivity.class));
            }
        });

//        budget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), BudgetActivity.class));
//            }
//        });
        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), IncomeActivity.class));
            }
        });


        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //get expenses from the database
                getSummary();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("Home","Nothing selected");
//                getSummary();
            }
        });

    }

    void getSummary() {
        ArrayList<Expense> expenses = new myDbAdapter(HomeActivity.this)
                .getExpensesPerMonth(month.getSelectedItem().toString());
        totalExpenses=0;
        for (int i = 0; i < expenses.size(); i++) {
            totalExpenses = totalExpenses + Double.parseDouble(expenses.get(i).amount);
        }

        totalIncome = new myDbAdapter(HomeActivity.this).getIncome(month.getSelectedItem().toString());

        balance = totalIncome - totalExpenses;

        balanceTv.setText("" + balance);
        expensesTv.setText("" + totalExpenses);
        incomeTv.setText("" + totalIncome);


    }


}
package com.example.m_kash.demo;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.m_kash.R;
import com.example.m_kash.database.myDbAdapter;
import com.example.m_kash.demo.model.Expense;
import com.example.m_kash.demo.model.ExpenseLimit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    LinearLayout expenses, income, limit;

    Spinner month;
    double totalExpenses = 0, totalIncome = 0, balance = 0;

    TextView incomeTv, expensesTv, balanceTv, expenseDesc,expenseLimit,exceedLimit;


    EditText limitAmount;
    Button addLimit;
    Spinner limitMonth;
    DateFormat dateFormat = new SimpleDateFormat("MM");
    Date date = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        expenses = findViewById(R.id.expenses);
        limit = findViewById(R.id.limit);
        income = findViewById(R.id.income);


        month = findViewById(R.id.period);
        expensesTv = findViewById(R.id.total_expenses);
        incomeTv = findViewById(R.id.total_income);
        balanceTv = findViewById(R.id.balance);

        expenseDesc = findViewById(R.id.exp_limit_desc);
        expenseLimit = findViewById(R.id.expense_limit);
        exceedLimit = findViewById(R.id.exceed_limit);


        Log.d("Month", dateFormat.format(date));


        expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ExpensesActivity.class));
            }
        });

        limit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLimit();
            }
        });
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
                Log.i("Home", "Nothing selected");
//                getSummary();
            }
        });

    }

    void addLimit() {
        LayoutInflater li = LayoutInflater.from(HomeActivity.this);
//Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dialog_limit, null);

        limitAmount = confirmDialog.findViewById(R.id.exp_limit);
        addLimit = confirmDialog.findViewById(R.id.add_limit);
        limitMonth = confirmDialog.findViewById(R.id.month);
        AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
//Adding our dialo box to the view of alert dialog
        alert.setView(confirmDialog);
//Creating an alert dialog
        final AlertDialog alertDialog = alert.create();
        addLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(limitAmount.getText().toString())) {
                    limitAmount.setError("Limit must not be empty");
                    return;
                }
                new myDbAdapter(HomeActivity.this).createLimit(limitAmount.getText().toString(), limitMonth.getSelectedItem().toString());
                Toast.makeText(getApplicationContext(), "Limit for " + limitMonth.getSelectedItem().toString() + " has been added", Toast.LENGTH_LONG).show();
                limitAmount.setText("");
                alertDialog.dismiss();
            }
        });

        alertDialog.show();


    }

    void getSummary() {
        ArrayList<Expense> expenses = new myDbAdapter(HomeActivity.this)
                .getExpensesPerMonth(month.getSelectedItem().toString());
        totalExpenses = 0;
        for (int i = 0; i < expenses.size(); i++) {
            totalExpenses = totalExpenses + Double.parseDouble(expenses.get(i).amount);
        }

        totalIncome = new myDbAdapter(HomeActivity.this).getIncome(month.getSelectedItem().toString());

        balance = totalIncome - totalExpenses;

        balanceTv.setText("" + balance);
        expensesTv.setText("" + totalExpenses);
        incomeTv.setText("" + totalIncome);

        ExpenseLimit expenseLim= new myDbAdapter(HomeActivity.this).getExpenditureLimit(month.getSelectedItem().toString());
        if(expenseLim!=null){
            expenseDesc.setVisibility(View.VISIBLE);
            expenseLimit.setVisibility(View.VISIBLE);
            expenseLimit.setText(""+expenseLim.limit==null?"0":expenseLim.limit);
            expenseDesc.setText("Expenditure Limit for "+month.getSelectedItem().toString());
        }else{
            expenseDesc.setVisibility(View.GONE);
            expenseLimit.setVisibility(View.GONE);
        }
        double amount=expenseLim.limit==null?0:Double.parseDouble(expenseLim.limit);
        if(totalExpenses>amount){
            exceedLimit.setText("You have exceeded your expenditure limit.");
            exceedLimit.setTextColor(Color.parseColor("#E50000"));
        }else{
            exceedLimit.setText("You are within your expenditure limit.");
            exceedLimit.setTextColor(Color.parseColor("#007F00"));
        }



    }


}
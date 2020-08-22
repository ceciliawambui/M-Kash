package com.example.m_kash.demo;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.m_kash.R;
import com.example.m_kash.database.myDbAdapter;
import com.example.m_kash.demo.model.Expense;
import com.example.m_kash.demo.model.ExpenseLimit;
import com.example.m_kash.demo.utils.DateUtils;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    LinearLayout expenses, income, limit;

    //    Spinner month;
    double totalExpenses = 0, totalIncome = 0, balance = 0;

    TextView incomeTv, expensesTv, balanceTv, expenseDesc, expenseLimit, exceedLimit;


    EditText limitAmount;
    Button addLimit;
    DateFormat dateFormat = new SimpleDateFormat("MM");
    Date date = new Date();

    TextView period, periodTv;
    ImageView chooseMonth;
    int yearSelected;
    int monthSelected;
    LinearLayout datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        expenses = findViewById(R.id.expenses);
        limit = findViewById(R.id.limit);
        income = findViewById(R.id.income);

        new myDbAdapter(HomeActivity.this);


        expensesTv = findViewById(R.id.total_expenses);
        incomeTv = findViewById(R.id.total_income);
        balanceTv = findViewById(R.id.balance);

        expenseDesc = findViewById(R.id.exp_limit_desc);
        expenseLimit = findViewById(R.id.expense_limit);
        exceedLimit = findViewById(R.id.exceed_limit);


        datePicker = findViewById(R.id.date_layout);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMonthYear(true, periodTv);
            }
        });
        yearSelected = DateUtils.getYear();
        monthSelected = DateUtils.getMonth();

        periodTv = findViewById(R.id.period);
        periodTv.setText(DateUtils.getMonth(monthSelected) + " " + yearSelected);

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

        getSummary();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getSummary();
    }

    public void getMonthYear(final boolean isExpense, final TextView period) {
        Calendar calendar = Calendar.getInstance();
        yearSelected = calendar.get(Calendar.YEAR);
        monthSelected = calendar.get(Calendar.MONTH);

        MonthYearPickerDialogFragment dialogFragment = MonthYearPickerDialogFragment
                .getInstance(monthSelected, yearSelected);

        dialogFragment.show(getSupportFragmentManager(), null);
        dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int year, int monthOfYear) {
                // do something
                int month = monthOfYear + 1;
                monthSelected = month;
                yearSelected = year;
                period.setText(DateUtils.getMonth(month) + " " + year);
                if (isExpense) {
                    getSummary();
                }

                //get month
            }
        });
    }

    void addLimit() {
        LayoutInflater li = LayoutInflater.from(HomeActivity.this);
//Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dialog_limit, null);

        limitAmount = confirmDialog.findViewById(R.id.exp_limit);
        addLimit = confirmDialog.findViewById(R.id.add_limit);

        period = confirmDialog.findViewById(R.id.period);

        yearSelected = DateUtils.getYear();
        monthSelected = DateUtils.getMonth();

        period.setText(DateUtils.getMonth(monthSelected) + " " + yearSelected);
        chooseMonth = confirmDialog.findViewById(R.id.choose_date);
        chooseMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMonthYear(false, period);
            }
        });


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
                new myDbAdapter(HomeActivity.this)
                        .createLimit(limitAmount.getText().toString(),
                                String.valueOf(monthSelected),
                                String.valueOf(yearSelected));
                Toast.makeText(getApplicationContext(), "Limit for " + period.getText().toString() + " has been added", Toast.LENGTH_LONG).show();
                limitAmount.setText("");
                alertDialog.dismiss();
            }
        });

        alertDialog.show();


    }

    void getSummary() {
        ArrayList<Expense> expenses = new myDbAdapter(HomeActivity.this)
                .getExpensesPerMonth(String.valueOf(monthSelected), String.valueOf(yearSelected));
        totalExpenses = 0;
        for (int i = 0; i < expenses.size(); i++) {
            totalExpenses = totalExpenses + Double.parseDouble(expenses.get(i).amount);
        }

        totalIncome = new myDbAdapter(HomeActivity.this).getIncome(String.valueOf(monthSelected), String.valueOf(yearSelected));

        balance = totalIncome - totalExpenses;

        balanceTv.setText("" + balance);
        expensesTv.setText("" + totalExpenses);
        incomeTv.setText("" + totalIncome);

        ExpenseLimit expenseLim = new myDbAdapter(HomeActivity.this).getExpenditureLimit(String.valueOf(monthSelected), String.valueOf(yearSelected));
        if (expenseLim != null) {
            expenseDesc.setVisibility(View.VISIBLE);
            expenseLimit.setVisibility(View.VISIBLE);
            expenseLimit.setText("" + expenseLim.limit == null ? "0" : expenseLim.limit);
            expenseDesc.setText("Expenditure Limit for " + DateUtils.getMonth(monthSelected) + " " + yearSelected);
        } else {
            expenseDesc.setVisibility(View.GONE);
            expenseLimit.setVisibility(View.GONE);
        }
        double amount = expenseLim.limit == null ? 0 : Double.parseDouble(expenseLim.limit);
        if (totalExpenses > amount) {
            exceedLimit.setText("You have exceeded your expenditure limit.");
            exceedLimit.setTextColor(Color.parseColor("#E50000"));
        } else {
            exceedLimit.setText("You are within your expenditure limit.");
            exceedLimit.setTextColor(Color.parseColor("#007F00"));
        }


    }


}
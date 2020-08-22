package com.example.m_kash;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m_kash.database.myDbAdapter;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MenuItem item;
    private TextView monthlyExpenseLabel, budgetBalanceLabel, monthlyIncomeLabel, monthlyBudgetLabel;
    private ImageView monthlyExpense, budgetBalance, monthlyIncome, monthlyBudget;
    private EditText amount;
    private Spinner period;
    private Button okay, Cancel;
    private Spinner month;
    private EditText category;
    private Button ok, cancel;
    private ListView listView;
    private Button Clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        monthlyExpense = findViewById(R.id.monthlyExpense);
        monthlyBudget = findViewById(R.id.monthlyBudget);
        budgetBalance = findViewById(R.id.budgetBalance);
        monthlyIncome = findViewById(R.id.monthlyIncome);
        monthlyExpenseLabel = findViewById(R.id.monthlyExpenseLabel);
        monthlyIncomeLabel = findViewById(R.id.monthlyIncomeLabel);
        budgetBalanceLabel = findViewById(R.id.budgetBalanceLabel);


        monthlyExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LayoutInflater li = LayoutInflater.from(Home.this);
//Creating a view to get the dialog box
                View confirmDialog = li.inflate(R.layout.activity_monthly_expenses, null);
                amount = confirmDialog.findViewById (R.id.amount);
                category = confirmDialog.findViewById (R.id.category);
                month = confirmDialog.findViewById (R.id.period);
                okay = confirmDialog.findViewById (R.id.submit1);
                cancel = confirmDialog.findViewById (R.id.Cancel);
                AlertDialog.Builder alert = new AlertDialog.Builder(Home.this);
//Adding our dialo box to the view of alert dialog
                alert.setView(confirmDialog);
//Creating an alert dialog
                final AlertDialog alertDialog = alert.create();
//Displaying the alert dialog
                alertDialog.show();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.hide();
                    }
                });
                okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String amounts = amount.getText().toString();
                        String categorys = category.getText().toString();
                        String months = month.getSelectedItem().toString();


                        myDbAdapter mydbadapter = new myDbAdapter(Home.this);
                        mydbadapter.insertMonthlyExpenses(amounts, categorys, months,"");

                        Toast.makeText(Home.this, "Succefylly Added Expense",Toast.LENGTH_LONG).show();

                    }



                });




            }
        });
        monthlyIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(Home.this);
//Creating a view to get the dialog box
                View confirmDialog = li.inflate(R.layout.activity_monthly_income, null);
                amount = confirmDialog.findViewById(R.id.amount);
                period = confirmDialog.findViewById(R.id.period);

                Cancel = confirmDialog.findViewById(R.id.Cancel);
                okay = confirmDialog.findViewById (R.id.submit);
                AlertDialog.Builder alert = new AlertDialog.Builder(Home.this);
//Adding our dialo box to the view of alert dialog
                alert.setView(confirmDialog);
//Creating an alert dialog
                final AlertDialog alertDialog = alert.create();
//Displaying the alert dialog
                alertDialog.show();

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.hide();
                    }
                });

                okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String amounts = amount.getText().toString();
                        String periods = period.getSelectedItem().toString();


                        myDbAdapter mydbadapter = new myDbAdapter(Home.this);
                        mydbadapter.insertMonthlyIncome(amounts, periods,"");
                        Toast.makeText(Home.this, "Succefylly Added Income",Toast.LENGTH_LONG).show();

                    }
                });


            }
        });
        budgetBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(Home.this);
//Creating a view to get the dialog box
                View confirmDialog = li.inflate(R.layout.activity_budget_balance, null);
                period = confirmDialog.findViewById(R.id.period);
                listView = confirmDialog.findViewById(R.id.listView);
                Cancel = confirmDialog.findViewById(R.id.Cancel);
                okay = confirmDialog.findViewById (R.id.Okay);
                Clear = confirmDialog.findViewById (R.id.Clear);
                AlertDialog.Builder alert = new AlertDialog.Builder(Home.this);
//Adding our dialo box to the view of alert dialog
                alert.setView(confirmDialog);
//Creating an alert dialog
                final AlertDialog alertDialog = alert.create();
                alertDialog.show();
//Displaying the alert dialog
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.hide();
                    }
                });
                Clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDbAdapter mydbadapter = new myDbAdapter(Home.this);
                        mydbadapter.delete(period.getSelectedItem().toString());

                        Toast.makeText(Home.this,"Record Deleted Succesfully", Toast.LENGTH_LONG).show();
                    }
                });
                okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDbAdapter mydbadapter = new myDbAdapter(Home.this);
                        final ArrayList array_listExpenses = mydbadapter.getAllBalanceRecords();
                        final ArrayAdapter arrayAdapter = new ArrayAdapter(Home.this,android.R.layout.simple_list_item_1, array_listExpenses);
                        listView.setAdapter(arrayAdapter);
                    }
                });


            }
        });
        monthlyBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(Home.this);
//Creating a view to get the dialog box
                View confirmDialog = li.inflate(R.layout.activity_budget, null);
                amount = confirmDialog.findViewById(R.id.amount);
                period = confirmDialog.findViewById(R.id.period);
                category = confirmDialog.findViewById(R.id.category);
                Cancel = confirmDialog.findViewById(R.id.Cancel);
                okay = confirmDialog.findViewById (R.id.Okay);
                AlertDialog.Builder alert = new AlertDialog.Builder(Home.this);
//Adding our dialo box to the view of alert dialog
                alert.setView(confirmDialog);
//Creating an alert dialog
                final AlertDialog alertDialog = alert.create();
//Displaying the alert dialog
                alertDialog.show();

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.hide();
                    }
                });

                okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String amounts = amount.getText().toString();
                        String categorys = category.getText().toString();
                        String periods = period.getSelectedItem().toString();


                        myDbAdapter mydbadapter = new myDbAdapter(Home.this);
                        mydbadapter.insertBudget(amounts, periods, categorys,"");
                        Toast.makeText(Home.this, "Succefylly Added Income",Toast.LENGTH_LONG).show();

                    }
                });


            }
        });

    }
}

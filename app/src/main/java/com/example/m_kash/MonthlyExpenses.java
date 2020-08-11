package com.example.m_kash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.m_kash.database.SqliteHelper;
import com.example.m_kash.database.myDbAdapter;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class MonthlyExpenses extends AppCompatActivity {


    EditText amount;
    EditText category;
    Spinner month;
    Button ok;
    Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_expenses);
        amount = findViewById (R.id.amount);
        category = findViewById (R.id.category);
        month = findViewById (R.id.period);
        ok = findViewById (R.id.submit);
        cancel = findViewById (R.id.Cancel);
        myDbAdapter mydbadapter = new myDbAdapter(MonthlyExpenses.this);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
            String amounts = amount.getText().toString();
            String categorys = category.getText().toString();
            String months = month.getSelectedItem().toString();


            myDbAdapter mydbadapter = new myDbAdapter(MonthlyExpenses.this);



        });





    }

   
}

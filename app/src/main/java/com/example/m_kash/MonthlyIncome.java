package com.example.m_kash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.m_kash.database.myDbAdapter;

public class MonthlyIncome extends AppCompatActivity {
    EditText amount;
    Spinner month;
    Button ok;
    Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_expenses);
        amount = findViewById (R.id.amount);
        month = findViewById (R.id.period);
        ok = findViewById (R.id.submit);
        cancel = findViewById (R.id.Cancel);
        myDbAdapter mydbadapter = new myDbAdapter(MonthlyIncome.this);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
            String amounts = amount.getText().toString();
            String months = month.getSelectedItem().toString();


            myDbAdapter mydbadapter = new myDbAdapter(MonthlyIncome.this);



        });





    }


}

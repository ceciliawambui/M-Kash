package com.example.m_kash;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.m_kash.database.myDbAdapter;

public class Budget extends AppCompatActivity {

    EditText amount;
    EditText category;
    Spinner month;
    Button ok;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        amount = findViewById(R.id.amount);
        category = findViewById(R.id.category);
        month = findViewById(R.id.period);
        ok = findViewById(R.id.submit);
        cancel = findViewById(R.id.Cancel);
        myDbAdapter mydbadapter = new myDbAdapter(Budget.this);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

            String amounts = amount.getText().toString();
            String categorys = category.getText().toString();
            String months = month.getSelectedItem().toString();


            myDbAdapter mydbadapter = new myDbAdapter(Budget.this);


        });
    }
}

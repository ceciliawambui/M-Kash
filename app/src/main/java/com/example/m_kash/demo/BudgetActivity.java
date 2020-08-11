package com.example.m_kash.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m_kash.Home;
import com.example.m_kash.R;
import com.example.m_kash.database.myDbAdapter;
import com.example.m_kash.demo.model.Expense;
import com.example.m_kash.demo.model.ExpenseHeader;
import com.example.m_kash.demo.model.RecyclerViewItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BudgetActivity extends AppCompatActivity {
    FloatingActionButton add;
    RecyclerView recyclerView;
    TextView error;

    EditText amount;
    EditText category;
    Button okay;
    Spinner period;
    private Button  Cancel;
    Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget2);


        add = findViewById(R.id.add_budget);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add expense
                addBudget();
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        error=findViewById(R.id.error);

        getIncomes();

    }

    void getIncomes(){
        ArrayList<RecyclerViewItem> recyclerViewItems = new ArrayList<>();

        ArrayList<Expense> incomes =  new myDbAdapter(BudgetActivity.this).getIncomes();
        recyclerViewItems.clear();
//        recyclerViewItems.add(new ExpenseHeader(String.valueOf(total)));
        recyclerViewItems.addAll(incomes);

        if(recyclerViewItems.size()==0){
            error.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(this);
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(MyLayoutManager);
        MyAdapter bottomAdapter = new MyAdapter(BudgetActivity.this, recyclerViewItems);
        recyclerView.setAdapter(bottomAdapter);
    }

    private void  addBudget(){
        LayoutInflater li = LayoutInflater.from(BudgetActivity.this);
//Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.activity_budget, null);
        amount = confirmDialog.findViewById(R.id.amount);
        period = confirmDialog.findViewById(R.id.period);
        category = confirmDialog.findViewById(R.id.category);
        Cancel = confirmDialog.findViewById(R.id.Cancel);
        okay = confirmDialog.findViewById (R.id.Okay);
        AlertDialog.Builder alert = new AlertDialog.Builder(BudgetActivity.this);
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


                myDbAdapter mydbadapter = new myDbAdapter(BudgetActivity.this);
                mydbadapter.insertBudget(amounts, periods, categorys);
                Toast.makeText(BudgetActivity.this, "Successfully Added Income",Toast.LENGTH_LONG).show();
                alertDialog.hide();
            }
        });

    }

    public class MyAdapter extends RecyclerView.Adapter {
        public static final int BODY_ITEM = 3;
        public static final int HEADER_ITEM = 4;
        List<RecyclerViewItem> recyclerViewItems;
        Context context;

        public MyAdapter(Context context, List<RecyclerViewItem> recyclerViewItems) {
            this.recyclerViewItems = recyclerViewItems;
            this.context = context;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View row;



            if (viewType == BODY_ITEM) {
                row = inflater.inflate(R.layout.list_item_expense, parent, false);
                return new MyAdapter.BodyHolder(row);

            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            RecyclerViewItem recyclerViewItem = recyclerViewItems.get(position);
            //Check holder instance to populate data  according to it
            if (holder instanceof IncomeActivity.MyAdapter.BodyHolder) {
                IncomeActivity.MyAdapter.BodyHolder bodyHolder = (IncomeActivity.MyAdapter.BodyHolder) holder;
                final Expense expense = (Expense) recyclerViewItem;
                Log.i("Expense",expense.category);
                Log.i("Expense",expense.amount);

                bodyHolder.category.setText(expense.month);
                bodyHolder.amount.setText(expense.amount);


            }

        }


        @Override
        public int getItemViewType(int position) {
            //here we can set view type
            RecyclerViewItem recyclerViewItem = recyclerViewItems.get(position);
            //if its header then return header item
            if (recyclerViewItem instanceof ExpenseHeader)
                return HEADER_ITEM;
            else if (recyclerViewItem instanceof Expense)
                return BODY_ITEM;
            else
                return super.getItemViewType(position);

        }

        @Override
        public int getItemCount() {
            return recyclerViewItems.size();
        }


        public class BodyHolder extends RecyclerView.ViewHolder {

            TextView amount, category;

            public BodyHolder(View v) {
                super(v);
//                name = v.findViewById(R.id.name);
                category = v.findViewById(R.id.category);
                amount = v.findViewById(R.id.amount);

            }
        }

        public class HeaderHolder extends RecyclerView.ViewHolder {

            TextView total;
            Spinner month;


            public HeaderHolder(View v) {
                super(v);
                month = v.findViewById(R.id.period);
                total = v.findViewById(R.id.total);
            }
        }


    }
}
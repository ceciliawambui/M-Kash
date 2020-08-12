package com.example.m_kash.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_kash.R;
import com.example.m_kash.database.myDbAdapter;
import com.example.m_kash.demo.model.Expense;
import com.example.m_kash.demo.model.ExpenseHeader;
import com.example.m_kash.demo.model.RecyclerViewItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ExpensesActivity extends AppCompatActivity {
    EditText amount;
    EditText category;

    Button okay;
    Spinner month;
    Button cancel;
    FloatingActionButton add;
    RecyclerView recyclerView;
    TextView error;

    TextView total;
    Spinner selectedMonth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        setTitle("My Expenses");
        add = findViewById(R.id.add_expense);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add expense
                addExpense();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        error=findViewById(R.id.error);

        selectedMonth = findViewById(R.id.period);
        total = findViewById(R.id.total);
        selectedMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //get expenses from the database
                Log.i("Expense",selectedMonth.getAdapter().getItem(position).toString())  ;
                if(selectedMonth.getSelectedItem()!=null){
                    String month=selectedMonth.getAdapter().getItem(position).toString();
                    ArrayList<Expense> expenses = new myDbAdapter(ExpensesActivity.this).getExpensesPerMonth(month);
                    getExpenses(expenses);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//init list
        getExpenses(new ArrayList<Expense>());

    }

    private void addExpense() {


        LayoutInflater li = LayoutInflater.from(ExpensesActivity.this);
//Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.activity_monthly_expenses, null);
        amount = confirmDialog.findViewById(R.id.amount);
        category = confirmDialog.findViewById(R.id.category);
        month = confirmDialog.findViewById(R.id.period);
        okay = confirmDialog.findViewById(R.id.submit1);
        cancel = confirmDialog.findViewById(R.id.Cancel);
        AlertDialog.Builder alert = new AlertDialog.Builder(ExpensesActivity.this);
//Adding our dialo box to the view of alert dialog
        alert.setView(confirmDialog);
//Creating an alert dialog
        final AlertDialog alertDialog = alert.create();
//Displaying the alert dialog
        alertDialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amounts = amount.getText().toString();
                String categorys = category.getText().toString();
                String months = month.getSelectedItem().toString();


                myDbAdapter mydbadapter = new myDbAdapter(ExpensesActivity.this);
                mydbadapter.insertMonthlyExpenses(amounts, categorys, months);
                alertDialog.dismiss();
                Toast.makeText(ExpensesActivity.this, "Successfully Added Expense", Toast.LENGTH_LONG).show();

            }


        });
    }

    private void getExpenses(ArrayList<Expense> expenses) {

        ArrayList<RecyclerViewItem> recyclerViewItems = new ArrayList<>();
        double sum = 0;
        for (int i = 0; i < expenses.size(); i++) {
            sum = sum + Double.parseDouble(expenses.get(i).amount);

        }
        total.setText(""+sum);
        recyclerViewItems.clear();
//        recyclerViewItems.add(new ExpenseHeader(String.valueOf(total)));
        recyclerViewItems.addAll(expenses);

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
        MyAdapter bottomAdapter = new MyAdapter(ExpensesActivity.this, recyclerViewItems);
        recyclerView.setAdapter(bottomAdapter);
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

            if (viewType == HEADER_ITEM) {
                row = inflater.inflate(R.layout.list_item_expense_header, parent, false);
                return new HeaderHolder(row);

            }

            if (viewType == BODY_ITEM) {
                row = inflater.inflate(R.layout.list_item_expense, parent, false);
                return new BodyHolder(row);

            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            RecyclerViewItem recyclerViewItem = recyclerViewItems.get(position);
            //Check holder instance to populate data  according to it
            if (holder instanceof HeaderHolder) {
                final  HeaderHolder headerHolder = (HeaderHolder) holder;
                ExpenseHeader header = (ExpenseHeader) recyclerViewItem;
                headerHolder.total.setText(header.total);

                headerHolder.month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //get expenses from the database
                        Log.i("Expense",headerHolder.month.getAdapter().getItem(position).toString())  ;
                        if(headerHolder.month.getSelectedItem()!=null){
                            String selectedMonth=headerHolder.month.getAdapter().getItem(position).toString();
                            ArrayList<Expense> expenses = new myDbAdapter(ExpensesActivity.this).getExpensesPerMonth(selectedMonth);
                            getExpenses(expenses);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            } else if (holder instanceof BodyHolder) {
                BodyHolder bodyHolder = (BodyHolder) holder;
                final Expense expense = (Expense) recyclerViewItem;
Log.i("Expense",expense.category);
                Log.i("Expense",expense.amount);

                bodyHolder.category.setText(expense.category);
                bodyHolder.amount.setText(expense.amount);

                bodyHolder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //delete from database
                        new myDbAdapter(ExpensesActivity.this).deleteExpense(expense.id);
                        //reload list
                        String month=selectedMonth.getSelectedItem().toString();
                        ArrayList<Expense> expenses = new myDbAdapter(ExpensesActivity.this).getExpensesPerMonth(month);
                        getExpenses(expenses);
                        Toast.makeText(ExpensesActivity.this,"Expense removed",Toast.LENGTH_LONG).show();
                    }
                });


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
            ImageButton remove;

            public BodyHolder(View v) {
                super(v);
//                name = v.findViewById(R.id.name);
                category = v.findViewById(R.id.category);
                amount = v.findViewById(R.id.amount);
                remove=v.findViewById(R.id.remove);

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
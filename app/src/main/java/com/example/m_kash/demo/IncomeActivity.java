package com.example.m_kash.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.m_kash.demo.utils.DateUtils;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class IncomeActivity extends AppCompatActivity {
    private static String TAG = IncomeActivity.class.getSimpleName();
    FloatingActionButton add;
    RecyclerView recyclerView;
    TextView error;

    EditText amount;
    EditText category;
    Button okay;
    TextView period;
    ImageView chooseMonth;
    int yearSelected;
    int monthSelected;

    private Button Cancel;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        setTitle("My Income");
        add = findViewById(R.id.add_income);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add expense
                addIncome();
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        error = findViewById(R.id.error);

        getIncomes();
    }

    void getIncomes() {
        ArrayList<RecyclerViewItem> recyclerViewItems = new ArrayList<>();

        ArrayList<Expense> incomes = new myDbAdapter(IncomeActivity.this).getIncomes();
        recyclerViewItems.clear();

        recyclerViewItems.addAll(incomes);

        if (recyclerViewItems.size() == 0) {
            error.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            return;
        }

        recyclerView.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(this);
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(MyLayoutManager);
        MyAdapter bottomAdapter = new MyAdapter(IncomeActivity.this, recyclerViewItems);
        recyclerView.setAdapter(bottomAdapter);
    }

//    I think some of the things amesema apo we had discussed about them like creating a budget.
//    What we can do is create a budget with items for each month, when adding expenses
//    choose from the list of budget items which will help you determine if a certain item
//    has exceeded its limit eg if food is 6k then it exceeds 6k it shows you.
//    Also we mentioned about year, should be added on the drop down as well.
//    A calendar would work well.


    public void getMonthYear() {
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

                Log.i(TAG, " Selected month " + month);
                period.setText(DateUtils.getMonth(month) + " " + year);
                //get month
            }
        });
    }


    private void addIncome() {

        LayoutInflater li = LayoutInflater.from(IncomeActivity.this);
//Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.activity_monthly_income, null);
        amount = confirmDialog.findViewById(R.id.amount);
        period = confirmDialog.findViewById(R.id.period);

        yearSelected = DateUtils.getYear();
        monthSelected = DateUtils.getMonth();

        period.setText(DateUtils.getMonth(monthSelected) + " " + yearSelected);


        chooseMonth = confirmDialog.findViewById(R.id.choose_date);
        chooseMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMonthYear();
            }
        });
        Cancel = confirmDialog.findViewById(R.id.Cancel);
        okay = confirmDialog.findViewById(R.id.submit);
        AlertDialog.Builder alert = new AlertDialog.Builder(IncomeActivity.this);
//Adding our dialo box to the view of alert dialog
        alert.setView(confirmDialog);
//Creating an alert dialog
        final AlertDialog alertDialog = alert.create();
//Displaying the alert dialog
        alertDialog.show();

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amounts = amount.getText().toString();
                myDbAdapter mydbadapter = new myDbAdapter(IncomeActivity.this);
                mydbadapter.insertMonthlyIncome(amounts, String.valueOf(monthSelected),String.valueOf(yearSelected));
                Toast.makeText(IncomeActivity.this, "Successfully Added Income", Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
                getIncomes();
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
                return new BodyHolder(row);

            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            RecyclerViewItem recyclerViewItem = recyclerViewItems.get(position);
            //Check holder instance to populate data  according to it
            if (holder instanceof BodyHolder) {
                BodyHolder bodyHolder = (BodyHolder) holder;
                final Expense expense = (Expense) recyclerViewItem;
                Log.i("Expense", expense.category);
                Log.i("Expense", expense.amount);

                bodyHolder.category.setText(DateUtils.getMonth(Integer.parseInt(expense.month)) + " " + expense.year);
                bodyHolder.amount.setText(expense.amount);

                bodyHolder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //delete from database
                        new myDbAdapter(IncomeActivity.this).deleteIncome(expense.id);
                        //reload list
                        getIncomes();
                        Toast.makeText(IncomeActivity.this, "Income removed", Toast.LENGTH_LONG).show();
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
                remove = v.findViewById(R.id.remove);
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
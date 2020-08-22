package com.example.m_kash.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.example.m_kash.demo.model.Expense;
import com.example.m_kash.demo.model.ExpenseLimit;

import java.util.ArrayList;

import static java.lang.String.valueOf;


@SuppressWarnings("ALL")
public class myDbAdapter {
    myDbHelper myhelper;

    public myDbAdapter(Context context) {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String name, String pass) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.MyPASSWORD, pass);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public long insertMonthlyExpenses(String amount, String category, String month, String year) {
//        String uniqueId = UUID.randomUUID().toString();
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.AMOUNT, amount);
        contentValues.put(myDbHelper.CATEGORY, category);
        contentValues.put(myDbHelper.MONTH, month);
        contentValues.put(myDbHelper.YEAR, year);
//        contentValues.put(myDbHelper.UNIQUE_ID, uniqueId);

        long id = dbb.insert(myDbHelper.MONTHLY_EXPENSES_TABLE, null, contentValues);
        return id;
    }

    public long insertBudget(String amount, String category, String month, String year) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.AMOUNT, amount);
        contentValues.put(myDbHelper.CATEGORY, category);
        contentValues.put(myDbHelper.MONTH, month);
        contentValues.put(myDbHelper.YEAR, year);
        long id = dbb.insert(myDbHelper.MONTHLY_EXPENSES_TABLE, null, contentValues);
        return id;
    }

    public long createLimit(String limit, String month, String year) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.LIMIT, limit);
        contentValues.put(myDbHelper.MONTH, month);
        contentValues.put(myDbHelper.YEAR, year);
        long id = dbb.insert(myDbHelper.EXPENDITURE_LIMIT_TABLE, null, contentValues);
        return id;
    }

    public long insertMonthlyIncome(String amount, String month, String year) {
//        String uniqueId = UUID.randomUUID().toString();

        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.AMOUNT, amount);
        contentValues.put(myDbHelper.MONTH, month);
        contentValues.put(myDbHelper.YEAR, year);
//        contentValues.put(myDbHelper.UNIQUE_ID, uniqueId);
        long id = dbb.insert(myDbHelper.MONTHLY_INCOME_TABLE, null, contentValues);
        return id;
    }

    public ArrayList getAllExpenseRecords() {
        SQLiteDatabase db = myhelper.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = db.rawQuery("select * from " + myDbHelper.MONTHLY_EXPENSES_TABLE, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(myDbHelper.AMOUNT)));
            array_list.add(res.getString(res.getColumnIndex(myDbHelper.MONTH)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Expense> getExpensesPerMonth(String month,String year) {
        ArrayList<Expense> expenses = new ArrayList<>();
        String selectQuery = "SELECT  *  FROM  " + myDbHelper.MONTHLY_EXPENSES_TABLE
                + " WHERE " + myDbHelper.MONTH + "='" + month + "' AND "+ myDbHelper.YEAR + "='" + year + "'";
        try {
            SQLiteDatabase db = myhelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Expense expense = new Expense(
                            cursor.getString(cursor.getColumnIndex(myDbHelper.UID)),
                            cursor.getString(cursor.getColumnIndex(myDbHelper.CATEGORY)),
                            cursor.getString(cursor.getColumnIndex(myDbHelper.MONTH)),
                            cursor.getString(cursor.getColumnIndex(myDbHelper.AMOUNT)),
                            cursor.getString(cursor.getColumnIndex(myDbHelper.YEAR)));

                    if (!TextUtils.isEmpty(expense.amount) && !TextUtils.isEmpty(expense.category))
                        expenses.add(expense);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.i("Db", Log.getStackTraceString(e));
        }
        return expenses;
    }

    public ExpenseLimit getExpenditureLimit(String month,String year) {
        ExpenseLimit expenseLimit = new ExpenseLimit();
        String selectQuery = "SELECT  *  FROM  " + myDbHelper.EXPENDITURE_LIMIT_TABLE
                + " WHERE " + myDbHelper.MONTH + "='" + month + "' AND "+ myDbHelper.YEAR + "='" + year + "'";
        try {
            SQLiteDatabase db = myhelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            Log.i("Db", DatabaseUtils.dumpCursorToString(cursor));

            if (cursor.moveToFirst()) {
                do {

                    ExpenseLimit limit = new ExpenseLimit(
                            cursor.getString(cursor.getColumnIndex(myDbHelper.UID)),
                            cursor.getString(cursor.getColumnIndex(myDbHelper.LIMIT)),
                            cursor.getString(cursor.getColumnIndex(myDbHelper.MONTH)),
                            cursor.getString(cursor.getColumnIndex(myDbHelper.YEAR)));


                    expenseLimit = limit;
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.i("Db", Log.getStackTraceString(e));
        }
        return expenseLimit;
    }


    public ArrayList<Expense> getIncomes() {
        ArrayList<Expense> expenses = new ArrayList<>();
        String selectQuery = "SELECT  *  FROM  " + myDbHelper.MONTHLY_INCOME_TABLE;
        try {
            SQLiteDatabase db = myhelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {

                    Expense expense = new Expense(
                            cursor.getString(cursor.getColumnIndex(myDbHelper.UID)),
                            "",
                            cursor.getString(cursor.getColumnIndex(myDbHelper.MONTH)),
                            cursor.getString(cursor.getColumnIndex(myDbHelper.AMOUNT)),
                            cursor.getString(cursor.getColumnIndex(myDbHelper.YEAR)));

                    if (!TextUtils.isEmpty(expense.amount))
                        expenses.add(expense);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.i("Db", Log.getStackTraceString(e));
        }
        return expenses;
    }

    public void deleteIncome(String id) {
        Log.i("Db", "Id " + id);
        try {
            SQLiteDatabase db = myhelper.getWritableDatabase();
            db.execSQL("DELETE FROM " + myDbHelper.MONTHLY_INCOME_TABLE + " WHERE " + myDbHelper.UID + " =\'" + id + "\'");
            Log.i("Db", "Item deleted");

        } catch (Exception e) {
            Log.i("Db", Log.getStackTraceString(e));
        }
    }

    public void deleteExpense(String id) {
        try {
            SQLiteDatabase db = myhelper.getWritableDatabase();
            db.execSQL("DELETE FROM " + myDbHelper.MONTHLY_EXPENSES_TABLE + " WHERE " + myDbHelper.UID + " =\'" + id + "\'");


        } catch (Exception e) {
            Log.i("Db", Log.getStackTraceString(e));
        }
    }


    public double getIncome(String month,String year) {
        double income = 0;
        String selectQuery = "SELECT  *  FROM  " + myDbHelper.MONTHLY_INCOME_TABLE
                + " WHERE " + myDbHelper.MONTH + "='" + month + "' AND "+ myDbHelper.YEAR + "='" + year + "'";
        ;
        try {

            SQLiteDatabase db = myhelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    income = Double.parseDouble(cursor.getString(cursor.getColumnIndex(myDbHelper.AMOUNT)));

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.i("Db", Log.getStackTraceString(e));
        }
        return income;
    }

    public ArrayList getAllExpenses() {

        SQLiteDatabase db = myhelper.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = db.rawQuery("select * from " + myDbHelper.MONTHLY_BUDGET_TABLE,
                null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(myDbHelper.AMOUNT)));
            array_list.add(res.getString(res.getColumnIndex(myDbHelper.CATEGORY)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList getAllBalanceRecords() {
        SQLiteDatabase db = myhelper.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = db.rawQuery("select * from " + myDbHelper.MONTHLY_EXPENSES_TABLE, null);
        Cursor resIncome = db.rawQuery("select * from " + myDbHelper.MONTHLY_INCOME_TABLE, null);
        res.moveToFirst();
        resIncome.moveToFirst();
        while (res.isAfterLast() == false && resIncome.isAfterLast() == false) {

            int incomeValue = resIncome.getInt(resIncome.getColumnIndex(myDbHelper.AMOUNT));
            int expenseValue = res.getInt(res.getColumnIndex(myDbHelper.AMOUNT));
            Log.d("heres we have income", String.valueOf(incomeValue));
            Log.d("here we have expenses", String.valueOf(expenseValue));
            int diff = incomeValue - expenseValue;
            Log.d("difference", valueOf(diff));
            array_list.add(valueOf(diff));
            array_list.add(res.getString(res.getColumnIndex(myDbHelper.MONTH)));
            res.moveToNext();
            resIncome.moveToNext();
        }
        return array_list;
    }

    public ArrayList getAllIncomeRecords() {
        SQLiteDatabase db = myhelper.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = db.rawQuery("select * from " + myDbHelper.MONTHLY_INCOME_TABLE, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(myDbHelper.AMOUNT)));
            array_list.add(res.getString(res.getColumnIndex(myDbHelper.MONTH)));
            res.moveToNext();
        }
        return array_list;
    }

    public long signUp(String name, String email, String pass) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.MyPASSWORD, pass);
        contentValues.put(myDbHelper.EMAIL, email);

        long id = dbb.insert(myDbHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public String getData() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.NAME, myDbHelper.MyPASSWORD};
        Cursor cursor = db.query(myDbHelper.TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int cid = cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name = cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            String password = cursor.getString(cursor.getColumnIndex(myDbHelper.MyPASSWORD));
            buffer.append(cid).append(" ").append(name).append(" ").append(password).append(" \n");
        }
        return buffer.toString();
    }

    public int delete(String uname) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs = {uname};

        int count = db.delete(myDbHelper.MONTHLY_EXPENSES_TABLE, myDbHelper.MONTH + " = ?", whereArgs);
        return count;
    }

    public int updateName(String oldName, String newName) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, newName);
        String[] whereArgs = {oldName};
        int count = db.update(myDbHelper.TABLE_NAME, contentValues, myDbHelper.NAME + " = ?", whereArgs);
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "myDatabase"; // Database Name
        private static final String TABLE_NAME = "myTable"; // Table Name
        private static final String MONTHLY_EXPENSES_TABLE = "monthlyExpenses"; // Table Name
        private static final String MONTHLY_INCOME_TABLE = "monthlyIncome"; // Table Name
        private static final String MONTHLY_BUDGET_TABLE = "monthlyBudget"; // Table Name
        private static final int DATABASE_Version = 7; // Database Version
        private static final String EXPENDITURE_LIMIT_TABLE = "ExpenditureLimit";
        private static final String UID = "_id"; // Column I (Primary Key)
        private static final String NAME = "Name"; //Column II
        private static final String MyPASSWORD = "Password"; // Column III
        private static final String EMAIL = "Email";
        private static final String AMOUNT = "Amount";

        private static final String LIMIT = "LimitAmount";


        //        private static final String UNIQUE_ID = "Id";
        private static final String CATEGORY = "Category";
        private static final String MONTH = "Month";
        private static final String YEAR = "Year";

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255) ," + MyPASSWORD + " VARCHAR(225)," + EMAIL + " VARCHAR(225));";
        private static final String MONTHLY_EXPENSES = "CREATE TABLE " + MONTHLY_EXPENSES_TABLE +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AMOUNT + " VARCHAR(255) ," + CATEGORY + " VARCHAR(225)," + YEAR + " VARCHAR(225)," + MONTH + " VARCHAR(225));";
        private static final String MONTHLY_INCOME = "CREATE TABLE " + MONTHLY_INCOME_TABLE +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AMOUNT + " VARCHAR(255) ," + YEAR + " VARCHAR(225)," + MONTH + " VARCHAR(225));";
        private static final String MONTHLY_BUDGET = "CREATE TABLE " + MONTHLY_BUDGET_TABLE +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AMOUNT + " VARCHAR(255) ," + CATEGORY + " VARCHAR(225)," + YEAR + " VARCHAR(225)," + MONTH + " VARCHAR(225));";


        private static final String LIMIT_CREATE_QUERY = "CREATE TABLE " + EXPENDITURE_LIMIT_TABLE +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LIMIT + " VARCHAR(255) ," + YEAR + " VARCHAR(225)," + MONTH + " VARCHAR(225));";


        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private static final String DROP_MONTHLY_EXPENSES_TABLE = "DROP TABLE IF EXISTS " + MONTHLY_EXPENSES_TABLE;
        private static final String DROP_MONTHLY_INCOME_TABLE = "DROP TABLE IF EXISTS " + MONTHLY_INCOME_TABLE;
        private static final String DROP_MONTHLY_BUDGET_TABLE = "DROP TABLE IF EXISTS " + MONTHLY_BUDGET_TABLE;
        private static final String DROP_EXPENDITURE_LIMIT_TABLE = "DROP TABLE IF EXISTS " + EXPENDITURE_LIMIT_TABLE;


        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
                db.execSQL(MONTHLY_EXPENSES);
                db.execSQL(MONTHLY_INCOME);
                db.execSQL(MONTHLY_BUDGET);
                db.execSQL(LIMIT_CREATE_QUERY);
            } catch (Exception e) {
                Log.i("Db",Log.getStackTraceString(e));
                Message.message(context, "" + e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context, "OnUpgrade");
                db.execSQL(DROP_TABLE);
                db.execSQL(DROP_MONTHLY_EXPENSES_TABLE);
                db.execSQL(DROP_MONTHLY_INCOME_TABLE);
                db.execSQL(DROP_MONTHLY_BUDGET_TABLE);
                db.execSQL(DROP_EXPENDITURE_LIMIT_TABLE);
                onCreate(db);
            } catch (Exception e) {
                Log.i("Db>>",Log.getStackTraceString(e));
                Message.message(context, "" + e);
            }
        }

    }
}
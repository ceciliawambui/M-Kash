package com.example.m_kash.demo.utils;

import android.util.Log;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by JahsonK on 11/16/2017.
 */

public class DateUtils {
    private static String TAG = DateUtils.class.getSimpleName();

    /**
     * @param orderDate
     * @return
     */
    public int timeSeparatorStyle(String orderDate) {
        try {

            //convert date to millis
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date convertedDate = new Date();
            convertedDate = sdf.parse(orderDate);
            //   LogCS.i(TAG, "Date in millis " + convertedDate.getTime());
            long time = convertedDate.getTime();
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTimeInMillis(time);
            cal2.setTimeInMillis(System.currentTimeMillis());

            boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
            if (sameDay) {
                //  LogCS.i(TAG, "Today");
                return 0;
            }
            boolean yesterday = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    (cal1.get(Calendar.DAY_OF_YEAR) + 1) == cal2.get(Calendar.DAY_OF_YEAR);
            if (yesterday) {
                //   LogCS.i(TAG, "Yesterday");
                return 1;
            }
            Date date6 = new Date(time);
            if (date6.after(new Date())) {
                //  LogCS.i(TAG, "Date post today....");
                return 2;
            }
//            boolean afterTomorrow = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
//                    (cal1.get(Calendar.DAY_OF_YEAR) - 1) == cal2.get(Calendar.DAY_OF_YEAR);
//
//            if (afterTomorrow) {
//                Toast.makeText(getActivity(), "Order date " + orderDate, Toast.LENGTH_LONG).show();
//             //   LogCS.i(TAG, "Incoming orders.");
//                return 2;
//            }

            Timestamp stamp = new Timestamp(time);
            Date date = new Date(stamp.getTime());
            //SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());
            //  LogCS.i(TAG, "Past orders");
            //return sdf.format(date);
            return 3;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static String getDay(String date) {
//        2019-09-19T21:00:00.000Z
        try {
//            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date d = outputFormat.parse(date);
//            String formattedDate = outputFormat.format(d);
            return new SimpleDateFormat("EE").format(d);
        } catch (Exception e) {
            Log.i(TAG, android.util.Log.getStackTraceString(e));
            return "Sun";
        }

    }

    public static String getMonth(int num) {
        num=num-1;
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;

    }

    public static int getMonth() {
        Calendar calendar = Calendar.getInstance(java.util.TimeZone.getDefault());

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH)+1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        return currentMonth;

    }

    public static int getYear() {
        Calendar calendar = Calendar.getInstance(java.util.TimeZone.getDefault());

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        return currentYear;

    }

    public static String date(String date) {
        try {
            //2017-08-30
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date convertedDate = new Date();
            convertedDate = sdf.parse(date);
            String dateStr = sdf.format(convertedDate);
            //return date;
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
            Date convertedDate1 = new Date();
            convertedDate1 = sdf.parse(date);

            return sdf2.format(convertedDate1);
        } catch (Exception e) {
            Log.i(TAG, android.util.Log.getStackTraceString(e));
            return date;
        }

    }

    public static String getTodaysDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String today = df.format(c);
        return today;
    }

    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String today = df.format(c);
        return today;
    }

    public static String getFirstDateOfTheMonth() {
        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println(c.getTime());

        Date date = c.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String firstDate = df.format(date);
        return firstDate;
    }


}

package com.roche.rapid.test.APIAutomation.CustomTests.cdsoar.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    static DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
    static Date currentDate = new Date();
    static Calendar calendar = Calendar.getInstance();

    public static String getCurrentDate() {
        return dateFormat.format(currentDate);
    }

    public static String getSpecificMonth(int month) {
        calendar.add(Calendar.MONTH, month);
        return dateFormat.format(calendar.getTime());
    }

    public static String getSpecificYear(int year) {
        calendar.add(Calendar.YEAR, year);
        return dateFormat.format(calendar.getTime());
    }

    public static String getSpecificDate(int date) {
        calendar.add(Calendar.DAY_OF_MONTH, date);
        return dateFormat.format(calendar.getTime());
    }

    }


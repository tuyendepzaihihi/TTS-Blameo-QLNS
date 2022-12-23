package com.example.tuyendv.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String DAY_OF_WEEK_FORMAT = "EEE";
    public static final SimpleDateFormat cmdateFormat = new SimpleDateFormat(DATE_FORMAT);
    public static final SimpleDateFormat cmdayFormat = new SimpleDateFormat(DAY_OF_WEEK_FORMAT);

    public static Integer getDayFromDate(Date date) {
        return Integer.valueOf(cmdateFormat.format(date).substring(0, 2));
    }

    public static Integer getMonthFromDate(Date date) {
        return Integer.valueOf(cmdateFormat.format(date).substring(3, 5));
    }

    public static Integer getYearFromDate(Date date) {
        return Integer.valueOf(cmdateFormat.format(date).substring(6, 10));
    }

    public static java.sql.Date toDateSql(Date date) {
        String dateStr = getYearFromDate(date) + "-" + getMonthFromDate(date) + "-" + getDayFromDate(date);
        return java.sql.Date.valueOf(dateStr);
    }

    public static Date toDate(String dateStr) throws ParseException {
        return cmdateFormat.parse(dateStr);
    }

    public static String getDayOfWeek(Date date) {
        return cmdayFormat.format(date);
    }

}

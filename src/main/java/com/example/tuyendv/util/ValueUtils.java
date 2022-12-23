package com.example.tuyendv.util;

public class ValueUtils {

    public static boolean isEmail(String email) {
        return email.matches("\\w+@\\w+(\\.\\w+){1,2}");
    }

    public static boolean isPhone(String phone) {
        return phone.matches("\0\\d{9,10}");
    }

    public static boolean isMonth(Integer month) {
        return month > 0 && month < 12;
    }

    public static boolean isYear(Integer year) {
        return year > 999 && year < 10000;
    }

}

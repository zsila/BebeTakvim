package com.sayar.sila.bebetakvim.com.sayar.sila.bebetakvim.util;

import java.util.Calendar;

/**
 * Created by zisan on 28.09.2016.
 */
public class CalendarUtil {

    static int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    static int minute = Calendar.getInstance().get(Calendar.MINUTE);
    static int second = Calendar.getInstance().get(Calendar.SECOND);
    static int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    static int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
    static int year = Calendar.getInstance().get(Calendar.YEAR);

    public static String today = CalendarUtil.getDay() + "/" + CalendarUtil.getMonth() + "/" + CalendarUtil.getYear();

    public static int getHour() {
        hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    public static int getMinute() {
        minute = Calendar.getInstance().get(Calendar.MINUTE);
        return minute;
    }

    public static int getSecond() {
        second = Calendar.getInstance().get(Calendar.SECOND);
        return second;
    }

    public static int getDay() {

        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public static int getMonth() {
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        return month;
    }

    public static int getYear() {
        year = Calendar.getInstance().get(Calendar.YEAR);
        return year;
    }

    public static String extractMonth(String date) {
        String[] items = date.split("\\/");
        if (items[0].equals("0"))
            return items[1].substring(1);
        else
            return items[1];
    }

    public static String extractYear(String date) {
        String[] items = date.split("\\/");
        return items[2];
    }

    public static String extractDay(String date) {
        String[] items = date.split("\\/");
        if (items[0].equals("0"))
            return items[1].substring(1);
        else
            return items[0];
    }


    public static String getMonthName(String date) {
        String name = null;
        String monthNum = extractMonth(date);
        if (monthNum.equals("1"))
            name = "Ocak";
        else if (monthNum.equals("2"))
            name = "Şubat";
        else if (monthNum.equals("3"))
            name = "Mart";
        else if (monthNum.equals("4"))
            name = "Nisan";
        else if (monthNum.equals("5"))
            name = "Mayıs";
        else if (monthNum.equals("6"))
            name = "Haziran";
        else if (monthNum.equals("7"))
            name = "Temmuz";
        else if (monthNum.equals("8"))
            name = "Ağustos";
        else if (monthNum.equals("9"))
            name = "Eylül";
        else if (monthNum.equals("10"))
            name = "Ekim";
        else if (monthNum.equals("11"))
            name = "Kasım";
        else if (monthNum.equals("12"))
            name = "Aralık";


        return name;
    }
}

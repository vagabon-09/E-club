package com.championclub_balirmath.com.ReusableCode;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
    public String Time(long timeStamp) {
        Date date = new Date(timeStamp);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        String time = simpleDateFormat.format(date);
        return time;
    }

    public String DayTime(long timeStamp) {
        Date date = new Date(timeStamp);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a");//EEEE represent full day name and hh represent hour and mm represent minute and a represent am/pm
        String time = simpleDateFormat.format(date);
        return time;
    }

    public String Date(long milliSecond) {
        Date date = new Date(milliSecond);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");//EEEE represent full day name and hh represent hour and mm represent minute and a represent am/pm
        String time = simpleDateFormat.format(date);
        return time;
    }

    public String Year(long ms) {
        Date date = new Date(ms);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        String year = simpleDateFormat.format(date);
        return year;
    }

    public String month(long ms) {
        Date date = new Date(ms);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        String month = simpleDateFormat.format(date);
        return month;
    }

    public String day(long ms) {
        Date date = new Date(ms);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        String day = simpleDateFormat.format(date);
        return day;
    }
}

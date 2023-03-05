package com.championclub_balirmath.com.ReusableCode;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
    public String Time(long timeStamp) {
        Date date = new Date(timeStamp);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        return simpleDateFormat.format(date);
    }

    public String DayTime(long timeStamp) {
        Date date = new Date(timeStamp);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a");//EEEE represent full day name and hh represent hour and mm represent minute and a represent am/pm
        return simpleDateFormat.format(date);
    }

    public String Date(long milliSecond) {
        Date date = new Date(milliSecond);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");//EEEE represent full day name and hh represent hour and mm represent minute and a represent am/pm
        return simpleDateFormat.format(date);
    }

    public String Year(long ms) {
        Date date = new Date(ms);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(date);
    }

    public String month(long ms) {
        Date date = new Date(ms);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        return simpleDateFormat.format(date);
    }


    public String day(long ms) {
        Date date = new Date(ms);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        return simpleDateFormat.format(date);
    }

    public String customDT(long ms,String format) {
        Date date = new Date(ms);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }
}

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
}

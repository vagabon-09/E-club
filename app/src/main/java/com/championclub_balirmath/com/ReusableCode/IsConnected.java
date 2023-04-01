package com.championclub_balirmath.com.ReusableCode;

import android.content.Context;
import android.net.ConnectivityManager;

public class IsConnected {
    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}

package com.championclub_balirmath.com.ReusableCode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class IsDarkMode {

    /*
     * Here we created a mode sharedPreferences with name mode and it is in private mode
     * it is taking value a boolean and a string and a context
     * here boolean is for putting boolean value
     * here string is the key name to access in future we need this key value
     * and context is needed to access getSharedPreferences
     */
    public void darkMode(Boolean value, String key, Context context) {
        SharedPreferences mode = context.getSharedPreferences("mode", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = mode.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}

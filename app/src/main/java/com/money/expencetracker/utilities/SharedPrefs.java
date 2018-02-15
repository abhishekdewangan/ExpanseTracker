package com.money.expencetracker.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by abhishek on 06/12/17.
 */

public class SharedPrefs {
    public static String SHARED_PREFERENCE_NAME = "EXPENSE_PREF";
    public static final String LAST_SMS_ID = "LAST_SMS_ID";
    public static final String SMS_READ_COUNT = "SMS_READ_COUNT";
    public static final String TOTAL_SMS_COUNT = "TOTAL_SMS_COUNT";
    private SharedPreferences sharedPref;

    public SharedPrefs(Context context) {
       sharedPref = context.getSharedPreferences(SharedPrefs.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPref() {
        return sharedPref;
    }

    public void putString(String key, String value) {
        sharedPref.edit().putString(key, value).apply();
    }

    public String getString(String key) {
       return sharedPref.getString(key, "");
    }

    public void putInt(String key, int value) {
        sharedPref.edit().putInt(key, value).apply();
    }

    public int getInt(String key) {
        return sharedPref.getInt(key, 0);
    }
}

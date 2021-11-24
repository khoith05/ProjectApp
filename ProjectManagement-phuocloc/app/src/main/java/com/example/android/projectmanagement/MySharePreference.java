package com.example.android.projectmanagement;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharePreference {
    private static final String MY_SHARE_PREFERENCE = "MY_SHARE_PREFERENCE";
    private Context mContext;

    public MySharePreference(Context mContext) {
        this.mContext = mContext;
    }

    public void putBooleanValue(String key, boolean value) {
        SharedPreferences sharedPreference = mContext.getSharedPreferences(MY_SHARE_PREFERENCE, 0);
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBooleanValue(String key){
        SharedPreferences sharedPreference = mContext.getSharedPreferences(MY_SHARE_PREFERENCE, 0);
        return sharedPreference.getBoolean(key,false);
    }
}

package com.dhopa.dhopaservice.SharePrefrence;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class AppPreferences {

    private SharedPreferences.Editor prefsEditor;

    private static AppPreferences appPreference;
    private SharedPreferences sharedPreferences;

    private AppPreferences(Context context, String DATABASE_NAME) {
        sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
    }

    public static AppPreferences init(Context context, String DATABASE_NAME) {
        if (appPreference == null) {
            appPreference = new AppPreferences(context, DATABASE_NAME);
        }
        return appPreference;
    }


    public void SaveString(String key, String value) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String GetString(String key) {

        return sharedPreferences.getString(key, "");
    }

    public void Logout() {
        sharedPreferences.edit().clear().apply();
    }

    public void saveUserDetails(String key, Object registerModelClass) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, gson.toJson(registerModelClass));
        editor.apply();
    }

    public <T> T getUserDetails(String key, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(sharedPreferences.getString(key, ""), type);
    }

    public void setSettings(String key, boolean value ){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public boolean getSettings(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public boolean isUserFirstTime(){
        return sharedPreferences.getBoolean("isUserFirstTime",true);
    }
    public void setUserLoginTime() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isUserFirstTime",false);
        editor.apply();
    }


    public void login(Context context, boolean connected) {
        sharedPreferences = context.getSharedPreferences("DhopaUser", 0);
        prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean("connected", connected);
        prefsEditor.commit();
    }

    public boolean isLogin(Context context) {

        return sharedPreferences.getBoolean("connected", false);
    }
}
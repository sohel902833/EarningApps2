package com.tanvir.itask24.LocalDatabase;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class CaptchaDb {

    private Activity activity;
    public CaptchaDb(Activity activity) {
        this.activity = activity;
    }



    public void  setCaptcha(int count,String dbName){
        SharedPreferences sharedPreferences=activity.getSharedPreferences(dbName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("count",count);
        editor.commit();
    }

    public int getCount(String dbName){
        SharedPreferences sharedPreferences=activity.getSharedPreferences(dbName, Context.MODE_PRIVATE);
        int count=sharedPreferences.getInt("count",1);
        return  count;
    }
}

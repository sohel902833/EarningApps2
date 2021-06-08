package com.tanvir.itask24.LocalDatabase;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class InstanceCaptcha {

    private Activity activity;
    public InstanceCaptcha(Activity activity) {
        this.activity = activity;
    }
    public void  setJobOneStatus(String dbName,boolean data){
        SharedPreferences sharedPreferences=activity.getSharedPreferences(dbName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("jobOneStatus",data);
        editor.commit();
    }
    public void  setJobTwoStatus(String dbName,boolean data){
        SharedPreferences sharedPreferences=activity.getSharedPreferences(dbName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("jobTwoStatus",data);
        editor.commit();
    }

    public boolean getJobOneStatus(String dbName){
        SharedPreferences sharedPreferences=activity.getSharedPreferences(dbName, Context.MODE_PRIVATE);
        boolean jobOneStatus=sharedPreferences.getBoolean("jobOneStatus",false);
        return  jobOneStatus;
    }
    public boolean getJobTwoStatus(String dbName){
        SharedPreferences sharedPreferences=activity.getSharedPreferences(dbName, Context.MODE_PRIVATE);
        boolean jobTwoStatus=sharedPreferences.getBoolean("jobTwoStatus",false);
        return  jobTwoStatus;
    }
}

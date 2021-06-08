package com.tanvir.itask24.LocalDatabase;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.tanvir.itask24.Model.UserModel;

public class LocalDb {

    private Activity activity;
    public LocalDb(Activity activity) {
        this.activity = activity;
    }

    public void  setUser(String token){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("token",token);
        editor.commit();
    }
    public void  setUser(UserModel user){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("users", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("name",user.getName());
        editor.putString("phone",user.getPhone());
        editor.putString("email",user.getEmail());
        editor.putLong("cap1LastPlay",user.getCap1LastPlay());
        editor.putLong("cap2LastPlay",user.getCap2LastPlay());
        editor.putLong("cap3LastPlay",user.getCap3LastPlay());
        editor.putLong("cap4LastPlay",user.getCap4LastPlay());
        editor.putLong("watchLastPlay",user.getWatchLastPlay());
        editor.putString("videoState",user.getVideoState());
        editor.putString("profileImage",user.getProfileImage());
        editor.putInt("coins",user.getCoins());
        editor.putString("myRefer",user.getMyReferCode());
        editor.commit();
    }
    public String getToken(){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        String token=sharedPreferences.getString("token","");
        return  token;
    }
    public UserModel getUser(){
        UserModel userModel=null;
        SharedPreferences sharedPreferences=activity.getSharedPreferences("user", Context.MODE_PRIVATE);
        userModel=new UserModel(
                sharedPreferences.getString("name",""),
                sharedPreferences.getString("phone",""),
                sharedPreferences.getString("email",""),
                sharedPreferences.getString("profileImage",""),
                sharedPreferences.getString("videoState",""),
                sharedPreferences.getLong("cap1LastPlay",0),
                sharedPreferences.getLong("cap2LastPlay",0),
                sharedPreferences.getLong("cap3LastPlay",0),
                sharedPreferences.getLong("cap4LastPlay",0),
                sharedPreferences.getLong("watchLastPlay",0),
                sharedPreferences.getInt("coins",0),
                sharedPreferences.getString("myRefer","")
        );


        return userModel;
    }
}

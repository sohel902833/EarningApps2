package com.tanvir.itask24.LocalDatabase;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.tanvir.itask24.Model.Setting;

public class SettingDb {

    private Activity activity;
    public SettingDb(Activity activity) {
        this.activity = activity;
    }
    public void  setSetting(Setting setting){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("coinValue",setting.getCoinValue());
        editor.putLong("firstCaptchaDelayTime",setting.getFirstCaptchaDelayTime());
        editor.putLong("secondCaptchaDelayTime",setting.getSecondCaptchaDelayTime());
        editor.putLong("thirdCaptchaDelayTime",setting.getThirdCaptchaDelayTime());
        editor.putLong("fourthCaptchaDelayTime",setting.getThirdCaptchaDelayTime());
        editor.putLong("thirdCaptchaDelayTime",setting.getFourthCaptchaDelayTime());
        editor.putLong("watchDelayTime",setting.getWatchDelayTime());
        editor.putInt("cap1",setting.getCap1());
        editor.putInt("cap2",setting.getCap2());
        editor.putInt("cap3",setting.getCap3());
        editor.putInt("cap4",setting.getCap4());
        editor.putInt("offer",setting.getOffer());
        editor.putInt("minW",setting.getMinW());
        editor.putInt("bkash",setting.getBkash());
        editor.putInt("paytm",setting.getPaytm());
        editor.putString("showOffer",setting.getShowOffer());
        editor.commit();
    }

    public  Setting getSetting(){
        Setting setting=null;
        SharedPreferences sharedPreferences=activity.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        setting=new Setting(
                sharedPreferences.getInt("bkash",0),
                sharedPreferences.getInt("paytm",0),
                sharedPreferences.getInt("coinValue",0),
                sharedPreferences.getLong("firstCaptchaDelayTime",0),
                sharedPreferences.getLong("secondCaptchaDelayTime",0),
                sharedPreferences.getLong("thirdCaptchaDelayTime",0),
                sharedPreferences.getLong("fourthCaptchaDelayTime",0),
                sharedPreferences.getLong("watchDelayTime",0),
                sharedPreferences.getInt("cap1",0),
                sharedPreferences.getInt("cap2",0),
                sharedPreferences.getInt("cap3",0),
                sharedPreferences.getInt("cap4",0),
                sharedPreferences.getInt("offer",0),
                sharedPreferences.getInt("minW",0),
                sharedPreferences.getString("showOffer","false")
        );
        return  setting;
    }

}

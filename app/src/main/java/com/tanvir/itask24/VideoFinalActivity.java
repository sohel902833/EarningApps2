package com.tanvir.itask24;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.RewardData;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.tanvir.itask24.LocalDatabase.SettingDb;
import com.tanvir.itask24.Model.Setting;
import com.tanvir.itask24.Model.UserModel;
import com.tanvir.itask24.Services.UserServices;

import java.util.Locale;

public class VideoFinalActivity extends AppCompatActivity {

    private Button collectButton;
    private int cardNo=0;

    private CountDownTimer offerCountDownTimer;
    private  long offerCountDownIInMillis=15000;

    private final String TAG = VideoFinalActivity.class.getSimpleName();
    private RewardedVideoAd rewardedVideoAd;
    private Setting setting;
    private UserModel userModel;
    private TextView offerCountDownTv;
    private SettingDb settingDb;
    LinearLayout linearLayout;
    Button offerClickButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_final);
        cardNo=getIntent().getIntExtra("cardNo",0);

        AudienceNetworkAds.initialize(this);
        rewardedVideoAd = new RewardedVideoAd(this, "VID_HD_9_16_39S_APP_INSTALL#549167759165615_549192132496511");



        settingDb=new SettingDb(this);
        setting=settingDb.getSetting();


        init();

        collectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserServices userServices=new UserServices(VideoFinalActivity.this);
                if(cardNo==8) {
                    userServices.updateUserState("watchLastPlay");
                    userServices.updateVideoServices(String.valueOf(1));
                    linearLayout.setVisibility(View.VISIBLE);
                    startOfferCountDown();
                }else{
                    userServices.updateVideoServices(String.valueOf(cardNo+1));
                }

               showInterstitialAdd();
            }
        });
        offerClickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOfferAdd();
            }
        });
    }
    @Override
    protected void onDestroy() {
        if (rewardedVideoAd != null) {
            rewardedVideoAd.destroy();
            rewardedVideoAd = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(rewardedVideoAd.isAdLoaded()){
            Toast.makeText(this, "Add is showing", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        collectButton=findViewById(R.id.collectButton);
        linearLayout=findViewById(R.id.below_line1);
        offerClickButton=findViewById(R.id.offerInstallButtonId);
        offerCountDownTv=findViewById(R.id.offerCountDown);
    }
    private  void startOfferCountDown(){
        offerCountDownTimer=new CountDownTimer(offerCountDownIInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                offerCountDownIInMillis=millisUntilFinished;
                updateOfferCountDownText();
            }

            @Override
            public void onFinish() {
                offerCountDownIInMillis=0;
                linearLayout.setVisibility(View.GONE);
                updateOfferCountDownText();
            }
        }.start();
    }
    private  void updateOfferCountDownText(){
        int seconds=(int)(offerCountDownIInMillis/1000)%60;
        String timeFormatted=String.format(Locale.getDefault(),"%01d",seconds);
        offerCountDownTv.setText("Bonous Expire In "+timeFormatted+"s");
    }
    private void showInterstitialAdd(){
        rewardedVideoAd = new RewardedVideoAd(this, "VID_HD_16_9_46S_APP_INSTALL");

        RewardedVideoAdListener rewardedVideoAdListener=new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoCompleted() {

            }

            @Override
            public void onRewardedVideoClosed() {
                finish();
                UserServices userServices=new UserServices(VideoFinalActivity.this);
                if(cardNo==8) {
                    userServices.updateUserState("watchLastPlay");
                    userServices.updateVideoServices(String.valueOf(1));
                    linearLayout.setVisibility(View.VISIBLE);
                    startOfferCountDown();
                }else{
                    userServices.updateVideoServices(String.valueOf(cardNo+1));
                }
                if(setting!=null){
                    userServices.setCoinIntoDb(String.valueOf(setting.getCap3()),"From Video");
                }
            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                rewardedVideoAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };



        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());

    }
    private void showOfferAdd(){
        RewardedVideoAdListener rewardedVideoAdListener=new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoCompleted() {

            }

            @Override
            public void onRewardedVideoClosed() {
                finish();
                UserServices userServices=new UserServices(VideoFinalActivity.this);
                userServices.updateUserState("watchLastPlay");

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                finish();
                UserServices userServices=new UserServices(VideoFinalActivity.this);
                userServices.updateUserState("watchLastPlay");

            }

            @Override
            public void onAdLoaded(Ad ad) {
                Toast.makeText(VideoFinalActivity.this, " Click Add For Get Offer Coin", Toast.LENGTH_LONG).show();
                rewardedVideoAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                UserServices userServices=new UserServices(VideoFinalActivity.this);
                if(setting!=null){
                    userServices.setCoinIntoDb(String.valueOf(setting.getOffer()),"From Offer");
                }
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
    }
}
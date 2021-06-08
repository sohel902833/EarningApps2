package com.tanvir.itask24;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.tanvir.itask24.LocalDatabase.CaptchaDb;
import com.tanvir.itask24.LocalDatabase.Common;
import com.tanvir.itask24.LocalDatabase.LocalDb;
import com.tanvir.itask24.LocalDatabase.SettingDb;
import com.tanvir.itask24.Model.Setting;
import com.tanvir.itask24.Model.UserModel;
import com.tanvir.itask24.Services.UserServices;

import java.util.Locale;
import java.util.Random;

public class ProCaptchaActivity extends AppCompatActivity {

    //<-----------------Count Down------------------>

    private CountDownTimer countDownTimer;
    private CountDownTimer offerCountDownTimer;
    private  long timeLeftInMIllis=5000;
    private  long offerCountDownIInMillis=15000;

    //<------------------Layout----------------->
    private TextView captchaTv;
    private EditText captchaEt;
    private Button captchaSubmitButton,offerInstallButton;
    private ImageButton backButton;
    private TextView counterTv;
    private TextView countDownTv,offerCountDown;
    private LinearLayout belowLinearLayout;

    //<------------------Variable-------------->
    private ProgressDialog progressDialog;
    private Setting setting;
    private UserModel userModel;
    private SettingDb settingDb;
    int count=0;
    LocalDb localDb;
    CaptchaDb captchaDb;


    private InterstitialAd interstitialAd;
    private final String TAG = ProCaptchaActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_captcha);

        AudienceNetworkAds.initialize(this);
        interstitialAd = new InterstitialAd(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
        settingDb=new SettingDb(this);
        setting=settingDb.getSetting();

        init();
        generateCaptcha();

        captchaSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt=captchaEt.getText().toString();
                if(txt.isEmpty()){
                    Toast.makeText(ProCaptchaActivity.this, "Please Write Your Captcha", Toast.LENGTH_SHORT).show();
                }else{
                    String captcha=captchaTv.getText().toString();
                    if(captcha.equals(txt)){
                        captchaTv.setText("");
                        captchaEt.setText("");
                        showInterstitialAdd();
                    }else{
                        Toast.makeText(ProCaptchaActivity.this, "Wrong Captcha", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        offerInstallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOfferAdd();
            }
        });

    }

    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
    private void init() {
        captchaDb=new CaptchaDb(this);
        progressDialog=new ProgressDialog(this);
        localDb=new LocalDb(ProCaptchaActivity.this);
        captchaTv=findViewById(R.id.captchaTv);
        captchaEt=findViewById(R.id.captcaEt);
        counterTv=findViewById(R.id.counterTv);
        countDownTv=findViewById(R.id.countDownTv);
        offerCountDown=findViewById(R.id.offerCountDown);
        captchaSubmitButton=findViewById(R.id.captchaSubmitButton);
        belowLinearLayout=findViewById(R.id.below_line1);
        backButton=findViewById(R.id.backButton);
        offerInstallButton=findViewById(R.id.offerInstallButtonId);
    }
    @Override
    protected void onStart() {
        super.onStart();
        setCountText();
    }
    public void  setCountText(){
        count=captchaDb.getCount(Common.cap2Db);
        if(count>10){
            captchaDb.setCaptcha(1,Common.cap2Db);
            captchaTv.setText("");
        }else{
            counterTv.setText(""+count);
        }
    }
    public void updateCount(){
        count++;
        captchaDb.setCaptcha(count,Common.cap2Db);
        setCountText();
    }
    private  void startCountDown(){
        countDownTimer=new CountDownTimer(timeLeftInMIllis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMIllis=millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMIllis=0;
                updateCountDownText();
                if(count>10){
                    belowLinearLayout.setVisibility(View.VISIBLE);
                    startOfferCountDown();
                }else{
                    generateCaptcha();
                }

            }
        }.start();
    }
    private  void startOfferCountDown(){
        countDownTimer=new CountDownTimer(offerCountDownIInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                offerCountDownIInMillis=millisUntilFinished;
                updateOfferCountDownText();
            }

            @Override
            public void onFinish() {
                offerCountDownIInMillis=0;
                belowLinearLayout.setVisibility(View.GONE);
                updateOfferCountDownText();
            }
        }.start();
    }
    private  void updateCountDownText(){
        int minutes=(int)(timeLeftInMIllis/1000/60);
        int seconds=(int)(timeLeftInMIllis/1000)%60;

        String timeFormatted=String.format(Locale.getDefault(),"%01d",seconds);
        countDownTv.setText(""+timeFormatted+"s");
    }
    private  void updateOfferCountDownText(){
        int seconds=(int)(offerCountDownIInMillis/1000)%60;
        String timeFormatted=String.format(Locale.getDefault(),"%01d",seconds);
        offerCountDown.setText("Bonous Expire In "+timeFormatted+"s");
    }
    private void sendUserToCaptchaActivity() {
        startActivity(new Intent(ProCaptchaActivity.this,MainActivity.class));
        finish();
    }
    private void generateCaptcha() {
        String txt="";
        int[] nmbr={1,2,3,4,5,6,7,8,9,0};
        for(int i=0; i<8; i++){
            int randomNumber=new Random().nextInt(9);
            txt=txt+nmbr[randomNumber];
        }
        captchaTv.setText(txt);
    }
    private void showInterstitialAdd(){
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

                updateCount();
                timeLeftInMIllis=10000;
                startCountDown();

                UserServices userServices=new UserServices(ProCaptchaActivity.this);
                if(count>10) {
                    userServices.updateUserState("cap3LastPlay");
                    belowLinearLayout.setVisibility(View.VISIBLE);
                    startOfferCountDown();
                }
                if(setting!=null){
                    userServices.setCoinIntoDb(String.valueOf(setting.getCap3()),"From Pro Captcha");
                }
            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                finish();
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }
    private void showOfferAdd(){
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                UserServices userServices=new UserServices(ProCaptchaActivity.this);
                userServices.updateUserState("cap3LastPlay");
                Toast.makeText(ProCaptchaActivity.this, " Click Add For Get Offer Coin", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                finish();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                UserServices userServices=new UserServices(ProCaptchaActivity.this);
                userServices.updateUserState("cap3LastPlay");
            }

            @Override
            public void onAdLoaded(Ad ad) {
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                UserServices userServices=new UserServices(ProCaptchaActivity.this);
                if(setting!=null){
                    userServices.setCoinIntoDb(String.valueOf(setting.getOffer()),"From Offer");
                }
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

}
package com.tanvir.itask24;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.tanvir.itask24.LocalDatabase.InstanceCaptcha;
import com.tanvir.itask24.LocalDatabase.LocalDb;
import com.tanvir.itask24.Model.Setting;
import com.tanvir.itask24.Model.UserModel;
import com.tanvir.itask24.Services.UserServices;

import java.util.Locale;
import java.util.Random;

public class InstanceCaptchaActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private CountDownTimer offerCountDownTimer;
    private  long timeLeftInMIllis=5000;
    private  long offerCountDownIInMillis=15000;




    //<------------------Layout----------------->
    private TextView captchaTv;
    private EditText captchaEt;
    private Button captchaSubmitButton;
    private ImageButton backButton;
    private TextView counterTv;
    private TextView countDownTv,offerCountDown;
    private LinearLayout belowLinearLayout;

    //<------------------Variable-------------->
    private ProgressDialog progressDialog;
    private Setting setting;
    private UserModel userModel;
    int count=0;
    LocalDb localDb;
    CaptchaDb captchaDb;


    String dbName="";
    int taskNo=0;


    private InterstitialAd interstitialAd;
    private final String TAG = ProCaptchaActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instance_captcha);
        dbName=getIntent().getStringExtra("dbName");
        taskNo=getIntent().getIntExtra("workNo",1);
        AudienceNetworkAds.initialize(this);
        interstitialAd = new InterstitialAd(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");

        init();
        generateCaptcha();


        captchaSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt=captchaEt.getText().toString();
                if(txt.isEmpty()){
                    Toast.makeText(InstanceCaptchaActivity.this, "Please Write Your Captcha", Toast.LENGTH_SHORT).show();
                }else{
                    String captcha=captchaTv.getText().toString();
                    if(captcha.equals(txt)){
                        captchaTv.setText("");
                        captchaEt.setText("");
                        showInterstitialAdd();

                    }else{
                        Toast.makeText(InstanceCaptchaActivity.this, "Wrong Captcha", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        setCountText();
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
        localDb=new LocalDb(InstanceCaptchaActivity.this);
        captchaTv=findViewById(R.id.captchaTv);
        captchaEt=findViewById(R.id.captcaEt);
        counterTv=findViewById(R.id.counterTv);
        countDownTv=findViewById(R.id.countDownTv);
        offerCountDown=findViewById(R.id.offerCountDown);
        captchaSubmitButton=findViewById(R.id.captchaSubmitButton);
        belowLinearLayout=findViewById(R.id.below_line1);
        backButton=findViewById(R.id.backButton);
    }
    public void  setCountText(){
        count=count+1;
        if(count>5){
            captchaTv.setText("");
            finalSave();
        }else{
            counterTv.setText(""+count);
        }
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
                if(count>5){
                   finalSave();
                }else{
                    generateCaptcha();
                }

            }
        }.start();
    }

    private void finalSave() {
        if(taskNo==1){
            InstanceCaptcha instanceCaptcha=new InstanceCaptcha(InstanceCaptchaActivity.this);
            instanceCaptcha.setJobOneStatus(dbName,true);
        }else if(taskNo == 2) {
            InstanceCaptcha instanceCaptcha=new InstanceCaptcha(InstanceCaptchaActivity.this);
            instanceCaptcha.setJobTwoStatus(dbName,true);
        }
       finish();
    }

    private  void updateCountDownText(){
        int minutes=(int)(timeLeftInMIllis/1000/60);
        int seconds=(int)(timeLeftInMIllis/1000)%60;

        String timeFormatted=String.format(Locale.getDefault(),"%01d",seconds);
        countDownTv.setText(""+timeFormatted+"s");
    }
    private void generateCaptcha() {
        String txt="";
        int[] nmbr={1,2,3,4,5,6,7,8,9,0};
        for(int i=0; i<3; i++){
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
                timeLeftInMIllis=5000;
                setCountText();
                startCountDown();
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
}
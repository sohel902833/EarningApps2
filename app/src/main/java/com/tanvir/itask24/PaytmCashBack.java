package com.tanvir.itask24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tanvir.itask24.LocalDatabase.Common;
import com.tanvir.itask24.LocalDatabase.InstanceCaptcha;
import com.tanvir.itask24.LocalDatabase.SettingDb;
import com.tanvir.itask24.Model.Setting;
import com.tanvir.itask24.Services.UserServices;

import org.w3c.dom.Text;

public class PaytmCashBack extends AppCompatActivity {

    private TextView userNameTv;
    private TextView jobOneStatusTv,jobTwoStatusTv;
    private Button openButton1,openButton2,balanceButton,withdrawButton;
    private EditText accountNoEditText;
    String userName="Md Sohrab";

    InstanceCaptcha instanceCaptcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm_cash_back);


        userName=getIntent().getStringExtra("name");
        init();
        userNameTv.setText(userName);

        openButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean jobOneStatus=instanceCaptcha.getJobOneStatus(Common.paytmDb);
                if(jobOneStatus){
                    Toast.makeText(PaytmCashBack.this, "Already Completed", Toast.LENGTH_SHORT).show();
                }else{
                    sendUserToInstanceCaptchaActivity(Common.paytmDb,1);
                }
            }
        });
        openButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean jobTwoStatus=instanceCaptcha.getJobTwoStatus(Common.paytmDb);
                if(jobTwoStatus){
                    Toast.makeText(PaytmCashBack.this, "Already Completed", Toast.LENGTH_SHORT).show();
                }else{
                    sendUserToInstanceCaptchaActivity(Common.paytmDb,2);
                }
            }
        });
        withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingDb settingDb=new SettingDb(PaytmCashBack.this);
                Setting setting=settingDb.getSetting();
                String accountNo=accountNoEditText.getText().toString().trim();
                if(accountNo.isEmpty()){
                    accountNoEditText.setError("Please Enter Your Account No.");
                    accountNoEditText.requestFocus();
                }else{
                    if(instanceCaptcha.getJobOneStatus(Common.paytmDb) && instanceCaptcha.getJobTwoStatus(Common.paytmDb)){
                        requestWithdraw();
                    }else{
                        Toast.makeText(PaytmCashBack.this, "Complete Both Work", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        setBalanceTxt();
    }

    private void setBalanceTxt() {
        int balance=0;
        if(instanceCaptcha.getJobOneStatus(Common.paytmDb)){
            jobOneStatusTv.setText("Completed");
            balance=balance+2;
        }
        if(instanceCaptcha.getJobTwoStatus(Common.paytmDb)){
            jobTwoStatusTv.setText("Completed");
            balance=balance+2;
        }

        balanceButton.setText(balance+"Tk");
    }

    private void sendUserToInstanceCaptchaActivity(String dbName, int workNo) {
        Intent intent=new Intent(PaytmCashBack.this,InstanceCaptchaActivity.class);
        intent.putExtra("dbName",dbName);
        intent.putExtra("workNo",workNo);
        startActivity(intent);
    }
    private void requestWithdraw() {

        instanceCaptcha.setJobTwoStatus(Common.paytmDb,false);
        instanceCaptcha.setJobOneStatus(Common.paytmDb,false);

        SettingDb settingDb=new SettingDb(PaytmCashBack.this);
        Setting setting=settingDb.getSetting();
        UserServices userServices=new UserServices(PaytmCashBack.this);
        userServices.sendInstantWithdrawRequest("Bkash",accountNoEditText.getText().toString(), String.valueOf(setting.getPaytm()));




    }
    private void init() {
        instanceCaptcha=new InstanceCaptcha(this);
        userNameTv=findViewById(R.id.userNameTv);
        jobOneStatusTv=findViewById(R.id.txt1);
        jobTwoStatusTv=findViewById(R.id.txt2);
        openButton1=findViewById(R.id.openButton1);
        openButton2=findViewById(R.id.openButton2);
        balanceButton=findViewById(R.id.balanceButton);
        accountNoEditText=findViewById(R.id.bkashNumberEt);
        withdrawButton=findViewById(R.id.withDrawButton);
    }
}
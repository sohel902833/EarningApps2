package com.tanvir.itask24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tanvir.itask24.LocalDatabase.InstanceCaptcha;

public class CashBackActivity extends AppCompatActivity {
    private TextView userNameTv;
    private CardView bkashCashback,paytmCashback;
    private String userName="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_back);

        userName=getIntent().getStringExtra("name");
        bkashCashback=findViewById(R.id.bkashCashbackCard);
        paytmCashback=findViewById(R.id.paytmCashBackCard);

        userNameTv=findViewById(R.id.userNameTv);


        bkashCashback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(CashBackActivity.this,BkashCashBackActivity.class);
               intent.putExtra("name",userName);
               startActivity(intent);
            }
        });
        paytmCashback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(CashBackActivity.this,PaytmCashBack.class);
               intent.putExtra("name",userName);
               startActivity(intent);
            }
        });



    }
}
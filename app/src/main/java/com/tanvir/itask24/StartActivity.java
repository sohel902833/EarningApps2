package com.tanvir.itask24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tanvir.itask24.LocalDatabase.LocalDb;

public class StartActivity extends AppCompatActivity {

    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startButton=findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,LoginActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalDb localDb=new LocalDb(this);
        if(!localDb.getToken().equals("")){
            sendUserToMainActivity();
        }
    }

    public void sendUserToMainActivity(){
        startActivity(new Intent(StartActivity.this,MainActivity.class));
        finish();
    }


}
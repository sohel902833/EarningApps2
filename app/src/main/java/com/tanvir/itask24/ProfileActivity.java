package com.tanvir.itask24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tanvir.itask24.LocalDatabase.Common;
import com.tanvir.itask24.LocalDatabase.InstanceCaptcha;
import com.tanvir.itask24.LocalDatabase.LocalDb;
import com.tanvir.itask24.LocalDatabase.SettingDb;
import com.tanvir.itask24.Model.Setting;
import com.tanvir.itask24.Model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

        private TextView nameTv;
        private CircleImageView profileImage;
        private ProgressDialog progressDialog;
        private UserModel userModel;
        private SettingDb settingDb;
        private Setting setting;
        private LocalDb localDb;
    InstanceCaptcha instanceCaptcha;
        //<---------------Card View---------------->
    private CardView  paymentCard,transectionCard,inviteCard1,inviteCard2,instanceCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        localDb=new LocalDb(ProfileActivity.this);
        settingDb=new SettingDb(ProfileActivity.this);
        setting=settingDb.getSetting();
        instanceCaptcha =new InstanceCaptcha(this);

        init();
        paymentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userModel!=null){
                    Intent intent=new Intent(ProfileActivity.this,WithDrawActivity.class);
                    intent.putExtra("name",userModel.getName());
                    intent.putExtra("coin",userModel.getCoins());
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(ProfileActivity.this,WithDrawActivity.class);
                    intent.putExtra("name",localDb.getUser().getName());
                    intent.putExtra("coin",localDb.getUser().getCoins());
                    startActivity(intent);
                }

            }
        });
    transectionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userModel!=null) {
                    Intent intent = new Intent(ProfileActivity.this, TransectionActivity.class);
                    intent.putExtra("name", userModel.getName());
                    intent.putExtra("coin", userModel.getCoins());
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(ProfileActivity.this, TransectionActivity.class);
                    intent.putExtra("name", localDb.getUser().getName());
                    intent.putExtra("coin", localDb.getUser().getCoins());
                    startActivity(intent);
                }
            }
        });

        inviteCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userModel!=null){
                    invite(userModel.getMyReferCode());
                }else{
                    invite(localDb.getUser().getMyReferCode());
                }
            }
        });
       inviteCard2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(userModel!=null){
                        invite(userModel.getMyReferCode());
                    }else{
                        invite(localDb.getUser().getMyReferCode());
                    }

                }
            });
      instanceCard.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(userModel!=null && setting!=null){
                  long time=setting.getFourthCaptchaDelayTime()+userModel.getCap4LastPlay();
                  if(time>System.currentTimeMillis()){
                      long tTime=time-System.currentTimeMillis();
                      int minutes=(int)(tTime/1000/60);
                      int seconds=(int)(tTime/1000)%60;
                      Toast.makeText(ProfileActivity.this, "You Are Blocked For "+minutes+"Min : "+seconds+"Sec", Toast.LENGTH_SHORT).show();
                  }else{
                      if(userModel!=null){
                          sendUserToCashBackActivity(userModel.getName());
                      }else{
                          sendUserToCashBackActivity(localDb.getUser().getName());
                      }
                  }
              }



          }
      });




    }

    public void sendUserToCashBackActivity(String name){
        Intent intent=new Intent(ProfileActivity.this,CashBackActivity.class);
        intent.putExtra("name",name);
        startActivity(intent);
    }


    private void invite(String myReferCode) {
        String sharedBody = "Hey, i'm Using the best earning app. Join using my invite code to instantly get 100"
                + "coins. My invite code is " + userModel.getMyReferCode() + "\n" +
                "Download from Play Store \n" +
                "https://play.google.com/store/apps/details?id=" + getPackageName();


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, sharedBody);
        startActivity(intent);
    }

    private void init() {
             paymentCard=findViewById(R.id.paymentCard);
             inviteCard1=findViewById(R.id.inviteCard1);
             inviteCard2=findViewById(R.id.inviteCard2);
             instanceCard=findViewById(R.id.instanceCard);
             transectionCard=findViewById(R.id.transectionCard);
             progressDialog=new ProgressDialog(this);
             nameTv=findViewById(R.id.userNameTv);
             profileImage=findViewById(R.id.profileImageView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getUserData();
    }

    public void getUserData(){
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestQueue requestQueue= Volley.newRequestQueue(ProfileActivity.this);
        String url=Api.Url+"user/single";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("users");
                    JSONObject receive=array.getJSONObject(0);

                    userModel=new UserModel(
                            receive.getString("name"),
                            receive.getString("phone"),
                            receive.getString("email"),
                            receive.getString("profileImage"),
                            receive.getString("videoState"),
                            receive.getLong("cap1LastPlay"),
                            receive.getLong("cap2LastPlay"),
                            receive.getLong("cap3LastPlay"),
                            receive.getLong("cap4LastPlay"),
                            receive.getLong("watchLastPlay"),
                            receive.getInt("coins"),
                            receive.getString("myRefer")
                    );
                    LocalDb localDb=new LocalDb(ProfileActivity.this);
                    localDb.setUser(userModel);
                    nameTv.setText(userModel.getName());

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ProfileActivity.this, "Failed"+error    .getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                LocalDb localDb=new LocalDb(ProfileActivity.this);

                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization",localDb.getToken());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
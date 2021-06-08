package com.tanvir.itask24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tanvir.itask24.LocalDatabase.LocalDb;
import com.tanvir.itask24.Model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VideoJobActivity extends AppCompatActivity {

    private CardView card1,card2,card3,card4,card5,card6,card7,card8;
    private ProgressDialog progressDialog;
    private  UserModel userModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_job);

        progressDialog=new ProgressDialog(this);
        

        card1=findViewById(R.id.card1);
        card2=findViewById(R.id.card2);
        card3=findViewById(R.id.card3);
        card4=findViewById(R.id.card4);
        card5=findViewById(R.id.card5);
        card6=findViewById(R.id.card6);
        card7=findViewById(R.id.card7);
        card8=findViewById(R.id.card8);



        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheck(1);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheck(2);
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheck(3);
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheck(4);
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheck(5);
            }
        });
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheck(6);
            }
        });
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheck(7);
            }
        });
        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheck(8);
            }
        });
    }

    public void onCheck(int cardNo){
        if(userModel!=null){
            if(userModel.getVideoState().equals(String.valueOf(cardNo))){
                sendUserToVideoViewActivity(cardNo);
            }else{
                Toast.makeText(this, "This Card Is Blocked.Please Play First "+userModel.getVideoState()+" Card", Toast.LENGTH_SHORT).show();
            }
        }else{
            finish();
        }
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

        RequestQueue requestQueue= Volley.newRequestQueue(VideoJobActivity.this);
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
                    LocalDb localDb=new LocalDb(VideoJobActivity.this);
                    localDb.setUser(userModel);
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(VideoJobActivity.this, "Failed"+error    .getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                LocalDb localDb=new LocalDb(VideoJobActivity.this);

                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization",localDb.getToken());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void sendUserToVideoViewActivity(int cardNo) {
        Intent intent=new Intent(VideoJobActivity.this,VideoFinalActivity.class);
        intent.putExtra("cardNo",cardNo);
        startActivity(intent);
    }
}
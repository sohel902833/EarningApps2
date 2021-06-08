package com.tanvir.itask24.Services;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tanvir.itask24.Api;
import com.tanvir.itask24.LocalDatabase.Common;
import com.tanvir.itask24.LocalDatabase.InstanceCaptcha;
import com.tanvir.itask24.LocalDatabase.LocalDb;
import com.tanvir.itask24.LocalDatabase.SettingDb;
import com.tanvir.itask24.Model.Setting;
import com.tanvir.itask24.WithDrawActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserServices {
    private LocalDb localDb;
    private ProgressDialog progressDialog;

    Activity context;
    public UserServices (Activity context){
        this.context=context;
        localDb=new LocalDb(context);
        progressDialog=new ProgressDialog(context);
    }


    public void setCoinIntoDb(String coin,String text){
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        String url= Api.Url+"user/coin";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Toast.makeText(context, "coin Addede", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               Toast.makeText(context, "Failed"+error    .getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization",localDb.getToken());
                return params;
            }
            @Override
            protected Map<String, String> getParams()  {
                Calendar calForDate= Calendar.getInstance();
                SimpleDateFormat currentDate=new SimpleDateFormat("dd-MMMM-yyy");
                String saveCurrentDate=currentDate.format(calForDate.getTime());

                Calendar callForTime=Calendar.getInstance();
                SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm");
                String saveCurrentTime=currentTime.format(callForTime.getTime());

                String time=saveCurrentDate+" At "+saveCurrentTime;


                Map<String, String>  parms=new HashMap<String, String>();
                parms.put("coin",coin);
                parms.put("text",text);
                parms.put("time",time);
                return  parms;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void updateUserState(String from){
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        String url= Api.Url+"user/";

        StringRequest stringRequest=new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               Toast.makeText(context, "Failed"+error    .getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization",localDb.getToken());
                return params;
            }
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String>  parms=new HashMap<String, String>();
                parms.put(from,String.valueOf(System.currentTimeMillis()));
                return  parms;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void updateVideoServices(String playdNo){
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        String url= Api.Url+"user/";

        StringRequest stringRequest=new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               Toast.makeText(context, "Failed"+error    .getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization",localDb.getToken());
                return params;
            }
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String>  parms=new HashMap<String, String>();
                parms.put("videoState",String.valueOf(playdNo));
                return  parms;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void sendInstantWithdrawRequest(String paymentType, String accountNo,String coin) {
        updateUserState("watchLastPlay");

        progressDialog.setMessage("Sending Withdraw Request..");
        progressDialog.setCancelable(false);
        progressDialog.show();


        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd-MMMM-yyy");
        String saveCurrentDate=currentDate.format(calForDate.getTime());

        Calendar callForTime=Calendar.getInstance();
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm");
        String  saveCurrentTime=currentTime.format(callForTime.getTime());
        final String time=saveCurrentDate+" At "+saveCurrentTime;

        RequestQueue requestQueue= Volley.newRequestQueue(context);
        String url=Api.Url+"instant/";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject=new JSONObject(response);
                    InstanceCaptcha instanceCaptcha=new InstanceCaptcha(context);
                    instanceCaptcha.setJobTwoStatus(Common.bkashDb,false);
                    instanceCaptcha.setJobOneStatus(Common.bkashDb,false);
                    Toast.makeText(context, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, "Failed"+error    .getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                LocalDb localDb=new LocalDb(context);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization",localDb.getToken());
                return params;
            }

            @Override
            protected Map<String, String> getParams()  {


                Map<String, String>  parms=new HashMap<String, String>();
                parms.put("coin",coin);
                parms.put("payment",paymentType);
                parms.put("time",time);
                parms.put("accountNo",accountNo);
                parms.put("state","pending");
                return  parms;
            }
        };
        requestQueue.add(stringRequest);
    }



}

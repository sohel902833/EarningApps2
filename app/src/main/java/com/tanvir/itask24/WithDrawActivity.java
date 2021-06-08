package com.tanvir.itask24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tanvir.itask24.LocalDatabase.LocalDb;
import com.tanvir.itask24.LocalDatabase.SettingDb;
import com.tanvir.itask24.Model.Setting;
import com.tanvir.itask24.Model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WithDrawActivity extends AppCompatActivity  implements View.OnClickListener {

    //<=======Layout==========>
    private TextView nameTv;
    private TextView bkashTv,paytmTv,amazonTv,paypalTv,gcashTv,payonerTv,jazzTv,playstoreTv;

    //<============Variable=================>
    private String name="";
    private int coin=0;
    private ProgressDialog progressDialog;

    int bkash=0,paytm=0,amazon=0,paypal=0,gcash=0,payoner=0,jazz=0,playstore=0;
    private SettingDb settingDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw);

        name=getIntent().getStringExtra("name");
        coin=getIntent().getIntExtra("coin",0);
        init();

        nameTv.setText(name);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getPaymentData();
    }

    private void init() {
        nameTv=findViewById(R.id.nameTv);
        progressDialog=new ProgressDialog(this);

        settingDb=new SettingDb(this);
        bkashTv=findViewById(R.id.bkashTv);
        amazonTv=findViewById(R.id.amazonTv);
        paytmTv=findViewById(R.id.paytmTv);
        jazzTv=findViewById(R.id.jazzTv);
        paypalTv=findViewById(R.id.paypalTv);
        payonerTv=findViewById(R.id.payoneerTv);
        gcashTv=findViewById(R.id.gcashTv);
        playstoreTv=findViewById(R.id.playStoreTv);


        bkashTv.setOnClickListener(this);
        amazonTv.setOnClickListener(this);
        paytmTv.setOnClickListener(this);
        jazzTv.setOnClickListener(this);
        paypalTv.setOnClickListener(this);
        payonerTv.setOnClickListener(this);
        gcashTv.setOnClickListener(this);
        playstoreTv.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Setting setting=settingDb.getSetting();
        if(setting!=null){
            if(v.getId()==R.id.bkashTv){
                    bkashCheck(setting);
            }if(v.getId()==R.id.amazonTv){
                amazonCheck(setting);
            }if(v.getId()==R.id.paytmTv){
                paytmCheck(setting);
            }if(v.getId()==R.id.jazzTv){
                jazzcheck(setting);
            }if(v.getId()==R.id.paypalTv){
                paypalCheck(setting);
            }if(v.getId()==R.id.payoneerTv){
                payonerCheck(setting);
            }if(v.getId()==R.id.gcashTv){
                gcashCheck(setting);
            }if(v.getId()==R.id.playStoreTv){
                plastoreCheck(setting);
            }
        }

    }

    public void bkashCheck(Setting setting){
        if(setting.getMinW()>coin){
            Toast.makeText(this, "You don not have Enough Coin", Toast.LENGTH_SHORT).show();
        }else{
            int coinValue=setting.getCoinValue();
            double doller=(double)coin/(double)coinValue;
            if(doller<bkash){
                Toast.makeText(this, "You don't Have Enought Coin", Toast.LENGTH_SHORT).show();
            }else{
                showcustomdioloage("Bkash",bkash);
            }
        }
    }
    public void amazonCheck(Setting setting){
        if(setting.getMinW()>coin){
            Toast.makeText(this, "You don not have Enough Coin", Toast.LENGTH_SHORT).show();
        }else{
            int coinValue=setting.getCoinValue();
            double doller=(double)coin/(double)coinValue;
            if(doller<amazon){
                Toast.makeText(this, "You don't Have Enought Coin", Toast.LENGTH_SHORT).show();
            }else{
                showcustomdioloage("Amazon",amazon);
            }
        }
    }
    public void paytmCheck(Setting setting){
        if(setting.getMinW()>coin){
            Toast.makeText(this, "You don not have Enough Coin", Toast.LENGTH_SHORT).show();
        }else{
            int coinValue=setting.getCoinValue();
            double doller=(double)coin/(double)coinValue;
            if(doller<paytm){
                Toast.makeText(this, "You don't Have Enought Coin", Toast.LENGTH_SHORT).show();
            }else{
                showcustomdioloage("Paytm",paytm);
            }
        }
    }
    public void jazzcheck(Setting setting){
        if(setting.getMinW()>coin){
            Toast.makeText(this, "You don not have Enough Coin", Toast.LENGTH_SHORT).show();
        }else{
            int coinValue=setting.getCoinValue();
            double doller=(double)coin/(double)coinValue;
            if(doller<jazz){
                Toast.makeText(this, "You don't Have Enought Coin", Toast.LENGTH_SHORT).show();
            }else{
                showcustomdioloage("Jazz",jazz);
            }
        }
    }
    public void paypalCheck(Setting setting){
        if(setting.getMinW()>coin){
            Toast.makeText(this, "You don not have Enough Coin", Toast.LENGTH_SHORT).show();
        }else{
            int coinValue=setting.getCoinValue();
            double doller=(double)coin/(double)coinValue;
            if(doller<paypal){
                Toast.makeText(this, "You don't Have Enought Coin", Toast.LENGTH_SHORT).show();
            }else{
                showcustomdioloage("Paypal",paypal);
            }
        }
    }
   public void payonerCheck(Setting setting){
        if(setting.getMinW()>coin){
            Toast.makeText(this, "You don not have Enough Coin", Toast.LENGTH_SHORT).show();
        }else{
            int coinValue=setting.getCoinValue();
            double doller=(double)coin/(double)coinValue;
            if(doller<payoner){
                Toast.makeText(this, "You don't Have Enought Coin", Toast.LENGTH_SHORT).show();
            }else{
                showcustomdioloage("Payoner",payoner);
            }
        }
    }
   public void gcashCheck(Setting setting){
        if(setting.getMinW()>coin){
            Toast.makeText(this, "You don not have Enough Coin", Toast.LENGTH_SHORT).show();
        }else{
            int coinValue=setting.getCoinValue();
            double doller=(double)coin/(double)coinValue;
            if(doller<gcash){
                Toast.makeText(this, "You don't Have Enought Coin", Toast.LENGTH_SHORT).show();
            }else{
                showcustomdioloage("GCash",gcash);
            }
        }
    }
  public void plastoreCheck(Setting setting){
        if(setting.getMinW()>coin){
            Toast.makeText(this, "You don not have Enough Coin", Toast.LENGTH_SHORT).show();
        }else{
            int coinValue=setting.getCoinValue();
            double doller=(double)coin/(double)coinValue;
            if(doller<playstore){
                Toast.makeText(this, "You don't Have Enought Coin", Toast.LENGTH_SHORT).show();
            }else{
                showcustomdioloage("Playstore",playstore);
            }
        }
    }

    public void getPaymentData(){
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestQueue requestQueue= Volley.newRequestQueue(WithDrawActivity.this);
        String url=Api.Url+"admin/payment";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("payment");
                    if(array.length()!=0){
                        JSONObject receive=array.getJSONObject(0);
                             bkash=receive.getInt("bkash");
                             paypal=receive.getInt("paypal");
                             gcash=receive.getInt("gcash");
                             paytm=receive.getInt("paytm");
                             jazz=receive.getInt("jazz");
                             payoner=receive.getInt("payoner");
                             playstore=receive.getInt("playstore");
                             amazon=receive.getInt("amazon");

                             bkashTv.setText(""+bkash+"$");
                             paypalTv.setText(""+paypal+"$");
                             gcashTv.setText(""+gcash+"$");
                             paytmTv.setText(""+paytm+"$");
                             jazzTv.setText(""+jazz+"$");
                             payonerTv.setText(""+payoner+"$");
                             playstoreTv.setText(""+playstore+"$");
                             amazonTv.setText(""+amazon+"$");
                    }else{
                      // finish();
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(WithDrawActivity.this, "Failed"+error    .getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                LocalDb localDb=new LocalDb(WithDrawActivity.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization",localDb.getToken());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void showcustomdioloage(String accountType,int amount) {
        AlertDialog.Builder builder=new AlertDialog.Builder(WithDrawActivity.this);
        View view=getLayoutInflater().inflate(R.layout.withdraw_popup,null);
        builder.setView(view);

        EditText accountNoEt=view.findViewById(R.id.accountNo_Et);
        Button sendButton=view.findViewById(R.id.withdraw_SendButton);
        accountNoEt.setHint("Enter "+accountType+" Account No");
        final AlertDialog dialog=builder.create();
        dialog.show();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountNo=accountNoEt.getText().toString();
               if(accountNo.isEmpty()){
                   accountNoEt.setError("Enter Account No");
                   accountNoEt.requestFocus();
                }else{
                   Setting setting=settingDb.getSetting();
                   int coin=setting.getCoinValue()*amount;
                   sendWithdrawRequest(dialog,coin,accountType,accountNo);
                }
            }
        });
    }

    private void sendWithdrawRequest(AlertDialog dialog,int coin,String paymentType,String accountNo) {




        
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

        RequestQueue requestQueue= Volley.newRequestQueue(WithDrawActivity.this);
        String url=Api.Url+"withdraw/";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject=new JSONObject(response);

                    Toast.makeText(WithDrawActivity.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                  } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(WithDrawActivity.this, "Failed"+error    .getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                LocalDb localDb=new LocalDb(WithDrawActivity.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization",localDb.getToken());
                return params;
            }

            @Override
            protected Map<String, String> getParams()  {
                Map<String, String>  parms=new HashMap<String, String>();
                parms.put("coin", String.valueOf(coin));
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
package com.tanvir.itask24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.printservice.CustomPrinterIconCallback;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.tanvir.itask24.Services.InternetCheck;
import com.tanvir.itask24.Services.UserServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private CardView captchaCard,videoCard,hourlyCaptchaCard,premiumCaptchaCard;
    private LinearLayout withdrawLinearLayout;
    private ProgressDialog progressDialog;

    private TextView coinTv,nameTv,dollerTv;

    private CircleImageView profileImageView;
    private  UserModel userModel;
    private  Setting setting;
    private Button walletButton;

    private Button profileButton;
    InternetCheck internetCheck;
    List<String> networkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       init();
        captchaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userModel!=null && setting!=null){
                    long time=setting.getFirstCaptchaDelayTime()+userModel.getCap1LastPlay();
                     if(time>System.currentTimeMillis()){
                         long tTime=time-System.currentTimeMillis();
                         int minutes=(int)(tTime/1000/60);
                         int seconds=(int)(tTime/1000)%60;
                         Toast.makeText(MainActivity.this, "You Are Blocked For "+minutes+"Min : "+seconds+"Sec", Toast.LENGTH_SHORT).show();
                     }else{
                         sendUserToCaptchaActivity("captcha");
                     }
                }
            }
        });

     premiumCaptchaCard.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {


             if(userModel!=null && setting!=null){
                 long time=setting.getSecondCaptchaDelayTime()+userModel.getCap2LastPlay();
                 if(time>System.currentTimeMillis()){
                     long tTime=time-System.currentTimeMillis();
                     int minutes=(int)(tTime/1000/60);
                     int seconds=(int)(tTime/1000)%60;
                     Toast.makeText(MainActivity.this, "You Are Blocked For "+minutes+"Min : "+seconds+"Sec", Toast.LENGTH_SHORT).show();
                 }else{
                     startActivity(new Intent(MainActivity.this,PremiumCaptchaActivity.class));
                 }
             }

         }
     });
     hourlyCaptchaCard.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             if(userModel!=null && setting!=null){
                 long time=setting.getThirdCaptchaDelayTime()+userModel.getCap3LastPlay();
                 if(time>System.currentTimeMillis()){
                     long tTime=time-System.currentTimeMillis();
                     int minutes=(int)(tTime/1000/60);
                     int seconds=(int)(tTime/1000)%60;
                     Toast.makeText(MainActivity.this, "You Are Blocked For "+minutes+"Min : "+seconds+"Sec", Toast.LENGTH_SHORT).show();
                 }else{
                     startActivity(new Intent(MainActivity.this,ProCaptchaActivity.class));
                 }
             }

         }
     });
        videoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userModel!=null && setting!=null){
                    long time=setting.getWatchDelayTime()+userModel.getWatchLastPlay();
                    if(time>System.currentTimeMillis()){
                        long tTime=time-System.currentTimeMillis();
                        int minutes=(int)(tTime/1000/60);
                        int seconds=(int)(tTime/1000)%60;
                        Toast.makeText(MainActivity.this, "You Are Blocked For "+minutes+"Min : "+seconds+"Sec", Toast.LENGTH_SHORT).show();
                    }else{
                        startActivity(new Intent(MainActivity.this,VideoJobActivity.class));
                    }
                }

           }
        });



     withdrawLinearLayout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             sendUserToWithDrawActivity();
         }
     });
    walletButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LocalDb localDb=new LocalDb(MainActivity.this);
            localDb.setUser("");
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();

        }
    });

    profileButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,ProfileActivity.class));
        }
    });




    }
    private void init() {

        walletButton=findViewById(R.id.walletButton);
        progressDialog=new ProgressDialog(this);
        captchaCard=findViewById(R.id.captchaCard);
         internetCheck=new InternetCheck();
        videoCard=findViewById(R.id.videoCard);
        hourlyCaptchaCard=findViewById(R.id.hourlyCaptchaCard);
        premiumCaptchaCard=findViewById(R.id.premiumCaptchaCard);
        withdrawLinearLayout=findViewById(R.id.right_line);


        coinTv=findViewById(R.id.main_CoinTv);
        dollerTv=findViewById(R.id.main_DollerTv);
        nameTv=findViewById(R.id.main_UserNameTv);
        profileImageView=findViewById(R.id.main_UserProfileImage);
        profileButton=findViewById(R.id.profileButton);



    }

    @Override
    protected void onStart() {
        super.onStart();
  // getUserData();
      if(internetCheck.isInternetOn(MainActivity.this)){
           /* if (!checkvpn()) {
                showAlert();
            }else{*/
                getVersion();
                getUserData();

         //   }
        }else{
            showInternetNoneAlert();
        }

    }

    private void showInternetNoneAlert() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Warning...");
        alertDialog.setMessage("No Internet Connection");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Check Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               if( internetCheck.isInternetOn(MainActivity.this)){
                    onStart();
                }else{
                   alertDialog.show();
               }
            }
        });

        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }
    public void showAlert(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Warning...");
        alertDialog.setMessage("আপনি যদি বাংলাদেশ থেকে এই অ্যাপ ব্যবহার করতে চান তাহলে সরাসরি তা পারবেন না .আপনাকে এজন্য প্লে স্টোরে থেকে একটি ভিপিন অ্যাপ ইনস্টল করতে হবে এবং ইউ এস,ইউকে,কানাডা এইরকম দেশ সিলেক্ট করে কানেক্ট করতে হবে .");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Check Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(checkvpn()){
                    onStart();
                }else{
                    alertDialog.show();
                }
            }
        });

        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }
    public void getUserData(){
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
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
                     LocalDb localDb=new LocalDb(MainActivity.this);
                     localDb.setUser(userModel);
                    getAppSettings(userModel);

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Failed"+error    .getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                LocalDb localDb=new LocalDb(MainActivity.this);

                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization",localDb.getToken());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void getVersion(){
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        String url=Api.Url+"version/";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("version");
                    JSONObject receive=array.getJSONObject(0);

                    String versionNO=   receive.getString("versionNo");
                    String priority=   receive.getString("priority");
                    if(!versionNO.isEmpty() && !priority.isEmpty()){
                        double dVersion=Double.parseDouble(versionNO);
                        double cVersion=Double.parseDouble(BuildConfig.VERSION_NAME);
                        if(dVersion>cVersion){
                            showUpdateDiolouge(priority);
                        }
                    }else{
                        progressDialog.dismiss();
                        LocalDb localDb=new LocalDb(MainActivity.this);
                        localDb.setUser("");
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        finish();
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    LocalDb localDb=new LocalDb(MainActivity.this);
                    localDb.setUser("");
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               progressDialog.dismiss();
                LocalDb localDb=new LocalDb(MainActivity.this);
                localDb.setUser("");
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                Toast.makeText(MainActivity.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                LocalDb localDb=new LocalDb(MainActivity.this);

                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization",localDb.getToken());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void showUpdateDiolouge(String priority) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Warning...");
        alertDialog.setMessage("New Update Available On Playstore");
        if(priority.equals("1")){
            alertDialog.setCancelable(false);
        }else{
            alertDialog.setCancelable(true);
            alertDialog.setNegativeButton("Update Later", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        alertDialog.setPositiveButton("Check Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }
    public void getAppSettings(UserModel userModel){
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        String url=Api.Url+"admin/setting";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("setting");
                    if(array.length()!=0){
                        JSONObject receive=array.getJSONObject(0);
                        setting=new Setting(
                                receive.getInt("bkash"),
                                receive.getInt("paytm"),
                                receive.getInt("coinValue"),
                                receive.getLong("firstCaptchaDelayTime"),
                                receive.getLong("secondCaptchaDelayTime"),
                                receive.getLong("thirdCaptchaDelayTime"),
                                receive.getLong("fourthCaptchaDelayTime"),
                                receive.getLong("watchDelayTime"),
                                receive.getInt("cap1"),
                                receive.getInt("cap2"),
                                receive.getInt("cap3"),
                                receive.getInt("cap4"),
                                receive.getInt("offer"),
                                receive.getInt("minW"),
                                receive.getString("showOffer")
                        );
                        SettingDb settingDb=new SettingDb(MainActivity.this);
                        settingDb.setSetting(setting);
                        nameTv.setText(userModel.getName());
                        coinTv.setText(""+userModel.getCoins());
                        int coin=userModel.getCoins();
                        int coinValue=setting.getCoinValue();
                        double doller=(double)coin/(double)coinValue;
                        dollerTv.setText("$"+new DecimalFormat("##.###").format(doller));

                    }else{
                        nameTv.setText(userModel.getName());
                        coinTv.setText(""+userModel.getCoins());
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
                Toast.makeText(MainActivity.this, "Failed"+error    .getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                LocalDb localDb=new LocalDb(MainActivity.this);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization",localDb.getToken());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void sendUserToWithDrawActivity() {
        Intent intent=new Intent(MainActivity.this,WithDrawActivity.class);
        startActivity(intent);
    }
    private void sendUserToCaptchaActivity(String txt) {
        Intent intent=new Intent(MainActivity.this,CaptchaActivity.class);
        intent.putExtra("text",txt);
        startActivity(intent);
    }
    public boolean checkvpn(){
       networkList = new ArrayList<>();
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.isUp()) {
                    networkList.add(networkInterface.getName());
                }
            }
        } catch (Exception ex) {
        }
        return networkList.contains("tun0");
    }
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { }
        return "";
    }



}
package com.tanvir.itask24;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tanvir.itask24.LocalDatabase.LocalDb;
import com.tanvir.itask24.Model.Setting;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEt,passwordEt,nameEt,phoneEt,referEt;
    private Button registeButton;
    private ProgressDialog progressDialog;
    private TextView loginButton;
    private String refer="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog=new ProgressDialog(this);

        emailEt=findViewById(R.id.r_EmailEt);
        passwordEt=findViewById(R.id.r_PasswordEt);
        phoneEt=findViewById(R.id.r_PhoneEt);
        nameEt=findViewById(R.id.r_NameEt);
        referEt=findViewById(R.id.r_ReferEt);

        loginButton=findViewById(R.id.r_LoginButton);
        registeButton=findViewById(R.id.r_RegisterButton);

        registeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailEt.getText().toString();
                String password=passwordEt.getText().toString();
                String name=nameEt.getText().toString();
                String phone=phoneEt.getText().toString();
                 refer=referEt.getText().toString();

                if(email.isEmpty()){
                    emailEt.setError("Write Your Email");
                    emailEt.requestFocus();
                }else if(password.isEmpty()){
                    passwordEt.setError("Password is Required");
                    passwordEt.requestFocus();
                }else if(name.isEmpty()){
                    nameEt.setError("Name is Required");
                    nameEt.requestFocus();
                }else if(phone.isEmpty()){
                    phoneEt.setError("Enter Your Phone");
                    phoneEt.requestFocus();
                }else{
                    registerUser(email,password,phone,name,refer);
                }



            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToLoginActivity();
            }
        });

    }
    public void registerUser(String email,String password,String phone,String name,String refer){

        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        @SuppressLint("HardwareIds") String deviceID= Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

        RequestQueue requestQueue= Volley.newRequestQueue(RegisterActivity.this);
        String url=Api.Url+"user/signup";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject=new JSONObject(response);
                    Toast.makeText(RegisterActivity.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    sendUserToLoginActivity();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Something Wrong.please Try Again Letter", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String>  parms=new HashMap<String, String>();
                parms.put("email",email);
                parms.put("password",password);
                parms.put("name",name);
                parms.put("phone",phone);
                parms.put("deviceId",deviceID);
                parms.put("myRefer",String.valueOf(System.currentTimeMillis()));
                parms.put("refer",refer);
                return  parms;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void sendUserToLoginActivity(){
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }
    public void sendUserToMainActivity(){
        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
        finish();
    }
}
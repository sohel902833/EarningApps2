package com.tanvir.itask24;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEt,passwordEt;
    private Button loginButton;
    private ProgressDialog progressDialog;

    TextView registeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        progressDialog=new ProgressDialog(this);

        emailEt=findViewById(R.id.l_EmailEt);
        passwordEt=findViewById(R.id.l_PasswordEt);
        loginButton=findViewById(R.id.l_LoginButton);
        registeButton=findViewById(R.id.l_RegisteButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailEt.getText().toString();
                String password=passwordEt.getText().toString();

                if(email.isEmpty()){
                    emailEt.setError("Write Your Email");
                    emailEt.requestFocus();
                }else if(password.isEmpty()){
                    passwordEt.setError("Password is Required");
                    passwordEt.requestFocus();
                }else{
                    loginUser(email,password);
                }



            }
        });



        registeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToRegisterActivity();
            }
        });
    }

    public void loginUser(String email,String password){

        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();


        RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);
        String url=Api.Url+"user/login";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject=new JSONObject(response);
                    LocalDb localDb=new LocalDb(LoginActivity.this);
                    localDb.setUser("Bearer "+jsonObject.getString("access_token"));
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    sendUserToMainActivity();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Failed"+error    .getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String>  parms=new HashMap<String, String>();
                parms.put("email",email);
                parms.put("password",password);
                return  parms;
            }
        };
        requestQueue.add(stringRequest);
    }


    public void sendUserToRegisterActivity(){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        finish();
    }
    public void sendUserToMainActivity(){
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalDb localDb=new LocalDb(this);
        if(!localDb.getToken().equals("")){
            sendUserToMainActivity();
        }

    }
}
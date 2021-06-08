package com.tanvir.itask24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tanvir.itask24.Adapters.WithdrawAdapter;
import com.tanvir.itask24.LocalDatabase.LocalDb;
import com.tanvir.itask24.LocalDatabase.SettingDb;
import com.tanvir.itask24.Model.Setting;
import com.tanvir.itask24.Model.WithdrawModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransectionActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private List<WithdrawModel> withdrawModelList=new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView userNameTv;

    private WithdrawAdapter adapter;
    private SettingDb settingDb;
    private Setting setting;
    private String name="";
    private  int coin=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transection);

        name=getIntent().getStringExtra("name");
        coin=getIntent().getIntExtra("coin",0);

        init();

        userNameTv.setText(""+name);
        setting=settingDb.getSetting();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new WithdrawAdapter(this,withdrawModelList,setting.getCoinValue());
        recyclerView.setAdapter(adapter);


    }

    private void init() {
        progressDialog=new ProgressDialog(this);
        userNameTv=findViewById(R.id.userName);
        recyclerView=findViewById(R.id.withdraw_ListRecyclerViewid);
        settingDb=new SettingDb(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllWithdrawList();
    }
    public void getAllWithdrawList(){
    progressDialog.setMessage("Loading...");
    progressDialog.show();
    progressDialog.setCancelable(false);

        RequestQueue requestQueue= Volley.newRequestQueue(TransectionActivity.this);
      String url=Api.Url+"withdraw/u/single";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();

                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("withdrawRequests");
                    withdrawModelList.clear();

                    for(int i=0; i<array.length(); i++){
                        JSONObject receive=array.getJSONObject(i);
                        WithdrawModel withdrawModel=new WithdrawModel(
                                receive.getString("userId"),
                                receive.getString("time"),
                                receive.getString("payment"),
                                receive.getString("coins"),
                                receive.getString("accountNo"),
                                receive.getString("state")
                        );

                        withdrawModelList.add(withdrawModel);
                        adapter.notifyDataSetChanged();
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
                Toast.makeText(TransectionActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                LocalDb localDb=new LocalDb(TransectionActivity.this);

                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization",localDb.getToken());
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }


}
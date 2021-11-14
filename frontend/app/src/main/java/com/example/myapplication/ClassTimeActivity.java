package com.example.myapplication;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class ClassTimeActivity extends AppCompatActivity {

    private EditText s_time, q_time;
    private Button s_class, q_class;
    //private TextView link_regist;
    private ProgressBar loading;
    private static String URL_LOGIN = "http://140.115.87.221:8080/POST/courseRecord";//填入資料庫位置(http://....)


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_time);

        loading = findViewById(R.id.loading);
        s_time = findViewById(R.id.s_time);
        q_time = findViewById(R.id.q_time);
        s_class = findViewById(R.id.s_class);
        q_class = findViewById(R.id.q_class);
        //link_regist = findViewById(R.id.link_regist);

        s_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stime = s_time.getText().toString().trim();

                if(!stime.isEmpty())
                {
                    Post1(stime);
                }else{
                    s_time.setError("請輸入上課時間");
                }
            }
        });

        q_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qtime = q_time.getText().toString().trim();

                if(!qtime.isEmpty())
                {
                    Post2(qtime);
                }else{
                    q_time.setError("請輸入下課時間");
                }
            }
        });

//        link_regist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Login.this, MainActivity.class));
//            }
//        });
//
    }


    private void Post1 ( final String s_time)
    {
        loading.setVisibility(View.VISIBLE);
        s_class.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("post");

                    if(success.equals("1")){
                        for(int i =0; i < jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String s_time= object.getString("s_time").trim();

                            Toast.makeText(ClassTimeActivity.this, "Success Post. \n上課時間是:"+s_time,Toast.LENGTH_SHORT).show();

                            loading.setVisibility(View.GONE);

                        }
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                    loading.setVisibility(View.GONE);
                    s_class.setVisibility(View.VISIBLE);
                    Toast.makeText(ClassTimeActivity.this, "Error"+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        s_class.setVisibility(View.VISIBLE);
                        Toast.makeText(ClassTimeActivity.this, "Error"+error.toString(),Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override

            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("courseID",s_time);
                params.put("courseStartTime",s_time);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        JsonObjectRequest objectRequest=new JsonObjectRequest(
                Request.Method.POST,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response",response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Reset Response",error.toString());
                    }
                }
        );


    }
    private void Post2 ( final String q_time)
    {
        loading.setVisibility(View.VISIBLE);
        s_class.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("post");

                    if(success.equals("1")){
                        for(int i =0; i < jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String q_time= object.getString("q_time").trim();

                            Toast.makeText(ClassTimeActivity.this, "Success Post. \n上課時間是:"+q_time,Toast.LENGTH_SHORT).show();

                            loading.setVisibility(View.GONE);

                        }
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                    loading.setVisibility(View.GONE);
                    q_class.setVisibility(View.VISIBLE);
                    Toast.makeText(ClassTimeActivity.this, "Error"+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        q_class.setVisibility(View.VISIBLE);
                        Toast.makeText(ClassTimeActivity.this, "Error"+error.toString(),Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("courseID",q_time);
                params.put("courseEndTime",q_time);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
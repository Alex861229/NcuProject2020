package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainPageActivityTeacher extends AppCompatActivity {

    private ArrayList<String> mOne = new ArrayList<>();
    private ArrayList<String> mMon = new ArrayList<>();
    private ArrayList<String> mTue = new ArrayList<>();
    private ArrayList<String> mWed = new ArrayList<>();
    private ArrayList<String> mThu = new ArrayList<>();
    private ArrayList<String> mFri = new ArrayList<>();
    private Context mContext;

    private Button attendAnalysis;

    private static final String URL_PRODUCTS = "http://www.json-generator.com/api/json/get/cpfSJTGWtK?indent=2";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
//    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_teacher);
        recyclerView = findViewById(R.id.recycler_view_teacher);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        attendAnalysis = findViewById(R.id.attendAnalysis);

        mAdapter = new RecyclerViewAdapter(this, mOne, mMon, mTue, mWed, mThu, mFri);
        recyclerView.setAdapter(mAdapter);

        loadSchedule();

        attendAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivityTeacher.this, AttendantAnalysis.class);
                startActivity(intent);
            }
        });
    }

    private void loadSchedule() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject schedule = array.getJSONObject(i);

                                //adding the product to product list
                                mOne.add(schedule.getString("Class"));
                                mMon.add(schedule.getString("Mon"));
                                mTue.add(schedule.getString("Tue"));
                                mWed.add(schedule.getString("Wed"));
                                mThu.add(schedule.getString("Thu"));
                                mFri.add(schedule.getString("Fri"));

                            }

                            //creating adapter object and setting it to recyclerview
                            RecyclerViewAdapter adapter = new RecyclerViewAdapter(MainPageActivityTeacher.this, mOne, mMon, mTue, mWed, mThu, mFri);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


}

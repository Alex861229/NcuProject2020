package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText id, password;
    private Button btn_login;
    private static String URL_LOGIN = "http://www.json-generator.com/api/json/get/cagfxjTpbC?indent=2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btnLogin);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(privilege == 1){}
//                Intent intent = new Intent (MainActivity.this,MainPageActivity.class);
//                startActivity(intent);
//                if(privilege == 0){}

                String mId = id.getText().toString().trim();
                String mPass = password.getText().toString().trim();

                if(!mId.isEmpty() && !mPass.isEmpty())
                {
                    Login(mId,mPass);
                }else{
                    id.setError("Please insert id");
                    password.setError("Please insert password");
                }
            }
        });
    }

    private void Login(final String id, final String password)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject verify = jsonArray.getJSONObject(0);

                    //之後要加privilege跟token
//                    if(verify.getString("privilege").equals("1")){
                    if(id.equals("t") && password.equals("t")){
                        Intent intent = new Intent (MainActivity.this,MainPageActivityTeacher.class);
                        startActivity(intent);
                    }
                    else if(id.equals("s") && password.equals("s")){
                        Intent intent = new Intent (MainActivity.this,MainPageActivity.class);
                        startActivity(intent);
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error"+e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error"+error.toString(),Toast.LENGTH_SHORT).show();

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                params.put("password",password);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

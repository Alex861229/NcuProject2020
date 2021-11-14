package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TakePicture extends AppCompatActivity {

    private Button photoBtn;
    private ImageView photo;
    private TextView test;
    private static String URL_PHOTO = "";
    private String image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);

        photo = findViewById(R.id.photo);
        photoBtn = findViewById(R.id.photo_btn);
//        test = findViewById(R.id.test);

        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File tmpFile = new File(getExternalFilesDir(null),"image.jpg");
                Uri outputFileUri = Uri.fromFile(tmpFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(intent, 0);
//                test.setText(outputFileUri.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(getExternalFilesDir(null) + "/image.jpg");
            photo.setImageBitmap(bitmap);
            image = bitmap.toString();
        }
    }

    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PHOTO, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            Toast.makeText(TakePicture.this, "Upload...", Toast.LENGTH_LONG).show();
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Toast.makeText(TakePicture.this, "Error"+error.toString(),Toast.LENGTH_SHORT).show();
        }
    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError
        {
            Map<String, String> params = new HashMap<>();
            params.put("picture", image);
            return params;
        }
    };

}

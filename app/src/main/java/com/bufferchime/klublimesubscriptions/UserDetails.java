package com.bufferchime.klublimesubscriptions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class UserDetails extends AppCompatActivity implements View.OnClickListener {


    EditText edittextname,edittextaddress,edittextemail,edittextcit;
    Button buttonAddItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_details);

        edittextname = (EditText)findViewById(R.id.input_name);
        edittextaddress = (EditText)findViewById(R.id.input_address);
        edittextemail = (EditText)findViewById(R.id.input_email);
        edittextcit = (EditText)findViewById(R.id.input_city);

        buttonAddItem = (Button)findViewById(R.id.btn_submit);
        buttonAddItem.setOnClickListener(this);


    }

    //This is the part where data is transafeered from Your Android phone to Sheet by using HTTP Rest API calls

    private void   addItemToSheet() {

        final ProgressDialog loading = ProgressDialog.show(this,"Submitting Details","Please wait");
        final String name = edittextname.getText().toString();
        final String address = edittextaddress.getText().toString();
        final String email = edittextemail.getText().toString();
        final String city = edittextcit.getText().toString();




        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxu2CL3TK1iYWfS8FjE6ocUoDYAWsbYvatbaEUQVFKBfDqoEwI/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(UserDetails.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),GamePlayActivity.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action","addItem");
                parmas.put("name",name);
                parmas.put("address",address);
                parmas.put("email",email);
                parmas.put("city",city);

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }




    @Override
    public void onClick(View v) {

        if(v==buttonAddItem){
            addItemToSheet();

            //Define what to do when button is clicked
        }
    }
}
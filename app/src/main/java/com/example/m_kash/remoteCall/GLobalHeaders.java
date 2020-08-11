package com.example.m_kash.remoteCall;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;



import org.json.JSONException;
import org.json.JSONObject;


public class GLobalHeaders {




    public  static  JSONObject  getGlobalHeaders(Context context){
        JSONObject headers = new  JSONObject();
        try {
            headers.put("Content-Type", "application/json");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return headers;
    }
}

package com.example.m_kash.remoteCall;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import com.example.m_kash.dialogs.NeedNetworkRequired;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class NetworkConnection {
    private static Application myapp;

    private static MediaType mediaType = MediaType.parse("application/json");

    public static boolean isConnectedToInternet(Application application) {
        myapp = application;
        myapp.getSystemService(Context.CONNECTIVITY_SERVICE);
        ConnectivityManager connectivityManager = (ConnectivityManager) myapp.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo == null ? false : true;
    }
    public static  void uploadImageToRemoteServer(String url, File imagefile, JSONObject headers, final OnReceivingResult onReceivingResult){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file","file",RequestBody.create(MediaType.parse("image/png"),imagefile)).build();

        Request.Builder request= new  Request.Builder().url(url);
        if(headers!=null) {
            Iterator<String> keys = headers.keys();
            while (keys.hasNext()) {
                String ourkey = keys.next();
                try {

                    request.addHeader(ourkey, headers.getString(ourkey));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        request.post(requestBody);
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                seriesRouter(e,onReceivingResult);


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                seriesRouter(response,onReceivingResult);



            }
        });

    }
    public static void makeAPutRequest(String url, JSONObject headers,final OnReceivingResult receivingResult) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder().url(url);

        if(headers!=null) {
            Iterator<String> keys = headers.keys();
            while (keys.hasNext()) {
                String ourkey = keys.next();
                try {
                    request.addHeader(ourkey, headers.getString(ourkey));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        request.put(new RequestBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {

            }
        });
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                seriesRouter(e,receivingResult);


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                seriesRouter(response,receivingResult);


            }

        });

    }

    public static void makeAPutRequest(String url, final String body, JSONObject headers,final OnReceivingResult receivingResult) {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(mediaType, body);
        Request.Builder request = new Request.Builder().url(url);

        if(headers!=null) {
            Iterator<String> keys = headers.keys();
            while (keys.hasNext()) {
                String ourkey = keys.next();
                try {
                    request.addHeader(ourkey, headers.getString(ourkey));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        request.put(requestBody);
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                seriesRouter(e,receivingResult);

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                seriesRouter(response,receivingResult);


            }
        });

    }
    public  static  void downloadImage(String url, final OnReceivingResult receivingResult){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder().url(url);

        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                seriesRouter(e,receivingResult);


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                seriesRouter(response,receivingResult);

            }
        });
    }
    public  static  void downloadImage(String url,JSONObject headers, final OnReceivingResult receivingResult){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder().url(url);
        if(headers!=null) {
            Iterator<String> keys = headers.keys();
            while (keys.hasNext()) {
                String ourkey = keys.next();
                try {
                    request.addHeader(ourkey, headers.getString(ourkey));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                seriesRouter(e,receivingResult);


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                seriesRouter(response,receivingResult);

            }
        });
    }
    public static void makeAPostRequest(String url, final String body, JSONObject headers, final OnReceivingResult receivingResult) {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(mediaType, body);
        Request.Builder request = new Request.Builder().url(url);

        if(headers!=null) {
            Iterator<String> keys = headers.keys();
            while (keys.hasNext()) {
                String ourkey = keys.next();
                try {
                    request.addHeader(ourkey, headers.getString(ourkey));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        request.post(requestBody);
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                seriesRouter( e,receivingResult);


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                seriesRouter(response,receivingResult);


            }
        });

    }
    public static void makeAGetRequest(String url, JSONObject headers, final OnReceivingResult receivingResult) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder().url(url);
        if (headers!=null){
            Iterator<String> keys = headers.keys();
            while (keys.hasNext()) {
                String ourkey = keys.next();
                try {
                    request.addHeader(ourkey, headers.getString(ourkey));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                seriesRouter( e,receivingResult);
            }

            @Override
            public void onResponse(Call call, final Response response) {

                seriesRouter( response,receivingResult);

            }
        });

    }
    public static void makeAGetRequest(URL url, JSONObject headers, final OnReceivingResult receivingResult) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder().url(url);
        Iterator<String> keys = headers.keys();
        while (keys.hasNext()) {
            String ourkey = keys.next();
            try {
                request.addHeader(ourkey, headers.getString(ourkey));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                seriesRouter(e ,receivingResult);

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                seriesRouter( response,receivingResult);


            }
        });

    }
    //check the http response code and route accordingly code in http series i.e 100 series,200 series, 300 series upto 500 series
    private static void seriesRouter(final Response response, final OnReceivingResult onReceivingResult){
        final int code=response.code();
        if (response.body() == null) {
            onReceivingResult.onErrorResult(new IOException("Response body was null"));
            return;
        }
        try {
            final byte[] responsebody=response.body().bytes();
            final String our_dear_response = new String(responsebody);

            if (code>=100&&code<200){

                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        RemoteResponse remoteResponse= new RemoteResponse(code,our_dear_response);
                        remoteResponse.setResponseBody(responsebody);
                        onReceivingResult.onReceiving100SeriesResponse(remoteResponse);

                    }
                });

                return;
            }
            if (code>=200&&code<300){
                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        RemoteResponse remoteResponse= new RemoteResponse(code,our_dear_response);
                        remoteResponse.setResponseBody(responsebody);
                        onReceivingResult.onReceiving200SeriesResponse(remoteResponse);
                    }
                });

                return;
            }
            if (code>=300&&code<400){
                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        onReceivingResult.onReceiving300SeriesResponse(new RemoteResponse(code,our_dear_response));

                    }
                });

                return;
            }
            if (code>=400&&code<500){
                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        onReceivingResult.onReceiving400SeriesResponse(new RemoteResponse(code,our_dear_response));


                    }
                });


                return;
            }
            if (code>=500&&code<600){
                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        onReceivingResult.onReceiving500SeriesResponse(new RemoteResponse(code,our_dear_response));

                    }
                });



                return;
            }
            onReceivingResult.onErrorResult( new IOException(our_dear_response));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void seriesRouter(final IOException ioexception, final OnReceivingResult onReceivingResult){
        new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                onReceivingResult.onErrorResult( ioexception);
            }
        });



    }
    public  static  void remoteResponseLogger(RemoteResponse remoteResponse){
        Log.e("Remote response","Code is :"+remoteResponse.getCode()+" "+remoteResponse.getMessage());
    }
    public static void generalJsonLogger(String tag,JSONObject jsonObject){
        Log.e(tag,jsonObject.toString());
    }
    /* public static JSONObject getGlobalHeaders(){
         JSONObject header= new JSONObject();
         try {
             header.put("accept", "application/json");
             header.put("Authorization", "Bearer " + Authenticate.getToken().getTokenString());

         } catch (JSONException e) {
             e.printStackTrace();
         }
         return  header;
     }*/
    public  static  void makeAPostOfQueryParams(String url, JSONObject headers, Map<String,String> queryParams, final OnReceivingResult onReceivingResult){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder();
        HttpUrl httpUrl=HttpUrl.parse(url);
        Iterator<String> keys = headers.keys();
        if (queryParams!=null) {
            HttpUrl.Builder builder = new HttpUrl.Builder();
            for (Map.Entry<String, String> query : queryParams.entrySet()){
                Log.e("query is",query.toString());

                try {
                    URL url1= new URL(url);

                    httpUrl = builder.addQueryParameter(query.getKey(), query.getValue()).scheme("http").host(url1.getHost()).encodedPath(url1.getPath()).build();
                    Log.e("Search path",httpUrl.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            request.url(httpUrl);
        }
        while (keys.hasNext()) {
            String ourkey = keys.next();
            try {
                request.addHeader(ourkey, headers.getString(ourkey));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                seriesRouter(e ,onReceivingResult);

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                seriesRouter( response,onReceivingResult);


            }
        });
    }
    public static  void invokeNetworkDialog(AppCompatActivity appCompatActivity){
        NeedNetworkRequired needNetworkRequired= NeedNetworkRequired.getInstance();
        if (needNetworkRequired.isVisible()){
            return;
        }
        needNetworkRequired.show(appCompatActivity.getSupportFragmentManager(),"network");

    }
}

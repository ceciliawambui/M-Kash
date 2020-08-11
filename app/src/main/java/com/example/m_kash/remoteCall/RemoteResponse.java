package com.example.m_kash.remoteCall;

import org.json.JSONException;
import org.json.JSONObject;

public class RemoteResponse {
    private int Code;
    private String message;
    private byte[] responseBody;

    public byte[] getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(byte[] responseBody) {
        this.responseBody = responseBody;
    }
    public RemoteResponse() {

    }
    public RemoteResponse(int code, String message) {
        Code = code;
        this.message = message;
    }
    public RemoteResponse(int code, String message, byte[] responseBody) {
        this(code,message);
        this.responseBody=responseBody;
    }
    public JSONObject getJSONObjectResponse(){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("code",getCode());
            jsonObject.put("message",getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }
    public JSONObject getMessangeAsJSON(){
        try {
            return  new JSONObject( message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        message = message;
    }
}

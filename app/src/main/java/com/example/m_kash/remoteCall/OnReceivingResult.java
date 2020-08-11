package com.example.m_kash.remoteCall;


import java.io.IOException;

public interface OnReceivingResult {
    public  void  onErrorResult(IOException e);
    public void onReceiving100SeriesResponse(RemoteResponse remoteResponse);
    public  void onReceiving200SeriesResponse(RemoteResponse remoteResponse);
    public  void  onReceiving300SeriesResponse(RemoteResponse remoteResponse);
    public  void  onReceiving400SeriesResponse(RemoteResponse remoteResponse);
    public  void  onReceiving500SeriesResponse(RemoteResponse remoteResponse);
}
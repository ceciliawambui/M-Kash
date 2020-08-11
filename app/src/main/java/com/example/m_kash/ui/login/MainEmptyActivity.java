package com.example.m_kash.ui.login;

import android.content.Intent;
import android.media.MediaSession2;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.m_kash.MainActivity;

public class MainEmptyActivity extends AppCompatActivity {
    private MediaSession2 Util;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent activityIntent;

        // go straight to main if a token is stored
        Util.getToken();
        activityIntent = new Intent(this, MainActivity.class);

        startActivity(activityIntent);
        finish();
    }
}

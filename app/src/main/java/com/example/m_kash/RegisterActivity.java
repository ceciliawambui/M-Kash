package com.example.m_kash;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.m_kash.R;
import com.example.m_kash.database.SqliteHelper;
import com.example.m_kash.database.User;
import com.example.m_kash.database.myDbAdapter;
import com.example.m_kash.remoteCall.BaseClassURLs;
import com.example.m_kash.remoteCall.GLobalHeaders;
import com.example.m_kash.remoteCall.NetworkConnection;
import com.example.m_kash.remoteCall.OnReceivingResult;
import com.example.m_kash.remoteCall.RemoteResponse;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class RegisterActivity extends AppCompatActivity {

    //Declaration EditTexts
    EditText editTextUserName;
    EditText editTextEmail;
    EditText editTextPassword;

    //Declaration Button
    Button buttonRegister;

    //Declaration SqliteHelper
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sqliteHelper = new SqliteHelper(this);

        setTitle("Register");
        initTextViewLogin();
        initViews();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = editTextUserName.getText().toString();
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    //Check in the database is there any user associated with  this email
                    if (!sqliteHelper.isEmailExists(Email)) {

                        myDbAdapter mydbadapter = new myDbAdapter(RegisterActivity.this);
                        mydbadapter.signUp(UserName, Email, Password);

                        //Email does not exist now add new user to database
                        Snackbar.make(buttonRegister, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    }else {

                        //Email exists with email input provided so show error user already exist
                        Snackbar.make(buttonRegister, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }


                        //Email does not exist now add new user to database
                       // sqliteHelper.addUser(new User(null, UserName, "", Password));

                        JSONObject jsonObject = new JSONObject();


                        try {
                            jsonObject.put("FNAME",UserName);
                            jsonObject.put("SNAME", UserName);
                            jsonObject.put("LNAME", UserName);
                            jsonObject.put("PASSWORD", Password);
                            jsonObject.put("USERNAME", UserName);
                            jsonObject.put("LOCATION", "singups");
                            jsonObject.put("action", "singups");


                            Log.d("lets see", jsonObject.toString());

                            NetworkConnection.makeAPostRequest(BaseClassURLs.BASE_SHORT_CODE, jsonObject.toString(), GLobalHeaders.getGlobalHeaders(RegisterActivity.this), new OnReceivingResult() {
                                @Override
                                public void onErrorResult(IOException e) {

                                }

                                @Override
                                public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {
                                    Log.d("lets see 100", remoteResponse.toString());
                                }

                                @Override
                                public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                                    Log.d("lets see 1", remoteResponse.toString());
                                    Toast.makeText(RegisterActivity.this,"User created successfully! Please Login ",Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {
                                    Log.d("lets see 300", remoteResponse.toString());
                            }

                                @Override
                                public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {
                                    Log.d("lets see 400", remoteResponse.toString());
                                }

                                @Override
                                public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {
                                    Log.d("lets see 500", remoteResponse.toString());
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }








                }
            }
        });
    }

    //this method used to set Login TextView click event
    private void initTextViewLogin() {
        TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //this method is used to connect XML views to its Objects
    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String UserName = editTextUserName.getText().toString();
        String Password = editTextPassword.getText().toString();
        String Email = editTextEmail.getText().toString();


        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            Toast.makeText(RegisterActivity.this,"Please enter valid username!",Toast.LENGTH_LONG).show();

        } else {
            if (UserName.length() > 5) {
                valid = true;

            } else {
                valid = false;

                Toast.makeText(RegisterActivity.this,"Username is too short!",Toast.LENGTH_LONG).show();

            }
        }



        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            Toast.makeText(RegisterActivity.this,"Please enter valid password!",Toast.LENGTH_LONG).show();

        } else {
            if (Password.length() > 5) {
                valid = true;

            } else {
                valid = false;
                Toast.makeText(RegisterActivity.this,"Password is too short!",Toast.LENGTH_LONG).show();

            }
        }
        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            editTextEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            editTextEmail.setError(null);
        }


        return valid;
    }
}
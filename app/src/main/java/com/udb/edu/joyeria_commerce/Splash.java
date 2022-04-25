package com.udb.edu.joyeria_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

public class Splash extends AppCompatActivity {
    Handler handler;
    String nombre,contraseña;

    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        getSupportActionBar().hide();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
               nombre= settings.getString("email","");
               if(TextUtils.isEmpty(nombre)){
                   Intent intent=new Intent(getApplicationContext(),Login.class);
                   startActivity(intent);
                   finish();
               }else{
                   Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                   startActivity(intent);
                   finish();
               }
            }
        },3000);
    }
}
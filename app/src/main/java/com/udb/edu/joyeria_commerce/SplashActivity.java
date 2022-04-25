package com.udb.edu.joyeria_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import sv.edu.udb.appfarmaciadsm.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Código para que la splashscreen se muestre por poco tiempo
        Thread thread = new Thread(){
            public void run(){
                try {

                    sleep(1575);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent newIntent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(newIntent);
                    finish();
                }
            }

        };
        thread.start();
    }
}
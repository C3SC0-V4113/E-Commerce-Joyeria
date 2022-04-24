package com.udb.edu.joyeria_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boton=findViewById(R.id.button);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ProductoDetalle(){
        Intent intent=new Intent(MainActivity.this, ProductoDetalleActivity.class);
        startActivity(intent);
    }
}
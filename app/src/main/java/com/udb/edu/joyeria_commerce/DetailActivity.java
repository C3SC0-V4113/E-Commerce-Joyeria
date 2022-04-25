package com.udb.edu.joyeria_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import self.is.ccahill.com.au.shapelist.MainActivity;
import com.udb.edu.joyeria_commerce.Filtro;

public class DetailActivity extends AppCompatActivity
{
    Joyas selectedJoyas;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSelectedJoyas();
        setValues();

    }

    private void getSelectedJoyas()
    {
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        selectedJoyas = Filtro.joyasList.get(Integer.valueOf(parsedStringID));
    }

    private void setValues()
    {
        TextView tv = (TextView) findViewById(R.id.joyasName);
        ImageView iv = (ImageView) findViewById(R.id.joyasImage);

        tv.setText(selectedJoyas.getName());
        iv.setImageResource(selectedJoyas.getImage());
    }
}
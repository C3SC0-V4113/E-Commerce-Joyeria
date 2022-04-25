package com.udb.edu.joyeria_commerce;

import android.graphics.Color;
import android.util.Log;

public class ColorModel {
    private int color;

    public ColorModel(String color){
        Log.w("MODELO COLOR: ",color);
        this.color=Color.parseColor(color);
        Log.w("COLOR: ", String.valueOf(this.color));
    }

    public int getColorModel(){
        return color;
    }
}

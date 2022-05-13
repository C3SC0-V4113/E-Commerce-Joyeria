package com.udb.edu.joyeria_commerce.datos;

import android.graphics.Color;
import android.util.Log;

public class ColorModel {
    private int color;

    public ColorModel(String color){
        this.color=Color.parseColor(color);
    }

    public int getColorModel(){
        return color;
    }
}

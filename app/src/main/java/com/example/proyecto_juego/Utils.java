package com.example.proyecto_juego;

import android.util.Log;

public class Utils {

    public static int generarRandom(int min, int max){

        //Random g = new Random();
        //  int random = g.nextInt(max - min + 1) + min;
        int random = min + (int) (Math.random()*(float)(max-min));
        Log.i("Random",""+random);
        return random;
    }
}

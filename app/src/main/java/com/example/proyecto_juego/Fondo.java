package com.example.proyecto_juego;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;

public class Fondo {
    //AQyu se crea el fondo de 0
    public PointF posicion;
    public Bitmap imagen;

    public Fondo(Bitmap imagen,float x, float y){
        this.imagen=imagen;
        this.posicion=new PointF(x,y);
    }

    public Fondo(Bitmap imagen,int altoPantalla) {
        this(imagen,0, altoPantalla - imagen.getHeight());
    }

    public void mover(int velocidad){
        posicion.y +=velocidad;
    }
}

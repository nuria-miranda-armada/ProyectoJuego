package com.example.proyecto_juego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

public class Etiqueta {
    private Bitmap imagen;
    public PointF posicion;
    Rect hitbox;
    Paint petiqueta,ptexto;
    String nombre = "Puntuacion";
    private int tamx;
    private int tamy;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Etiqueta(String nombre,float x, float y,int tamx, int tamy){

        this.posicion = new PointF(x,y);
        petiqueta = new Paint();
        ptexto = new Paint();
        this.nombre=nombre;
        ptexto.setColor(Color.WHITE);
        ptexto.setTextAlign(Paint.Align.CENTER);
        ptexto.setTextSize(tamy/2);
        petiqueta.setColor(Color.RED);
        //   pboton.setStyle(Paint.Style.STROKE);

        this.tamy=tamy;
        this.tamx=tamx;
        setHitbox();
    }

    public void setHitbox(){
        int x =(int) posicion.x;
        int y =(int) posicion.y;
        hitbox = new Rect (x,y, x+tamx,y+tamy);
    }
    public void dibujar(Canvas c){
        c.drawRect(hitbox, petiqueta);
        c.drawText(nombre,hitbox.centerX(),hitbox.centerY()+petiqueta.getTextSize(),ptexto);
    }
}

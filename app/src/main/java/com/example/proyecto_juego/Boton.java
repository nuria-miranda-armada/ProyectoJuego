package com.example.proyecto_juego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

public class Boton {
    private Bitmap imagen;
    public PointF posicion;
    Rect hitbox;
    Paint pboton,ptexto;
    String nombre = "Jugar";
    private int tamx;
    private int tamy;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //CREACION DE BOTOTENES
    //POSIBILIDAD DE QUE SEA EL PROPIO TEXTO EL QUE TE LLEVE ALLI
    //VISUALIZACION DE ENEMIGOS ¿que falla?
    public Boton(String nombre,float x, float y,int tamx, int tamy){

        this.posicion = new PointF(x,y);
        pboton = new Paint();
        ptexto = new Paint();
        this.nombre=nombre;
        ptexto.setColor(Color.WHITE);
        ptexto.setTextAlign(Paint.Align.CENTER);
        ptexto.setTextSize(tamy/2);
        pboton.setColor(Color.BLUE);
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
        c.drawRect(hitbox, pboton);
        c.drawText(nombre,hitbox.centerX(),hitbox.centerY()+pboton.getTextSize(),ptexto);
    }

    public boolean pulso(int x, int y){
        return hitbox.contains(x,y);
    }

}

package com.example.proyecto_juego.Pantallas;

import static android.view.MotionEvent.ACTION_UP;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class Pantalla {
    public int numEscena;
    Context context;
    int anchoPantalla;
    int altoPantalla;

    public int getColisiones(){
        return 0;
    }

    public Pantalla(int numEscena, Context context,int anchoPantalla, int altoPantalla){
        this.numEscena=numEscena;
        this.context=context;
        this.anchoPantalla=anchoPantalla;
        this.altoPantalla= altoPantalla;
       }

       public void dibujar(Canvas c){
        c.drawColor(Color.BLACK);
       }

       public void actualizarFisica(){

       }

    public void setColisiones(int colisiones) {

    }
       public int onTouch(MotionEvent event){
               int x = (int) event.getX();
               int y = (int) event.getY();
               if (event.getAction() == ACTION_UP) {
                   return numEscena;
               }
        return numEscena;
    }
}

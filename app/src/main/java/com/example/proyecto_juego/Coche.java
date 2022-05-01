package com.example.proyecto_juego;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

public class Coche {
 //ASSETMANAGER
        public PointF posicion;

        private Random g;
        boolean colision;
        //RECTANGULO QUE RODEARÁ AL COCHE
        public Rect hitbox;
        Paint p;
        ArrayList<Bitmap> coches = new ArrayList<>();
        int choques = 0;
        int numFrame= 0;


        private int anchoCoche;
        private int altoCoche;


    public Coche(ArrayList<Bitmap> coches,float x,float y){

            this.coches = coches;
            this.posicion= new PointF(x,y);
            g=new Random();
            p=new Paint();
            p.setColor(Color.RED);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(10);


            anchoCoche = coches.get(0).getWidth();
            altoCoche=coches.get(0).getHeight();

        setRectangulo();
        }

        public void setRectangulo(){
            //CAST a int porque la posicion es float
            int x =(int) posicion.x;
            int y =(int) posicion.y;
            hitbox = new Rect( x, y, x+anchoCoche, y +altoCoche
            );
        }

        public void dibuja(Canvas canvas) {
            //imgs.get(numFrame);
            //numFrame++;
            //if(numFrame >imagenes.length){
            //  numFrame = 0;
            //}
            canvas.drawBitmap(coches.get(numFrame), posicion.x, posicion.y, null);  //SE DIBUJA EL COCHE
            numFrame++;
            if(numFrame >=coches.size()){
            numFrame = 0;
        }
            canvas.drawRect(hitbox,p); //SE dibuja el rectangulo que rodea al coche
        }



        // SI COLISIONA PONER BOOLEANA A TRUE
        public boolean Colisiona(Enemigo enemigo){
            //dibujamos un rectángulo auxiliar para que al colisionar y volver a pintar
            //el rectángulo se dibuje entero
            Rect aux = new Rect(hitbox.left,hitbox.top,hitbox.right, hitbox.bottom);
            if(aux.intersect(enemigo.hitbox) && enemigo.puedecolisionar){
                choques++;
                enemigo.puedecolisionar = false;
                if(choques == 1){
                    p.setColor(Color.YELLOW);
                }else if(choques == 3){
                    p.setColor(Color.BLUE);
                }
            }
            return aux.intersect(enemigo.hitbox); //si el rectangulo auxiliar dibujado choca con el rectangulo
            //pasado por parámetro COLISONA

        }

        public void  mover(int x, int y,int velocidad){
            this.posicion.x=x;
            this.posicion.y=y;
            setRectangulo();
    }


    }



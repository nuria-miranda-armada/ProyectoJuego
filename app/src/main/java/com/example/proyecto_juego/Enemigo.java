package com.example.proyecto_juego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import java.util.Random;

public class Enemigo {
    //AÑADIR CONOS PARA SIMULAR QUE ESTÁN FIJOS TIENEN QUE IR A LA MISMA VELOCIDAD QUE LA PANTALLA
    //Otros coches
    //Objetos fijos
    //NO PUEDE SER UNA CLASE ESTATICA
    public boolean colisiones =true;
    private int anchoCoche;
    private int altoCoche;
    public PointF posicion;
    public Bitmap imagen;
    //numero aleatorio
    private Random g;
    Rect hitbox;
    Paint p;
    int altopantalla;
    int velocidady;
    boolean puedecolisionar = true;

    public Enemigo(Bitmap imagen, float x, float y,int velocidady,int altopantalla){

        this.imagen=imagen;
        this.posicion = new PointF(x,y);
        g= new Random();
        anchoCoche=imagen.getWidth();
        p=new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(10);
        this.altopantalla=altopantalla;
        this.velocidady=velocidady;
        actualizoHitBox();
    }

    //DIBUJAMOS AL ENEMIGO EN LA PANTALLA
    //LE pasamos la imagen y la posicion
    public void dibuja(Canvas canvas){
        canvas.drawBitmap(imagen,posicion.x,posicion.y,null);
        //PASAMOS EL RECTANGULO A DIBUJAR Y EL PINCEL(PAINT)
        canvas.drawRect(hitbox,p);
    }

    public void actualizoHitBox(){
        hitbox=new Rect((int)posicion.x,(int) posicion.y,(int)(imagen.getWidth()+posicion.x), (int)(imagen.getHeight()+posicion.y));
    }

    public void  mover(int x, int y,int velocidad){
        this.posicion.x=x;
        this.posicion.y=y;
        actualizoHitBox();
    }

    //Establece l movimiento de un enemigo en una pantalla definida por alto y ancho y cierta velocidad
    public void moverEnemigo( ) {
        posicion.y += velocidady;
            if(posicion.y>altopantalla){//si la posicion en Y supera la altura de pantalla
            posicion.y=Utils.generarRandom(500,5555)*-1; //Una vez que desaparece de pantalla se calcula la posición de manera aleatoria en números negativos
                //de esta manera cada coche aparecerá en pantalla en un tiempo diferente
                //para aumentar la dificultad, au,emtaremos la velocidad de los coches, los obstaculos, etc
             colisiones=true; //boolean para detectar las colosiones
            velocidady=Utils.generarRandom(15,30);  //La velocidad en Y se genera de forma aleatoria
           //posicion.x=g.nextFloat()*(alto-imagen.getHeight());
        }
        actualizoHitBox();
    }


}

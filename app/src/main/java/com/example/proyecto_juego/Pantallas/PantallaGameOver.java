package com.example.proyecto_juego.Pantallas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.proyecto_juego.Boton;
import com.example.proyecto_juego.Etiqueta;
import com.example.proyecto_juego.R;

public class PantallaGameOver extends Pantalla {
    public PointF posicion;
    int numEscena;
    Context context;
    int anchoPantalla;
    int altoPantalla;
    Paint p;
    Bitmap imgFondo;
    Rect r;
    Etiqueta mensaje;
    Boton boton;


    public PantallaGameOver(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(numEscena, context, anchoPantalla, altoPantalla);
        this.numEscena = numEscena;
        this.context = context;
        imgFondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.carretera2);
        imgFondo = Bitmap.createScaledBitmap(imgFondo, anchoPantalla, altoPantalla, true);
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        p = new Paint();
        p.setColor(Color.YELLOW);
        p.setTextSize(altoPantalla / 4);

        setRectangulo();
        int tamx = anchoPantalla / 2;
        int tamy = altoPantalla / 15;
        mensaje = new Etiqueta("GAME OVER ",  anchoPantalla / 2 - tamx / 2, (altoPantalla / 15) * 3, tamx, tamy);
        boton = new Boton("MENU", anchoPantalla / 2 - tamx / 2, (altoPantalla /15) * 9, tamx, tamy);
    }

    public void dibujar(Canvas c) {
        c.drawColor(Color.WHITE);
        c.drawBitmap(imgFondo, 0, 0, null);
        // c.drawRect(r, p);
        mensaje.dibujar(c);
        boton.dibujar(c);
    }

    public void setRectangulo() {
        //CAST a int porque la posicion es float
        //DIBUJO DE LOS RECTANGULOS QUE REPRESENTARAN BOTONES
        posicion = new PointF(altoPantalla / 4, anchoPantalla / 4);
        int x = (int) posicion.x;
        int y = (int) posicion.y;
        r = new Rect(x/2, y/2, x + altoPantalla / 5, y + anchoPantalla / 5);
        // r2 = new Rect(10, 100, x + altoPantalla / 5, y + anchoPantalla / 5);

    }
    public void actualizarFisica() {
    }
    public int onTouch(MotionEvent event) {
    //gestiona la pulsacion del bot??n de vuelta al men??
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (boton.pulso(x, y)) {
                return 1; //SI PULSA EL BOT??N VA AL MENU
            }else{
                return numEscena;//SINO Devuelve la escena actual
            }
          
        }
        return numEscena;
    }
}


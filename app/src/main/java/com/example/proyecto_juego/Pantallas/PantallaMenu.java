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
import com.example.proyecto_juego.R;

public class PantallaMenu  extends Pantalla {
    public PointF posicion;
    int numEscena;
    Context context;
    int anchoPantalla;
    int altoPantalla;
    Paint p;
    Bitmap imgFondo;
    Rect r;
    Rect r2;
    Boton jugar, ayuda, puntuacion, configuracion;
    //GENERAR ARRY DE RECTANGULOS SIMILAR A LOS ENEMIGOS



    public PantallaMenu(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
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
        int tamx=anchoPantalla/2;
        int tamy=altoPantalla/15;
        //DIMENSIONADO DE BOTONES
        configuracion= new Boton("Configuracion",anchoPantalla/2-tamx/2,(altoPantalla/12)*1,tamx,tamy);
        jugar=new Boton("Jugar",anchoPantalla/2-tamx/2,(altoPantalla/12)*3, tamx,tamy);
        puntuacion = new Boton("Puntuacion",anchoPantalla/2-tamx/2,(altoPantalla/12)*6,tamx,tamy);
        ayuda=new Boton("Ayuda",anchoPantalla/2-tamx/2,(altoPantalla/12)*9, tamx, tamy);


    }

    public void dibujar(Canvas c) {
        c.drawColor(Color.RED);
        //c.drawText("Este es el menú", anchoPantalla /50, altoPantalla / 20, p);
        c.drawBitmap(imgFondo, 0, 0, null);
       // c.drawRect(r, p);
        configuracion.dibujar(c);
        jugar.dibujar(c);
        puntuacion.dibujar(c);
        ayuda.dibujar(c);


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

        int x = (int) event.getX();
        int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_UP) {

            if (configuracion.pulso(x, y)) {
                return 0;
            } else if (jugar.pulso(x, y)) {
                return 2;
            } else if (puntuacion.pulso(x, y)) {
                return 3;
            } else if (ayuda.pulso(x, y)) {
                return 4;
            } else {
                return numEscena;
            }
        }
        return numEscena;
    }
}

package com.example.proyecto_juego.Pantallas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.example.proyecto_juego.Etiqueta;
import com.example.proyecto_juego.R;

public class PantallaPuntuacion extends Pantalla{
    public PointF posicion;
    Bitmap imgFondo;
    int numEscena;
    Context context;
    int anchoPantalla;
    int altoPantalla;
    Paint p;
    Rect r;
    Etiqueta actualPuntuacion, RecordPuntos;


    public PantallaPuntuacion(int numEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(numEscena, context, anchoPantalla, altoPantalla);
        this.numEscena = numEscena;
        this.context= context;
        imgFondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.carretera2);
        imgFondo = Bitmap.createScaledBitmap(imgFondo, anchoPantalla, altoPantalla, true);
        this.anchoPantalla=anchoPantalla;
        this.altoPantalla=altoPantalla;
        p = new Paint();
        int tamx=anchoPantalla/2;
        int tamy=altoPantalla/15;
        actualPuntuacion = new Etiqueta("Actual \n Puntuacion",anchoPantalla/2-tamx/2,(altoPantalla/12)*1,tamx,tamy);
        actualPuntuacion = new Etiqueta("Record",anchoPantalla/2-tamx/2,(altoPantalla/12)*3,tamx,tamy);
        setRectangulo();
    }

    public void dibujar(Canvas c){
        c.drawBitmap(imgFondo, 0, 0, null);
        // c.drawRect(r, p);
        actualPuntuacion.dibujar(c);


    }
    public void setRectangulo() {
        //CAST a int porque la posicion es float
        //DIBUJO DE LOS RECTANGULOS QUE REPRESENTARAN ETIQUETAS
        posicion = new PointF(altoPantalla / 4, anchoPantalla / 4);
        int x = (int) posicion.x;
        int y = (int) posicion.y;
        r = new Rect(x/2, y/2, x + altoPantalla / 5, y + anchoPantalla / 5);
        // r2 = new Rect(10, 100, x + altoPantalla / 5, y + anchoPantalla / 5);

    }

    //CREAR BOTON QUE ME MANDE A PANTALLA MENU
}

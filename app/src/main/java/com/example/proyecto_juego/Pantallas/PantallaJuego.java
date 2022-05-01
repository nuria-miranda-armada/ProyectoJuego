package com.example.proyecto_juego.Pantallas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

import com.example.proyecto_juego.Coche;
import com.example.proyecto_juego.Enemigo;
import com.example.proyecto_juego.Fondo;
import com.example.proyecto_juego.Juego;
import com.example.proyecto_juego.R;
import com.example.proyecto_juego.Utils;

import java.util.ArrayList;

public class PantallaJuego extends Pantalla {
    private AudioManager audioManager; //AUDIO
    MediaPlayer mediaPlayer;
    private Context context;
    private Bitmap imgFondo;
    private Bitmap bitmapEnemigo;
    private Bitmap bitmapCoche, bitmapCoche2,bitmapCoche3;
    private int anchoPantalla = 1;
    private int altoPantalla = 1;
    //private Hilo hilo; Necesario crear clase HIlo
    private Juego.Hilo hilo;
    private boolean funcionando = false;
    private boolean esTitulo = true;
    private static final int MIN_DXDY = 2;
    private Fondo[] fondo;
    private boolean cambiadibujo;
    Coche coche1;
    Paint hit ;
    Paint txt;
    int vel;
    Rect rect = new Rect();
    int colisiones = 0; //CONTADOR COLISIONES
    ArrayList<Enemigo> enemigos = new ArrayList<>();

    Enemigo enemigo;
    boolean muevoCoche=false;
    Pantalla pa;

    public PantallaJuego(int numEscena,Context context, int anchoPantalla, int altoPantalla) {
        super(numEscena, context, anchoPantalla, altoPantalla);
        this.numEscena = numEscena;
        this.context = context;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        imgFondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.carretera2); //se crea la variable de la imagen
        imgFondo = Bitmap.createScaledBitmap(imgFondo, anchoPantalla, altoPantalla, true);//Se ajusta el tamaño de la imagen
        fondo = new Fondo[2]; //Se crea el array con las dos imágenes de fondo
        fondo[0] = new Fondo(imgFondo, altoPantalla);
        fondo[1] = new Fondo(imgFondo, 0, fondo[0].posicion.y - imgFondo.getHeight());
        //IMAGEN ENEMIGO
        bitmapEnemigo= BitmapFactory.decodeResource(context.getResources(), R.drawable.en1);
        //**IMAGEN ENEMIGO ESCALADA
        bitmapEnemigo = Bitmap.createScaledBitmap(bitmapEnemigo, anchoPantalla / 6, altoPantalla / 6, true);
        //IMAGEN DEL COCHE
        bitmapCoche = BitmapFactory.decodeResource(context.getResources(), R.drawable.coche);
        //**IMAGEN DEL COCHE ESCALADA
        bitmapCoche = Bitmap.createScaledBitmap(bitmapCoche, anchoPantalla / 6, altoPantalla / 6, true);
        bitmapCoche2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.coche2); //FRAME 2
        bitmapCoche2 = Bitmap.createScaledBitmap(bitmapCoche2, anchoPantalla / 6, altoPantalla / 6, true);
        bitmapCoche3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.coche3);//FRAME 3
        bitmapCoche3 = Bitmap.createScaledBitmap(bitmapCoche3, anchoPantalla / 6, altoPantalla / 6, true);
        ArrayList<Bitmap> coches = new ArrayList<>();
        coches.add(bitmapCoche);
        coches.add(bitmapCoche2);
        coches.add(bitmapCoche3);

        coche1 = new Coche(coches, 430, altoPantalla - 500);
        hit = new Paint();
        hit.setTextSize(anchoPantalla/10); //TAMAÑO DE TEXTO
        //CREACIÓN DE LOS ENBEMIGOS Y SUS VALORES ES PANTALLA
        enemigos.add(new Enemigo(bitmapEnemigo, Utils.generarRandom(50,200), Utils.generarRandom(50,100)*-1,Utils.generarRandom(6,12),altoPantalla));
        enemigos.add(new Enemigo(bitmapEnemigo,  Utils.generarRandom(450,600), Utils.generarRandom(500,5555)*-1,Utils.generarRandom(15,20),altoPantalla));
        //enemigos.add(new Enemigo(bitmapEnemigo, 600, -1000,Utils.generarRandom(6,10),altoPantalla));
        enemigos.add(new Enemigo(bitmapEnemigo,  Utils.generarRandom(700,800), Utils.generarRandom(50,10000)*-1,Utils.generarRandom(12,15),altoPantalla));

        //SONIDO
        audioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
       // mediaPlayer= MediaPlayer.create(context, R.);
    }

    public void dibujar (Canvas c){

       try{
           c.drawColor(Color.RED);
           //SE DIBUJA EL FONDO (primero la primera iomagen y después la segunda)
           c.drawBitmap(fondo[0].imagen, fondo[0].posicion.x, fondo[0].posicion.y, null);//IMAGEN 1
           c.drawBitmap(fondo[1].imagen, fondo[1].posicion.x, fondo[1].posicion.y, null);//IMAGEN 2 IGUAL A LA PRIMERA

           //Dibujamos el coche llamando al método dibuja de la clase coche
           coche1.dibuja(c);

            //SI LAS COLISIONES LLEGAN A 3, PINTAMOS EL RECTANULO AZUL
           if (colisiones == 3) {
               cambiadibujo = true;
               hit.setColor(Color.BLUE);
           }else{ //pintamos un rectangulo amarillo
               hit.setColor(Color.YELLOW);
               hit.setStyle(Paint.Style.STROKE);
               hit.setStrokeWidth(10);
           }
           c.drawText(colisiones+ "",anchoPantalla/10,(hit.getTextSize()*1.5f), hit); //Dibujamos las colisiones y el texto
           c.drawText("Nivel",anchoPantalla/10,(hit.getTextSize()*1.5f),hit); //Escribimos el nivel en pantalla

           for(Enemigo enemigo: enemigos){
               enemigo.dibuja(c); //Aquí dibujamos los enemigos
           }

       }catch (Exception e){
           e.printStackTrace();
        }
    }

    public void actualizarFisica(){
       //SE ESTABLECE LA VELOCIDAD A LA QUE SE VA A MOVER EL FONDO
        fondo[0].mover(10);
        fondo[1].mover(10);
        for(Enemigo enemigo: enemigos){
            enemigo.moverEnemigo();
            //Tienes 3 intentos sin chocar, si choca 3 veces se acaba la partida
            //SI COLISIONA EL COLOR DEL RECTANGULO PASA A AMARILLO
            //ESTO SE DIBUJA EN CAVAS PASANDOLE EL hit(pincel creado para cuando colisiona)
            //QUIZA EMPLEAR UNA BOOLEANA PARA NO METER EL CODIGO DE PPINTAR AQUI
            // A POSTERIORI PINTAR SERVIRA PARA PINTAR CORAZONES
            if(coche1.Colisiona(enemigo) && enemigo.colisiones) {
                enemigo.colisiones = false;
                //SE INCREMENTAN LAS COLISIONES
                colisiones++;
                Log.i("col", "Colisiones después de incrementar: " + colisiones);
                //SI COLISIONA MÁS DE TRES VECES PIERDE

                if (colisiones == 3) {
                    cambiadibujo = true;
                    hit.setColor(Color.BLUE);
                    funcionando=false;
                 //   numEscena = 1;//FINDE JUEGO

                }
            }
        }//
        //FONDO, vamos colocando dos imágenes del mismo fondo, una a continuación de la otra, de esta manera deplazamos la imagen
        //a una velocidad y cuando esta acaba se coloca la otra. y generamos una sensación de movimiento en pantalla
        if (fondo[0].posicion.y > altoPantalla) {
            fondo[0].posicion.y = fondo[1].posicion.y - fondo[0].imagen.getHeight();
        }
        if (fondo[1].posicion.y > altoPantalla) {
            fondo[1].posicion.y = fondo[0].posicion.y - fondo[1].imagen.getHeight();
        }

    }

    public void setColisiones(int colisiones) {
        this.colisiones = colisiones;
    }

    public int getColisiones(){
        return colisiones;
    }
    
    public int onTouch(MotionEvent event){
        int pointerIndex = event.getActionIndex();
        // int pointerID = event.getPointerId(pointerIndex);
        int accion = event.getActionMasked();
        int x=(int)event.getX();
        int y=(int)event.getY();
        //coche1.mover(x,y,10);

        switch (accion) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:

                if (coche1.hitbox.contains(x, y)){
                    muevoCoche = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:

                Log.i("ACTION", "UP->ActionIndex=" + pointerIndex + " " );
                muevoCoche=false;
                coche1.mover(x,y,15); //ESTABLECEMOS LA VELOCIDAD DEL COCHE
                //SONIDO
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case MotionEvent.ACTION_MOVE:
                coche1.mover(x,y,15); //ESTABLECEMOS LA VELOCIDAD DEL COCHE

                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }

        return numEscena;
    }


}

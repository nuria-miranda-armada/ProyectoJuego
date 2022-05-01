package com.example.proyecto_juego;

import static android.view.MotionEvent.ACTION_UP;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.proyecto_juego.Pantallas.Pantalla;
import com.example.proyecto_juego.Pantallas.PantallaGameOver;
import com.example.proyecto_juego.Pantallas.PantallaJuego;
import com.example.proyecto_juego.Pantallas.PantallaMenu;
import com.example.proyecto_juego.Pantallas.PantallaPuntuacion;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;


public class Juego extends SurfaceView implements SurfaceHolder.Callback {
    //Pantalla p = new Pantalla();
    private SurfaceHolder surfaceHolder;
    private Context context;
    private Bitmap imgFondo;
    private Bitmap bitmapEnemigo;
    private Bitmap bitmapCoche,bitmapCoche2,bitmapCoche3;
    private int anchoPantalla = 1;
    private int altoPantalla = 1;
    //private Hilo hilo; Necesario crear clase HIlo
    private Hilo hilo;
    private boolean funcionando = false;
    private boolean esTitulo = true;
    private static final int MIN_DXDY = 2;
    private Fondo[] fondo;
    private boolean cambiadibujo;
    Coche coche1;
    Paint hit;
    Paint txt;
    int vel;
    Rect rect = new Rect();
    int colisiones = 0; //CONTADOR COLISIONES
    ArrayList<Enemigo> enemigos = new ArrayList<>();

    SecureRandom sr = new SecureRandom();
    Pantalla pa;
    //DIFERENTES FRAMES
    ArrayList<Bitmap> coches = new ArrayList<>();
    int numFrame = 0;
    //NUEVO RANDOM PARA PRUEBAS
    int max = 80; //MAX RANDOM
    int min = 15; //MIN RANDOM

    //para el evento onTouchEvent
    private int x;
    private int y;

    //ITEM FANTASMA COCHE CUANDO ES ASÍ NO DETECTA COLISIONES, POR TANTO NO PUEDES PERDER VIDAS

    boolean muevoCoche = false;
    final private static HashMap<Integer, PointF> posiciones = new HashMap<>();

    public Juego(Context context) {
        super(context);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.context = context;
        hilo = new Hilo();
        setFocusable(true);
        hit = new Paint();
        hit.setTextSize(50);
        imgFondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.carretera2);

    }

       @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        hilo.setFuncionando(true);
        if (hilo.getState() == Thread.State.NEW) hilo.start();
        if (hilo.getState() == Thread.State.TERMINATED) {
            hilo = new Hilo();
            hilo.start();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int width, int height) {
        anchoPantalla = width;
        altoPantalla = height;
        pa = new PantallaMenu(1, context, anchoPantalla, altoPantalla);
        bitmapEnemigo = BitmapFactory.decodeResource(context.getResources(), R.drawable.en1);
        //AÑADIMOS FRAMES COCHE
        bitmapCoche = BitmapFactory.decodeResource(context.getResources(), R.drawable.coche); //FRAME 1
        bitmapCoche = Bitmap.createScaledBitmap(bitmapCoche, anchoPantalla / 6, altoPantalla / 6, true);
        bitmapCoche2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.coche2); //FRAME 2
        bitmapCoche2 = Bitmap.createScaledBitmap(bitmapCoche2, anchoPantalla / 6, altoPantalla / 6, true);
        bitmapCoche3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.coche3);//FRAME 3
        bitmapCoche3 = Bitmap.createScaledBitmap(bitmapCoche3, anchoPantalla / 6, altoPantalla / 6, true);
        coches.add(bitmapCoche);
        coches.add(bitmapCoche2);
        coches.add(bitmapCoche3);
        coche1 = new Coche(coches, 430, altoPantalla - 500);
        bitmapEnemigo = Bitmap.createScaledBitmap(bitmapEnemigo, anchoPantalla / 6, altoPantalla / 6, true);

        //CREACIÓN DE LOS ENBEMIGOS Y SUS VALORES ES PANTALLA
        enemigos.add(new Enemigo(bitmapEnemigo, Utils.generarRandom(50, 200), Utils.generarRandom(50, 100) * -1, Utils.generarRandom(6, 12), altoPantalla));
        enemigos.add(new Enemigo(bitmapEnemigo, Utils.generarRandom(450, 600), Utils.generarRandom(500, 5555) * -1, Utils.generarRandom(15, 20), altoPantalla));
        // enemigos.add(new Enemigo(bitmapEnemigo, 600, -1000,Utils.generarRandom(6,10),altoPantalla));
        enemigos.add(new Enemigo(bitmapEnemigo, Utils.generarRandom(700, 800), Utils.generarRandom(50, 10000) * -1, Utils.generarRandom(12, 15), altoPantalla));
        //coche1=new Enemigo(bitmapCoche,200,200);
        hilo.setSurfaceSize(width, height);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        hilo.setFuncionando(false);
        try {
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class Hilo extends Thread {
        public Hilo() {
        }

        @Override
        public void run() {
            long tiempoDormido = 0;
            final int FPS = 50;
            final int TPS = 1000000000;
            final int FRAGMENTO_TEMPORAL = TPS / FPS;
            //Tomamos un tiempo de referencia actual en nanosegundos mas preciso que currentTimeMillis()
            long tiempoReferencia = System.nanoTime();

            while (funcionando) {
                Canvas c = null;
                try {
                    if (!surfaceHolder.getSurface().isValid())
                        continue;//si la superficie no está preparada se repite
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        c = surfaceHolder.lockHardwareCanvas();
                    }
                    if (c == null) {
                        c = surfaceHolder.lockCanvas();
                    }
                    synchronized (surfaceHolder) {
                        if (pa != null) {
                            pa.actualizarFisica();
                            pa.dibujar(c);
                            //Log.i("hola",""+pa.getColisiones());
                            if (pa.getColisiones() > 3) {
                                Log.i("hola23", "" + pa.numEscena);
                                pa.setColisiones(0);
                                //si las colisiones superan 3 paso a esta escena
                                pa = new PantallaGameOver(5, context, anchoPantalla, altoPantalla); //ESCENA GAME OVER

                            }
                        }
                    }
                } finally {
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                //Calculamos el siguiente instante temporal domnde volvemos a actualizar y pintar
                tiempoReferencia += FRAGMENTO_TEMPORAL;
                //EL tiempo que duerme será el siguiente menos el actual(Ya ha terminado de pintar y actualizar)
                tiempoDormido = tiempoReferencia - System.nanoTime();
                if (tiempoDormido > 0) {
                    try {
                        Thread.sleep(tiempoDormido / 1000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        void setFuncionando(boolean flag) {
            funcionando = flag;
        }

        public void setSurfaceSize(int width, int height) {
            synchronized (surfaceHolder) {
            }
        }
    }//fin de la clase hilo

    public void cambiaEscena(int nuevaEscena) {
        Log.i("hola2", nuevaEscena + " " + pa.numEscena);
        if (nuevaEscena != pa.numEscena) {
            switch (nuevaEscena) {
                case 0:
                    //pa = new PantallaConfiguracion(0,context,anchoPantalla,altoPantalla);
                case 1:
                    pa = new PantallaMenu(1, context, anchoPantalla, altoPantalla);
                    break;
                case 2:
                    pa = new PantallaJuego(2, context, anchoPantalla, altoPantalla);
                    break;
                case 3:
                    pa = new PantallaPuntuacion(3, context, anchoPantalla, altoPantalla);
                case 4:
                   // pa = new PantallaAyuda(4,context,anchoPantalla,altoPantalla);
                case 5:
                    pa = new PantallaGameOver(5,context,anchoPantalla,altoPantalla);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {

        int nuevaEscena = pa.onTouch(event);

        synchronized (surfaceHolder) {
         //   int pointerIndex = event.getActionIndex();
           //int pointerID = event.getPointerId(pointerIndex);
            int accion = event.getActionMasked();
            int x = (int) event.getX();
            int y = (int) event.getY();

            if (accion == ACTION_UP) {
                cambiaEscena(nuevaEscena);
                return true;
            }

            return true;

        }


    }
}








package com.example.proyecto_juego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Juego pantalla = new Juego(this);
        pantalla.setKeepScreenOn(true);
        setContentView(pantalla);

    }

    protected void onResume() {

        super.onResume();
        if (Build.VERSION.SDK_INT < 16) {// versiones anteriores a JellyBean
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {// versiones iguales o superiores a Jelly Bean
            final int flags = View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION// Oculta la barra de navegación
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION// Oculta la barra de navegación
                    | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            final View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(flags);

            decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(flags);
                }
            });
        }
        getSupportActionBar().hide();


    }
}
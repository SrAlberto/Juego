package com.sralbert.juego.hilos;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sralbert.juego.clases.Enemigo;
import com.sralbert.juego.clases.EnemigoB;

public class HiloAnimacion extends AsyncTask<Object,Integer,Void> {

    @SuppressLint("StaticFieldLeak")
    private ImageView iv;
    private int ivs[];
    @SuppressLint("StaticFieldLeak")
    private RelativeLayout panelJuego;


    HiloAnimacion(RelativeLayout panelJuego) {
        this.panelJuego = panelJuego;
    }

    @Override
    protected Void doInBackground(Object... objs) {
        iv = (ImageView) objs[0];
        Enemigo enemigo = (Enemigo) objs[1];
        ivs= enemigo.getImagenes();


        try {
            int i= 0;
            if(enemigo.getClass()==EnemigoB.class) i = 1;
            for (; i < ivs.length+1; i++) {
                publishProgress(i);
                Thread.sleep(50);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        if(values[0]==ivs.length) panelJuego.removeView(iv);
        else iv.setImageResource(ivs[values[0]]);
    }
}

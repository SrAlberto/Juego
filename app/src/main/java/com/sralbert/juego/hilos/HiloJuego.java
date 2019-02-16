package com.sralbert.juego.hilos;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sralbert.juego.Enemigo;
import com.sralbert.juego.EnemigoA;
import com.sralbert.juego.EnemigoB;
import com.sralbert.juego.EnemigoC;
import com.sralbert.juego.EnemigoD;
import com.sralbert.juego.MainActivity;
import com.sralbert.juego.R;

import java.util.Random;

public class HiloJuego extends AsyncTask<Void, Void, Void> implements View.OnClickListener {

    private MainActivity activity;
    private MediaPlayer melodia;
    private static final int MAX_VIDAS = 5;
    private static final int MAX_COMBO = 10;
    private int alto, ancho, puntos, vidas, combo;


    public HiloJuego (MainActivity activity) {
        this.activity = activity;
        this.alto = activity.getPanelJuego().getHeight();
        this.ancho = activity.getPanelJuego().getWidth();
        melodia = MediaPlayer.create(activity, R.raw.background);


     }

    private void iniciarPartida () {
        vidas= 3;
        puntos= 0;
        activity.getTxtPuntos().setText("0");
        for (int i = 0; i < vidas; i++) {
            activity.getIvHeart()[i].setVisibility(View.VISIBLE);
        }
        for (int i = MAX_VIDAS-1; i >= 3; i--) {
            activity.getIvHeart()[i].setVisibility(View.INVISIBLE);
        }
        activity.getPanelJuego().removeAllViews();
        melodia.setLooping(true);
        melodia.start();
    }

    private void hayVidas() {
        ImageView iv = new ImageView(activity);
        Object ob= new EnemigoA();
        Random r= new Random();
        int bicho= r.nextInt(13);

        switch (bicho){
            case 1:
            case 5:
            case 8:
            case 11:
                ob= new EnemigoB();
                break;
            case 2:
            case 6:
            case 9:
                ob= new EnemigoC();
                break;
            case 3:
                ob= new EnemigoD();
                break;
        }

        iv.setTag(String.valueOf(ob.getClass()));
        iv.setImageResource(((Enemigo) ob).getImagenes()[0]);
        iv.setAdjustViewBounds(true);
        iv.setMaxWidth(100);
        iv.setX((new Random().nextInt(ancho-80)+1)+80);
        iv.setOnClickListener(this);
        activity.getPanelJuego().addView(iv);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        hayVidas();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        iniciarPartida();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while (vidas>0) {
            try {
                publishProgress();
                Thread.sleep(600);
            } catch (InterruptedException e) {}
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        melodia.stop();

        for (int i=0; i<activity.getPanelJuego().getChildCount();i++) {
            activity.getPanelJuego().getChildAt(i).setOnClickListener(null);
        }

        MediaPlayer gameover = MediaPlayer.create(activity, R.raw.gameover);
        gameover.start();

        ImageView ivGO = new ImageView(activity);
        ivGO.setImageResource(R.drawable.gameover);
        activity.getPanelJuego().addView(ivGO);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)ivGO.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        ivGO.setLayoutParams(layoutParams);
    }

    @Override
    public void onClick(View v) {
        String enemigoA= EnemigoA.class.toString(),
               enemigoB= EnemigoB.class.toString(),
               enemigoC= EnemigoC.class.toString(),
               enemigoD= EnemigoD.class.toString(),
               id= (String) v.getTag();
        Object enemigo= new EnemigoA();


        if (id.equals(enemigoB)){
            enemigo= new EnemigoB();
        }else if (id.equals(enemigoC)){
            enemigo= new EnemigoC();
        }else if (id.equals(enemigoD)){
            enemigo= new EnemigoD();
        }

        setNewPuntos(((Enemigo) enemigo).getPuntos());
        setNewVida(((Enemigo) enemigo).getVida());
        v.setOnClickListener(null);
        MediaPlayer moneda = MediaPlayer.create(activity, R.raw.moneda);
        moneda.start();

        HiloAnimacion ha = new HiloAnimacion(activity.getPanelJuego());
        ha.executeOnExecutor(THREAD_POOL_EXECUTOR, (ImageView) v);
    }

    private void setNewPuntos(int puntos){
        this.puntos+= puntos;
        activity.getTxtPuntos().setText(String.valueOf(this.puntos));
    }

    private void setNewVida(int vidas){
        if(vidas!=0 && this.vidas>0 && this.vidas<5) {
            this.vidas+= vidas;
            if (vidas > 0) {
                activity.getIvHeart()[this.vidas - 1].setVisibility(View.VISIBLE);
            } else {
                activity.getIvHeart()[this.vidas].setVisibility(View.INVISIBLE);
            }
        }
    }
}

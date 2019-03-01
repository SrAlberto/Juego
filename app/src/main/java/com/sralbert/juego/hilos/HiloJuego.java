package com.sralbert.juego.hilos;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sralbert.juego.MainActivity;
import com.sralbert.juego.R;
import com.sralbert.juego.clases.Enemigo;
import com.sralbert.juego.clases.EnemigoA;
import com.sralbert.juego.clases.EnemigoB;
import com.sralbert.juego.clases.EnemigoC;
import com.sralbert.juego.clases.EnemigoD;

import java.util.Random;

public class HiloJuego extends AsyncTask<Void, Void, Void> implements View.OnClickListener {

    @SuppressLint("StaticFieldLeak")
    private MainActivity activity;
    private static final int MAX_VIDAS= 4;
    private static final int VELOCIDAD_SPAWN_BICHOS= 1000;
    private int alto, ancho, puntos, vidas, duracion= 4000, velocidadSpawn= VELOCIDAD_SPAWN_BICHOS;
    private ObjectAnimator animation;
    private SharedPreferences sp;


    HiloJuego(MainActivity activity) {
        this.activity = activity;
        this.alto = activity.getPanelJuego().getHeight()-120;
        this.ancho = activity.getPanelJuego().getWidth();
        sp= activity.getSharedPreferences("datos", Context.MODE_PRIVATE);
     }

    private void iniciarPartida () {
        vidas= 2;
        puntos= 0;
        activity.getTxtPuntos().setText("0");
        for (int i = 0; i <= vidas; i++) {
            activity.getIvHeart()[i].setVisibility(View.VISIBLE);
        }
        for (int i = MAX_VIDAS; i > vidas; i--) {
            activity.getIvHeart()[i].setVisibility(View.INVISIBLE);
        }
        activity.getPanelJuego().removeAllViews();
        activity.getMelodia().start();
        activity.getMelodia().setLooping(true);
    }

    private void hayVidas() {
        ImageView iv = new ImageView(activity);
        Enemigo ob= new EnemigoA();
        Random r= new Random();
        int bicho= r.nextInt(13), posAncho= new Random().nextInt(ancho-200);

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

        iv.setImageResource(ob.getImagenes()[0]);
        animation = ObjectAnimator.ofFloat(iv, "translationY", 0, alto+120);
        animation.setDuration(duracion);
        iv.setTag(String.valueOf(ob.getClass()));
        iv.setAdjustViewBounds(true);
        iv.setMaxWidth(200);
        iv.setX(posAncho);
        iv.setOnClickListener(this);
        animation.start();
        activity.getPanelJuego().addView(iv);
    }

    private void getLimite(){
        RelativeLayout panel= activity.getPanelJuego();
        int hijos= panel.getChildCount();

        for (int i = 0; i < hijos; i++) {
            if(panel.getChildAt(i) instanceof ImageView){
                ImageView iv= (ImageView) panel.getChildAt(i);
                Enemigo e= getEnemyType((String) iv.getTag());
                if(iv.getTranslationY()>=alto){
                    panel.removeView(iv);
                    if(e instanceof EnemigoA || e instanceof EnemigoB && (vidas-1)>=-1) setNewVida(-1);
                }
            }
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        hayVidas();
        getLimite();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.setHiloJuego(this);
        iniciarPartida();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while (vidas>-1) {
            try {
                publishProgress();
                Thread.sleep(velocidadSpawn);
                animation.setDuration(duracion);
            } catch (InterruptedException ignored) {}
            if((VELOCIDAD_SPAWN_BICHOS-puntos)>0 && puntos%2==0) velocidadSpawn= VELOCIDAD_SPAWN_BICHOS-puntos;
        }
        return null;
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        activity.getMelodia().stop();

        for (int i=0; i<activity.getPanelJuego().getChildCount(); i++) {
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

        int max= sp.getInt("max", 0);

        if(puntos>max)
            sp.edit().putInt("max", puntos).apply();

        activity.getMaxTxt().setVisibility(View.VISIBLE);
        activity.getMaxPuntos().setVisibility(View.VISIBLE);
        activity.getMaxPuntos().setText(String.valueOf(max));


        HiloFinal hf= new HiloFinal(activity);
        hf.execute(ivGO);
    }

    @Override
    public void onClick(View v) {
        String id[] = ((String) v.getTag()).split(";");
        Enemigo enemigo = new EnemigoA();

            ImageView e = (ImageView) v;
            if (getEnemyType(id[0]) instanceof EnemigoB) {
                enemigo = new EnemigoB();
                if (id.length == 1) {
                    v.setTag(id[0] + ";"+enemigo.getNumToques());
                    e.setImageResource(enemigo.getImagenes()[1]);
                    return;
                }
            } else if (getEnemyType(id[0]) instanceof EnemigoC) {
                enemigo = new EnemigoC();
            } else if (getEnemyType(id[0]) instanceof EnemigoD) {
                enemigo = new EnemigoD();
            }

        setNewPuntos(enemigo.getPuntos());
        setNewVida(enemigo.getVida());
        setNewDuracionCaida(enemigo.getVelocidad());
        v.setOnClickListener(null);

        if(activity.getSonido()!=null){
            activity.getSonido().release();
            activity.setSonido(null);
        }
        activity.setSonido(MediaPlayer.create(activity, enemigo.getSonido()));
        activity.getSonido().start();
        animation.setDuration(duracion);
        HiloAnimacion ha = new HiloAnimacion(activity.getPanelJuego());
        ha.executeOnExecutor(THREAD_POOL_EXECUTOR, v, enemigo);
    }

    private void setNewPuntos(int puntos){
        this.puntos+= puntos;
        activity.getTxtPuntos().setText(String.valueOf(this.puntos));
    }

    private void setNewVida(int vidas){
        if(vidas!=0) {
            if (vidas > 0) {
                if(this.vidas<MAX_VIDAS)this.vidas+= vidas;
                activity.getIvHeart()[this.vidas].setVisibility(View.VISIBLE);
            } else {
                activity.getIvHeart()[this.vidas].setVisibility(View.INVISIBLE);
                this.vidas+= vidas;
            }
        }
    }

    private void setNewDuracionCaida(int velocidad){
        int duracionCaida= duracion- (puntos*velocidad);
        animation.setDuration(duracionCaida);
    }

    private Enemigo getEnemyType(String tag){
        String enemigoB= EnemigoB.class.toString(),
                enemigoC= EnemigoC.class.toString(),
                enemigoD= EnemigoD.class.toString();
        Enemigo e= new EnemigoA();

        if (tag.equals(enemigoB)){
            e= new EnemigoB();
        }else if (tag.equals(enemigoC)){
            e= new EnemigoC();
        }else if (tag.equals(enemigoD)){
            e= new EnemigoD();
        }
        return e;
    }
}

package com.sralbert.juego;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sralbert.juego.hilos.HiloCuentaAtras;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView btnInicio, ivHeart[];
    private RelativeLayout panelJuego;
    private TextView txtPuntos;
    private AnimationSet animacionFade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInicio = findViewById(R.id.btnInicio);
        panelJuego = findViewById(R.id.panelJuego);
        txtPuntos = findViewById(R.id.txtPuntos);
        ivHeart= new ImageView[]{findViewById(R.id.ivHeart0), findViewById(R.id.ivHeart1),
                findViewById(R.id.ivHeart2), findViewById(R.id.ivHeart3), findViewById(R.id.ivHeart4)};
        btnInicio.setOnClickListener(this);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1000);
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);
        animacionFade= new AnimationSet(false);
        animacionFade.addAnimation(fadeIn);
        animacionFade.addAnimation(fadeOut);
        for (ImageView heart:ivHeart){
            heart.setAnimation(animacionFade);
        }
    }

    @Override
    public void onClick(View v) {
        HiloCuentaAtras hca = new HiloCuentaAtras(this);
        hca.execute();
    }

    public ImageView getBtnInicio() {
        return btnInicio;
    }
    public TextView getTxtPuntos() {
        return txtPuntos;
    }
    public RelativeLayout getPanelJuego() {
        return panelJuego;
    }
    public ImageView[] getIvHeart() {
        return ivHeart;
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if(melodia.isPlaying()) melodia.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(!melodia.isPlaying()) melodia.start();
    }
}

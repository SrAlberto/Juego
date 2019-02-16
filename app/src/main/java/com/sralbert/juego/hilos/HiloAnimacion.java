package com.sralbert.juego.hilos;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sralbert.juego.R;

public class HiloAnimacion extends AsyncTask<ImageView,Integer,Void> {

    private ImageView iv;
    private RelativeLayout panelJuego;


    public HiloAnimacion(RelativeLayout panelJuego) {
        this.panelJuego = panelJuego;
    }

    @Override
    protected Void doInBackground(ImageView... ivs) {
        iv = ivs[0];
        try {
            for (int i = 0; i < 4; i++) {
                publishProgress(i);
                Thread.sleep(50);
            }
        }
        catch (Exception e) {}
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        switch (values[0]) {
            case 0: {
                iv.setImageResource(R.drawable.bicho1);
                break;
            }
            case 1: {
                iv.setImageResource(R.drawable.bicho2);
                break;
            }
            case 2: {
                iv.setImageResource(R.drawable.bicho3);
                break;
            }
            case 3:
                panelJuego.removeView(iv);

        }
    }
}

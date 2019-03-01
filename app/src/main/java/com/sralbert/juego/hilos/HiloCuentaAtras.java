package com.sralbert.juego.hilos;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import com.sralbert.juego.MainActivity;
import com.sralbert.juego.R;

public class HiloCuentaAtras extends AsyncTask<Void,Integer,Void> {

    @SuppressLint("StaticFieldLeak")
    private MainActivity activity;

    public HiloCuentaAtras(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        MediaPlayer mp = MediaPlayer.create(activity, R.raw.countdown);
        mp.start();
        try {
            publishProgress(3);
            Thread.sleep(500);
            publishProgress(2);
            Thread.sleep(500);
            publishProgress(1);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mp.stop();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        HiloJuego hj = new HiloJuego(activity);
        hj.execute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        switch (values[0]) {
            case 3: activity.getBtnInicio().setImageResource(R.drawable.num3); break;
            case 2: activity.getBtnInicio().setImageResource(R.drawable.num2); break;
            case 1: activity.getBtnInicio().setImageResource(R.drawable.num1); break;
        }
    }
}

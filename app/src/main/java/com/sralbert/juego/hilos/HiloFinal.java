package com.sralbert.juego.hilos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.sralbert.juego.MainActivity;

public class HiloFinal extends AsyncTask<ImageView, ImageView, Void> {

    @SuppressLint("StaticFieldLeak")
    private MainActivity activity;
    private SharedPreferences sp;

    HiloFinal(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(ImageView... ivs) {
        publishProgress(ivs[0]);
        return null;
    }

    @Override
    protected void onProgressUpdate(ImageView... values) {
        super.onProgressUpdate(values);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        values[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, activity.getClass());
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }
}

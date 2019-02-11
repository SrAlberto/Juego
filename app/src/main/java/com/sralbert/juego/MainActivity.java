package com.sralbert.juego;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends AppCompatActivity{

    private MediaPlayer music= new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        music= MediaPlayer.create(this, R.raw.background);
        music.setLooping(true);
        music.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(music.isPlaying()) music.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!music.isPlaying()) music.start();
    }
}

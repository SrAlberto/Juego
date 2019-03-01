package com.sralbert.juego.clases;

import com.sralbert.juego.R;

public class EnemigoD extends Enemigo{

    public EnemigoD() {
        super(1, 1, 4, 5,
                new int[]{R.drawable.heart0, R.drawable.heart1, R.drawable.heart2, R.drawable.heart3}, R.raw.levelup);
    }
}

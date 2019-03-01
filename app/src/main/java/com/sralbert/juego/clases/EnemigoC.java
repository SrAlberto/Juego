package com.sralbert.juego.clases;

import com.sralbert.juego.R;

public class EnemigoC extends Enemigo{

    public EnemigoC() {
        super(1, -1, 3, 0,
                new int[]{R.drawable.bomba0, R.drawable.bomba1, R.drawable.bomba2, R.drawable.bomba3}, R.raw.boom) ;
    }
}

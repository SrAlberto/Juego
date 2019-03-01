package com.sralbert.juego.clases;

import com.sralbert.juego.R;

public class Enemigo {
    private int numToques;
    private int vida;
    private int velocidad;
    private int puntos;
    private int[] imagenes;
    private int sonido;

    Enemigo(int numToques, int velocidad, int puntos, int[] imagenes) {
        this.numToques = numToques;
        this.velocidad = velocidad;
        this.puntos = puntos;
        this.imagenes = imagenes;
        this.sonido = R.raw.moneda;
    }

    public Enemigo(int numToques, int vida, int velocidad, int puntos, int[] imagenes) {
        this.numToques = numToques;
        this.vida = vida;
        this.velocidad = velocidad;
        this.puntos = puntos;
        this.imagenes = imagenes;
        this.sonido = R.raw.moneda;
    }

    Enemigo(int numToques, int vida, int velocidad, int puntos, int[] imagenes, int sonido) {
        this.numToques = numToques;
        this.vida = vida;
        this.velocidad = velocidad;
        this.puntos = puntos;
        this.imagenes = imagenes;
        this.sonido = sonido;
    }

    public int getNumToques() {
        return numToques;
    }

    public int getVida() {
        return vida;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getPuntos() {
        return puntos;
    }

    public int[] getImagenes() {
        return imagenes;
    }

    public int getSonido() {
        return sonido;
    }
}

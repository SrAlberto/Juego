package com.sralbert.juego;

public class Enemigo {
    private int numToques;
    private int vida;
    private int velocidad;
    private int puntos;
    private int[] imagenes;

    public Enemigo(int numToques, int vida, int velocidad, int puntos, int[] imagenes) {
        this.numToques = numToques;
        this.vida = vida;
        this.velocidad = velocidad;
        this.puntos = puntos;
        this.imagenes = imagenes;
    }

    public Enemigo() {}

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
}

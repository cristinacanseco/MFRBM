package com.example.llorar.Muestreo;

import java.util.ArrayList;

public class Muestreo {

    public String nombre_muestreo;
    public String color_muestreo;
    public String gps_muestreo;
    public int imagen_muestreo;
    public ArrayList<Dimension> dimensiones_muestreo;

    public Muestreo(String nombre_muestreo, String color_muestreo, String gps_muestreo, int imagen_muestreo) {
        this.nombre_muestreo = nombre_muestreo;
        this.color_muestreo = color_muestreo;
        this.gps_muestreo = gps_muestreo;
        this.imagen_muestreo = imagen_muestreo;
    }

    public Muestreo(String nombre_muestreo, String color_muestreo, String gps_muestreo, int imagen_muestreo, ArrayList<Dimension> dimensiones_muestreo) {
        this.nombre_muestreo = nombre_muestreo;
        this.color_muestreo = color_muestreo;
        this.gps_muestreo = gps_muestreo;
        this.imagen_muestreo = imagen_muestreo;
        this.dimensiones_muestreo = dimensiones_muestreo;
    }

    public String getNombre_muestreo() {
        return nombre_muestreo;
    }

    public String getColor_muestreo() {
        return color_muestreo;
    }

    public String getGps_muestreo() {
        return gps_muestreo;
    }

    public int getImagen_muestreo() {
        return imagen_muestreo;
    }

    public ArrayList<Dimension> getDimensiones_muestreo() {
        return dimensiones_muestreo;
    }
}

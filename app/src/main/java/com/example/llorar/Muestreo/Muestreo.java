package com.example.llorar.Muestreo;

import java.io.Serializable;

public class Muestreo implements Serializable {

    public String nombre_mtr;
    public String imagen_mtr;
    public String forma_mtr;
    public String textura_mtr;
    public String color_mtr;
    public String dimension_mtr;
    public String lugar_mtr;
    public String fecha_mtr;
    public String hora_mtr;
    public String id_mtr;

    public Muestreo() { }

    public Muestreo(String nombre_mtr, String imagen_mtr, String forma_mtr, String textura_mtr, String color_mtr, String dimension_mtr, String lugar_mtr, String fecha_mtr, String hora_mtr) {
        this.nombre_mtr = nombre_mtr;
        this.imagen_mtr = imagen_mtr;
        this.forma_mtr = forma_mtr;
        this.textura_mtr = textura_mtr;
        this.color_mtr = color_mtr;
        this.dimension_mtr = dimension_mtr;
        this.lugar_mtr = lugar_mtr;
        this.fecha_mtr = fecha_mtr;
        this.hora_mtr = hora_mtr;
    }

    public Muestreo(String nombre_mtr, String imagen_mtr, String forma_mtr, String textura_mtr,String color_mtr, String dimension_mtr, String lugar_mtr, String fecha_mtr, String hora_mtr, String id_mtr) {
        this.nombre_mtr = nombre_mtr;
        this.imagen_mtr = imagen_mtr;
        this.forma_mtr = forma_mtr;
        this.textura_mtr = textura_mtr;
        this.color_mtr = color_mtr;
        this.dimension_mtr = dimension_mtr;
        this.lugar_mtr = lugar_mtr;
        this.fecha_mtr = fecha_mtr;
        this.hora_mtr = hora_mtr;
        this.id_mtr = id_mtr;
    }

    public String getNombre_mtr() { return nombre_mtr; }

    public String getImagen_mtr() { return imagen_mtr; }

    public String getForma_mtr() { return forma_mtr; }

    public String getTextura_mtr() { return textura_mtr; }

    public String getColor_mtr() { return color_mtr; }

    public String getDimension_mtr() { return dimension_mtr; }

    public String getLugar_mtr() { return lugar_mtr; }

    public String getFecha_mtr() { return fecha_mtr; }

    public String getHora_mtr() { return hora_mtr; }

    public String getId_mtr() { return id_mtr; }
}

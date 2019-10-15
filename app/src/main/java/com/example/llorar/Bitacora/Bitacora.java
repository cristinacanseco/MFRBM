package com.example.llorar.Bitacora;

import java.io.Serializable;

public class Bitacora implements Serializable {

    public String nombre_btc;
    public String ubicacion_btc;
    public String cantidad_btc;
    public String fecha_btc;
    public String hora_btc;
    public String imagen_btc;
    public String id_btc;

    public Bitacora(){}

    public Bitacora(String nombre_btc, String ubicacion_btc, String cantidad_btc, String fecha_btc, String hora_btc, String imagen_btc) {
        this.nombre_btc = nombre_btc;
        this.ubicacion_btc = ubicacion_btc;
        this.cantidad_btc = cantidad_btc;
        this.fecha_btc = fecha_btc;
        this.hora_btc = hora_btc;
        this.imagen_btc = imagen_btc;
    }

    public Bitacora(String nombre_btc, String ubicacion_btc, String cantidad_btc, String fecha_btc, String hora_btc, String imagen_btc, String id_btc) {
        this.nombre_btc = nombre_btc;
        this.ubicacion_btc = ubicacion_btc;
        this.cantidad_btc = cantidad_btc;
        this.fecha_btc = fecha_btc;
        this.hora_btc = hora_btc;
        this.imagen_btc = imagen_btc;
        this.id_btc = id_btc;
    }

    public String getNombre_btc() {
        return nombre_btc;
    }

    public String getUbicacion_btc() {
        return ubicacion_btc;
    }

    public String getCantidad_btc() { return cantidad_btc; }

    public String getFecha_btc() {
        return fecha_btc;
    }

    public String getImagen_btc() { return imagen_btc; }

    public String getHora_btc() { return hora_btc; }

    public String getId_btc() { return id_btc; }
}

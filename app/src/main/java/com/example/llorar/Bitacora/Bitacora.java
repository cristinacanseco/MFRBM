package com.example.llorar.Bitacora;

public class Bitacora {

    public String nombre_bitacora;
    public String lugar_bitacora;
    public int cantidad_muestreos;
    public String fecha_bitacora;
    public int imagen;

    public Bitacora(String nombre_bitacora, String lugar_bitacora, int cantidad_muestreos, String fecha_bitacora, int imagen) {
        this.nombre_bitacora = nombre_bitacora;
        this.lugar_bitacora = lugar_bitacora;
        this.cantidad_muestreos = cantidad_muestreos;
        this.fecha_bitacora = fecha_bitacora;
        this.imagen = imagen;
    }

    public String getNombre_bitacora() {
        return nombre_bitacora;
    }

    public String getLugar_bitacora() {
        return lugar_bitacora;
    }

    public int getCantidad_muestreos() {
        return cantidad_muestreos;
    }

    public String getFecha_bitacora() {
        return fecha_bitacora;
    }

    public int getImagen() { return imagen; }
}

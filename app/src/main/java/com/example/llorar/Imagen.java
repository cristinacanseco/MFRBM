package com.example.llorar;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

public class Imagen {

    public int altura;
    public int ancho;
    public String descripcion;
    public ImageView imagen;
    public Drawable imagenD;
    public Bitmap imagenB;
    ImageView iv = null;

    public Imagen(Bitmap imagenB) {
        this.imagenB = imagenB;
        this.imagenD = bitmapToDrawable(this.imagenB);
        //bitmapToImage(this.imagenB);
        this.descripcion = "Image";
        this.altura = imagenB.getHeight();
        this.ancho = imagenB.getWidth();
    }

    public Imagen(Drawable imagenD) {
        this.imagenD = imagenD;
        this.imagenB = drawableToBitmap(this.imagenD);
        //bitmapToImage(this.imagenB);
        this.descripcion = "Image";
        this.altura = imagenB.getHeight();
        this.ancho = imagenB.getWidth();
    }


    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }

    public void guardarImagen(Image imagen){}

    public Drawable getImagenD() {
        return imagenD;
    }

    public Bitmap getImagenB() {
        return imagenB;
    }

    public void setImagenB(Bitmap imagenB) {
        this.imagenB = imagenB;
    }

    public void setImagenD(Drawable imagenD) {
        this.imagenD = imagenD;
    }

    public Drawable bitmapToDrawable(Bitmap i){
        Drawable d = new BitmapDrawable(i);
        return d;
    }

    public Bitmap drawableToBitmap(Drawable i){
        Bitmap b = ((BitmapDrawable) i).getBitmap();
        return b;
    }

    private void bitmapToImage(Bitmap imagenB) {
        iv.setImageBitmap(imagenB);
        this.imagen = iv;
    }

    public int getAltura() {
        return altura;
    }

    public int getAncho() {
        return ancho;
    }


    public int getRGB(int x, int y){
        int pixel = this.imagenB.getPixel(x,y);
      return pixel;
    }

    public int getRed(int pixel) {
        return Color.red(pixel);
    }

    public int getBlue(int pixel) {
        return Color.blue(pixel);
    }
    public int getGreen(int pixel) {
        return Color.green(pixel);
    }

    public int getRGB2(int x, int y){
        int pixel = this.imagenB.getPixel(x,y);
        int r = Color.red(pixel);
        int g = Color.red(pixel);
        int b = Color.red(pixel);
        return pixel;
    }

}

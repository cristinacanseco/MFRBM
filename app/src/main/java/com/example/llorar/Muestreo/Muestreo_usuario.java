package com.example.llorar.Muestreo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.llorar.Segmentacion.Imagen;
import com.example.llorar.R;
import com.example.llorar.Segmentacion.BrushStroke;

import java.util.ArrayList;
import java.util.LinkedList;

public class Muestreo_usuario extends AppCompatActivity {

    public Imagen imagenProcesada;
    public ArrayList tonosColor = new ArrayList();
    public TextView texto;
    public ImageView imgMuestra;
    public String text = "";
    public LinkedList<int[]> mPathPoints = new LinkedList<>();
    public ArrayList<BrushStroke> mBrushStrokes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestreo_usuario);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras != null) {

                ArrayList<BrushStroke> datos = (ArrayList<BrushStroke>) extras.get("stack");
                if (datos != null) {
                    mBrushStrokes = datos;
                }

                //LinkedList<int[]> datos = (LinkedList<int[]>) extras.get("stack");
                //if (datos != null) {
                //    mPathPoints = datos;
                //}

                Bitmap image = (Bitmap) extras.get("image");
                if (image != null) {
                    imagenProcesada = new Imagen(image);
                }

            }
        }
        texto = findViewById(R.id.tv_color_muestreo_personalizado);
        imgMuestra = findViewById(R.id.img_muestreo_personalizado);
        imgMuestra.setImageBitmap(imagenProcesada.getImagenB());
        obtenerRGBSegementado();
    }

    public enum CanalColor {ROJO(0),VERDE(1),AZUL(2);

        private int index;

        private CanalColor(int index){
            this.index = index;
        }

        public int getColorIndex(){

            return index;
        }
    }


    private void obtenerRGBSegementado() {
        //double frecuencias[] = new double[256];

        text += "H: "+imagenProcesada.getAltura() + " W: "+ imagenProcesada.getAncho()+ "\n";
        /*for(int i=0; i<imagenProcesada.getAncho(); i++){
            for(int j=0; j<imagenProcesada.getAltura(); j++){
                     if((i== mPathPoints.get(x)[0]) && (j==mPathPoints.get(x)[1]) ){
                        text += "("+mPathPoints.get(x)[0]+" , ";
                        text += ""+mPathPoints.get(x)[1]+")\t";
                        text += "" + imagenProcesada.getRGB((int)mBrushStrokes.get(x).getmPathPoints().get(y)[0],(int)mBrushStrokes.get(x).getmPathPoints().get(y)[1])+ ")\n";
                        int pix = imagenProcesada.getRGB(i,j);
                        text += "("+imagenProcesada.getRed(pix)+"," +imagenProcesada.getGreen(pix)+"," +imagenProcesada.getBlue(pix)+")";
                    }

            }
        }*/
        text += mBrushStrokes.size();
        texto.setText(""+text);
    }





}
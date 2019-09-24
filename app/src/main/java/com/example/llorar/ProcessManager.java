package com.example.llorar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class ProcessManager extends AppCompatActivity {

    private Imagen imagenProcesada;
    private ArrayList<BrushStroke> mBrushStrokes = new ArrayList<>();
    private ArrayList tonosColor = new ArrayList();
    private TextView texto;
    private ImageView imgMuestra;
    private String text = "";
    private LinkedList<int[]> mPathPoints = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_manager);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras != null) {

                //ArrayList<BrushStroke> datos = (ArrayList<BrushStroke>) extras.get("stack");
                //if (datos != null) {
                //    mBrushStrokes = datos;
                //}

                LinkedList<int[]> datos = (LinkedList<int[]>) extras.get("stack");
                if (datos != null) {
                    mPathPoints = datos;
                }

                Bitmap image = (Bitmap) extras.get("image");
                if (image != null) {
                    imagenProcesada = new Imagen(image);
                }

            }
        }
        texto = findViewById(R.id.textTonos);
        imgMuestra = findViewById(R.id.imgMuestra);
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
        for(int i=0; i<imagenProcesada.getAncho(); i++){
            for(int j=0; j<imagenProcesada.getAltura(); j++){
                //for(int x=0; x<mPathPoints.size(); x++) {

                //ordenamiento de índices!!!

                  //     if((i== mPathPoints.get(x)[0]) && (j==mPathPoints.get(x)[1]) ){
                                //text += "("+mPathPoints.get(x)[0]+" , ";
                                //text += ""+mPathPoints.get(x)[1]+")\t";
                                //text += "" + imagenProcesada.getRGB((int)mBrushStrokes.get(x).getmPathPoints().get(y)[0],(int)mBrushStrokes.get(x).getmPathPoints().get(y)[1])+ ")\n";
                                //int pix = imagenProcesada.getRGB(i,j);
                                //text += "("+imagenProcesada.getRed(pix)+"," +imagenProcesada.getGreen(pix)+"," +imagenProcesada.getBlue(pix)+")";
                    //    }

                //}
            }
        }
        texto.setText(""+text);
    }





}

package com.example.llorar.Segmentacion;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.llorar.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Camara extends AppCompatActivity implements View.OnClickListener {

    public String rutaAbsoluta="";
    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    public Button botonCargar;
    public ImageView imagen;

    public File imagenFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        imagen= (ImageView) findViewById(R.id.imagemId);
        botonCargar= (Button) findViewById(R.id.btnCargarImg);


        if(validaPermisos()){
            botonCargar.setEnabled(true);
            botonCargar.setOnClickListener(this);
        }else{
            botonCargar.setEnabled(false);
        }

    }

    private boolean validaPermisos() {

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                botonCargar.setEnabled(true);
            }else{
                solicitarPermisosManual();
            }
        }
    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"Sí","No"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(Camara.this);
        alertOpciones.setTitle("¿Deseas configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Sí")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(Camara.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debes aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }


    private void cargarImagen() throws IOException {

        /*final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(Camara.this);
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    try {
                        tomarFotografia();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    if (opciones[i].equals("Cargar Imagen")){
                        Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),COD_SELECCIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();*/
        tomarFotografia();

    }

    public File crearDirectorioPublico(String nombreDirectorio) {
        //Crear directorio público en la carpeta Pictures.
        File directorio = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), nombreDirectorio);

        if (!directorio.mkdirs())
            Log.e("" ,"Error: No se creo el directorio público");

        return directorio;
    }

    private File crearFotoFile() throws IOException{

        File fileImagen = crearDirectorioPublico("MFRBM");
        //File fileImagen=getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        String timeStamp="";
        String imagenFileName="";

        boolean isCreada=fileImagen.exists();
        if(isCreada==false){
            isCreada=fileImagen.mkdirs();
        }

        if(isCreada==true){
            //nombreImagen=(System.currentTimeMillis()/1000)+".jpg";
            timeStamp=new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());
            imagenFileName ="imagen_muestreo_"+timeStamp;
        }

        File fotoFile=File.createTempFile(imagenFileName,".jpg",fileImagen);

        rutaAbsoluta=fotoFile.getAbsolutePath();

        return fotoFile;
    }


    private void tomarFotografia() throws IOException {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        imagenFile= crearFotoFile();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagenFile));
        startActivityForResult(intent,COD_FOTO);
        Toast.makeText(this, ""+rutaAbsoluta, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){

            switch (requestCode){
                case COD_SELECCIONA:
                    Uri miPath=data.getData();
                    imagen.setImageURI(miPath);
                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{rutaAbsoluta}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento","Path: "+path);
                                }
                            });

                    Intent intent = new Intent(Camara.this, PaintView.class);
                    intent.putExtra("rutaAbsoluta", rutaAbsoluta);
                    startActivity(intent);
                    //Bitmap bitmap= BitmapFactory.decodeFile(rutaAbsoluta);
                    //imagen.setImageBitmap(bitmap);

                    break;
            }


        }
    }

    @Override
    public void onClick(View view) {
        try {
            tomarFotografia();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package com.example.llorar.Bitacora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.llorar.Motor_DB.Crud;
import com.example.llorar.R;

public class NewBitacora extends AppCompatActivity implements View.OnClickListener {

    private EditText et_nombre_btc_nb, et_descripcion_btc_nb;
    private Crud crud;
    private Button btn_guardar_nb;
    private ImageView iv_regresar_nb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bitacora);

        crud = new Crud(NewBitacora.this);
        et_nombre_btc_nb = findViewById(R.id.et_nombre_btc_nb);
        et_descripcion_btc_nb = findViewById(R.id.et_descripcion_btc_nb);
        btn_guardar_nb = findViewById(R.id.btn_guardar_nb);
        btn_guardar_nb.setOnClickListener(this);
        iv_regresar_nb = findViewById(R.id.iv_regresar_nb);
        iv_regresar_nb.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_guardar_nb:
                crud.insertarDatoBitacora(generarDatosBitacora());
                break;
            case R.id.iv_regresar_nb:
                startActivity(new Intent(NewBitacora.this, Mis_bitacoras.class));
            default:
                break;
        }
    }


    private Bitacora generarDatosBitacora() {
        String nombre_btc = et_nombre_btc_nb.getText().toString();
        String ubicacion_btc = "Guadalupe";
        String cantidad_btc = "55";
        String fecha_btc = "2345";
        String hora_btc = "345";
        String imagen_btc = "null";
        String descripcion_btc = et_descripcion_btc_nb.getText().toString();

        return new Bitacora(nombre_btc, ubicacion_btc, cantidad_btc, fecha_btc, hora_btc, imagen_btc, descripcion_btc);
    }
}

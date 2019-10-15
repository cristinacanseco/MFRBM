package com.example.llorar.Bitacora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.llorar.Motor_DB.Crud;
import com.example.llorar.Muestreo.Mis_muestreos;
import com.example.llorar.R;

public class Mis_bitacoras extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv_mis_bitacoras;
    private ImageButton btn_agregar_muestreo;
    private ImageView img_opciones;
    public Crud crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_bitacoras);

        rv_mis_bitacoras = (RecyclerView) findViewById(R.id.rv_mis_bitacoras);
        rv_mis_bitacoras.setLayoutManager(new LinearLayoutManager(this));
        crud = new Crud(Mis_bitacoras.this);
        crud.mostrarTodasBitacoras(rv_mis_bitacoras);


        btn_agregar_muestreo = (ImageButton) findViewById(R.id.btn_agregar_muestreo);
        btn_agregar_muestreo.setOnClickListener(this);
        img_opciones = (ImageView) findViewById(R.id.img_Opciones);

        img_opciones.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_agregar_muestreo:
                Intent intent = new Intent(Mis_bitacoras.this, Mis_muestreos.class);
                startActivity(intent);
                break;
            case  R.id.img_Opciones:
                crud.cerrarSesion();
                finish();
                break;
            default:
                break;
        }
    }


}

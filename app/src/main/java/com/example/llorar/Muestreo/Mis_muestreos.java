package com.example.llorar.Muestreo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.llorar.Motor_DB.Crud;
import com.example.llorar.R;
import com.example.llorar.Segmentacion.Camara;

public class Mis_muestreos extends AppCompatActivity implements View.OnClickListener {

    public Crud crud;
    private RecyclerView rv_mis_muestreos;
    private ImageButton btn_nuevo_muestreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_muestreos);

        rv_mis_muestreos = (RecyclerView) findViewById(R.id.rv_mis_muestreos);
        rv_mis_muestreos.setLayoutManager(new LinearLayoutManager(this));
        crud = new Crud(Mis_muestreos.this);
        //crud.mostrarTodosMuestreos(rv_mis_muestreos, "RpDIrFWv82IVprN0RSYq");

        btn_nuevo_muestreo = (ImageButton) findViewById(R.id.btn_nuevo_muestreo);
        btn_nuevo_muestreo.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_nuevo_muestreo:
                Intent intent = new Intent(Mis_muestreos.this, Camara.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}

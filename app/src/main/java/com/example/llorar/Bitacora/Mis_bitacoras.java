package com.example.llorar.Bitacora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.llorar.Muestreo.Mis_muestreos;
import com.example.llorar.R;

import java.util.ArrayList;

public class Mis_bitacoras extends AppCompatActivity implements View.OnClickListener {

    private ListView lv_mis_bitacoras;
    private Adaptador_mi_bitacora adaptadorMibitacora;
    private Button btn_cerrar_sesion_bitacora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_bitacoras);

        lv_mis_bitacoras = (ListView) findViewById(R.id.lv_mis_bitacoras);
        adaptadorMibitacora = new Adaptador_mi_bitacora(this, getArrayItems());
        lv_mis_bitacoras.setAdapter(adaptadorMibitacora);
        btn_cerrar_sesion_bitacora = (Button) findViewById(R.id.btn_cerrar_sesion_mis_bitacoras);
        btn_cerrar_sesion_bitacora.setOnClickListener(this);
    }

    private ArrayList<Bitacora> getArrayItems(){
        ArrayList<Bitacora> lista = new ArrayList<>();
        lista.add(new Bitacora("Bitácora 1", "Zacatecas, Zacatecas", 5, "13/05/2019 12:34", R.drawable.cactus));
        lista.add(new Bitacora("Bitácora 2", "Guadalupe, Zacatecas", 2, "05/01/2019 11:43", R.drawable.cactus));
        return lista;
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Mis_bitacoras.this, Mis_muestreos.class);
        startActivity(intent);
    }
}

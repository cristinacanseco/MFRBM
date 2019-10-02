package com.example.llorar.Muestreo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.llorar.Bitacora.Mis_bitacoras;
import com.example.llorar.MainActivity;
import com.example.llorar.R;

import java.util.ArrayList;

public class Mis_muestreos extends AppCompatActivity implements View.OnClickListener {

    private ListView lv_mis_muestreos;
    private Adaptador_muestreo adaptador_muestreo;
    private Button btn_cerrar_sesion_mis_muestreos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_muestreos);
        lv_mis_muestreos = (ListView) findViewById(R.id.lv_mis_muestreos);
        adaptador_muestreo = new Adaptador_muestreo(this, getArrayItems());
        lv_mis_muestreos.setAdapter(adaptador_muestreo);

        btn_cerrar_sesion_mis_muestreos = (Button) findViewById(R.id.btn_cerrar_sesion_mis_muestreos);
        btn_cerrar_sesion_mis_muestreos.setOnClickListener(this);
    }

    private ArrayList<Muestreo> getArrayItems(){
        ArrayList<Muestreo> lista = new ArrayList<>();
        lista.add(new Muestreo("Muestreo 1", "(12,24,45", "123.34,134.35", R.drawable.cactus));
        lista.add(new Muestreo("Muestreo 2", "73,135,32", "245.4,235.66",R.drawable.flor));
        return lista;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Mis_muestreos.this, MainActivity.class);
        startActivity(intent);
    }
}

package com.example.llorar.Muestreo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.llorar.Motor_DB.Crud;
import com.example.llorar.R;
import com.example.llorar.Segmentacion.Camara;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Map;

public class Mis_muestreos extends AppCompatActivity implements View.OnClickListener, MuestreoAdapter.OnItemClickListener {

    public Crud crud;
    private RecyclerView rv_mis_muestreos;
    public FloatingActionButton fab_agregar_muestreo;
    public String id_bitacora;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_muestreos);

        crud = new Crud(Mis_muestreos.this);
        id_bitacora = getIntent().getStringExtra("id_bitacora");

        fab_agregar_muestreo = findViewById(R.id.fab_agregar_muestreo);
        fab_agregar_muestreo.setOnClickListener(this);

        rv_mis_muestreos = findViewById(R.id.rv_mis_muestreos);
        crud.mostrarMuestreo(rv_mis_muestreos, id_bitacora);
        rv_mis_muestreos.setHasFixedSize(true);
        rv_mis_muestreos.setLayoutManager(new GridLayoutManager(this, 2));


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                crud.borrarMuestreo(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rv_mis_muestreos);

        crud.getAdapterMuestreo().setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_agregar_muestreo:
                Intent intent = new Intent(Mis_muestreos.this, Camara.class);
                startActivity(intent);
                finishAfterTransition();
                break;
            default:
                break;
        }

    }

    @Override
    public void onItemClick(DocumentSnapshot documentSnapshot, int posicion) {
        String id = documentSnapshot.getId();
        Map<String, Object> hashMap = documentSnapshot.getData();
        Intent intent = new Intent(Mis_muestreos.this, Muestreo_personalizado.class);
        intent.putExtra("id_bitacora", id);
        intent.putExtra("map", (Serializable)hashMap);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(DocumentSnapshot documentReference, int posicion) {
        String id = documentReference.getId();
        Map<String, Object> hashMap = documentReference.getData();
        Intent intent = new Intent(Mis_muestreos.this, Muestreo_personalizado.class);
        intent.putExtra("id_bitacora", id);
        intent.putExtra("map", (Serializable)hashMap);
        startActivity(intent);
    }
}

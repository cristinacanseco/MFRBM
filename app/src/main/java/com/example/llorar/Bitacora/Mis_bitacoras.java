package com.example.llorar.Bitacora;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Map;

public class Mis_bitacoras extends AppCompatActivity implements View.OnClickListener, BitacoraAdapter.OnItemClickListener {

    private Crud crud;
    public RecyclerView rv_mis_bitacoras_p;
    public FloatingActionButton fab_agregar_bitacora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_bitacoras);

        crud = new Crud(Mis_bitacoras.this);

        fab_agregar_bitacora = findViewById(R.id.fab_agregar_bitacora);
        fab_agregar_bitacora.setOnClickListener(this);

        rv_mis_bitacoras_p = findViewById(R.id.rv_mis_bitacoras_p);
        crud.mostrarBitacoras(rv_mis_bitacoras_p);
        rv_mis_bitacoras_p.setHasFixedSize(true);
        rv_mis_bitacoras_p.setLayoutManager(new GridLayoutManager(this, 2));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    crud.borrarBitacora(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rv_mis_bitacoras_p);

        crud.getAdapterPrueba().setOnItemClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        crud.getAdapterPrueba().startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        crud.getAdapterPrueba().stopListening();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_agregar_bitacora:
                startActivity(new Intent(Mis_bitacoras.this, NewBitacora.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(DocumentSnapshot documentSnapshot, int posicion) {
        String id = documentSnapshot.getId();
        Map<String, Object> hashMap = documentSnapshot.getData();
        Intent intent = new Intent(Mis_bitacoras.this, Bitacora_personalizada.class);
        intent.putExtra("id_bitacora", id);
        intent.putExtra("map", (Serializable)hashMap);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(DocumentSnapshot documentReference, int posicion) {
        String id = documentReference.getId();
        Map<String, Object> hashMap = documentReference.getData();
        Intent intent = new Intent(Mis_bitacoras.this, BitacoraEditar.class);
        intent.putExtra("id_bitacora", id);
        intent.putExtra("map", (Serializable)hashMap);
        startActivity(intent);
    }
}

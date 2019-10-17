package com.example.llorar.Bitacora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.llorar.Motor_DB.Crud;
import com.example.llorar.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;

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
        rv_mis_bitacoras_p.setHasFixedSize(true);
        rv_mis_bitacoras_p.setLayoutManager(new LinearLayoutManager(this));

        crud.mostrarBitacoras(rv_mis_bitacoras_p);

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
        Bitacora bitacora = documentSnapshot.toObject(Bitacora.class);
        String id = documentSnapshot.getId();
        Intent intent = new Intent(Mis_bitacoras.this, Bitacora_personalizada.class);
        intent.putExtra("id_bitacora", id);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(DocumentSnapshot documentReference, int posicion) {

    }
}

package com.example.llorar.Muestreo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.llorar.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class MuestreoAdapter extends FirestoreRecyclerAdapter<Muestreo, MuestreoAdapter.MuestreoHolder> {


    private OnItemClickListener listener;

    public MuestreoAdapter(@NonNull FirestoreRecyclerOptions<Muestreo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MuestreoHolder holder, int position, @NonNull Muestreo model) {
        holder.tv_nombre_mtr.setText(model.getNombre_mtr());
        holder.tv_localizacion_mtr.setText(model.getLugar_mtr());
        holder.iv_imagen_mtr.setImageResource(R.drawable.flores1);
        Log.e("hola","aquí ando");
    }


    @NonNull
    @Override
    public MuestreoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.muestreo_item, parent, false);
        return new MuestreoHolder(v);
    }


    class MuestreoHolder extends RecyclerView.ViewHolder{
        TextView tv_nombre_mtr, tv_localizacion_mtr;
        ImageView iv_imagen_mtr;


            public MuestreoHolder(@NonNull View itemView) {
                super(itemView);
                tv_nombre_mtr = itemView.findViewById(R.id.tv_nombre_mtr);
                tv_localizacion_mtr = itemView.findViewById(R.id.tv_localizacion_mtr);
                iv_imagen_mtr = itemView.findViewById(R.id.iv_imagen_mtr);

                itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        int posicion = getAdapterPosition();
                        if(posicion != RecyclerView.NO_POSITION && listener != null){
                            listener.onItemClick(getSnapshots().getSnapshot(posicion), posicion);
                        }
                    }
                });

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        int posicion = getAdapterPosition();
                        if(posicion != RecyclerView.NO_POSITION && listener != null){
                            listener.onItemLongClick(getSnapshots().getSnapshot(posicion), posicion);
                        }
                        return true;
                    }
                });


            }
    }


    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int posicion);
        void onItemLongClick(DocumentSnapshot documentReference, int posicion);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
         this.listener = listener;
    }





}

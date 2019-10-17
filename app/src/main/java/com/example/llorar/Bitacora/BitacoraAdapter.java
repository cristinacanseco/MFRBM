package com.example.llorar.Bitacora;

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

public class BitacoraAdapter extends FirestoreRecyclerAdapter<Bitacora, BitacoraAdapter.BitacoraHolder> {


    private OnItemClickListener listener;

    public BitacoraAdapter(@NonNull FirestoreRecyclerOptions<Bitacora> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BitacoraHolder holder, int position, @NonNull Bitacora model) {
        holder.tv_nombre_bitacora.setText(model.getNombre_btc());
        holder.tv_id_bitacora.setText(model.getId_btc());
        holder.tv_lugar_bitacora.setText(model.getUbicacion_btc());
        holder.tv_numero_muestreos_bitacora.setText(model.getCantidad_btc());
        holder.tv_fecha_bitacora.setText(model.getFecha_btc());
        holder.imagen_btc.setImageResource(R.drawable.cactus);
    }


    @NonNull
    @Override
    public BitacoraHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bitacora_item, parent, false);
        return new BitacoraHolder(v);
    }


    class BitacoraHolder extends RecyclerView.ViewHolder{
        TextView tv_nombre_bitacora, tv_id_bitacora, tv_lugar_bitacora,
                tv_numero_muestreos_bitacora,tv_fecha_bitacora;
        ImageView imagen_btc;


            public BitacoraHolder(@NonNull View itemView) {
                super(itemView);
                tv_nombre_bitacora = itemView.findViewById(R.id.tv_nombre_btr);
                tv_id_bitacora = itemView.findViewById(R.id.tv_id_bitacora);
                tv_lugar_bitacora = itemView.findViewById(R.id.tv_lugar_bitacora);
                tv_numero_muestreos_bitacora = itemView.findViewById(R.id.tv_numero_muestreos_bitacora);
                tv_fecha_bitacora = itemView.findViewById(R.id.tv_fecha_bitacora);
                imagen_btc = itemView.findViewById(R.id.imagen_btc);

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

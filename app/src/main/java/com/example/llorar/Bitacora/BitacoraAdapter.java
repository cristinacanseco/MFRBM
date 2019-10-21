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
        holder.tv_nombre_btc.setText(model.getNombre_btc());
        holder.tv_descripcion_btc.setText(model.getDescripcion_btc());
        holder.iv_imagen_btc.setImageResource(R.drawable.flores1);
    }


    @NonNull
    @Override
    public BitacoraHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bitacora_item, parent, false);
        return new BitacoraHolder(v);
    }


    class BitacoraHolder extends RecyclerView.ViewHolder{
        TextView tv_nombre_btc, tv_descripcion_btc;
        ImageView iv_imagen_btc;


            public BitacoraHolder(@NonNull View itemView) {
                super(itemView);
                tv_nombre_btc = itemView.findViewById(R.id.tv_nombre_btc);
                tv_descripcion_btc = itemView.findViewById(R.id.tv_descripcion_btc);
                iv_imagen_btc = itemView.findViewById(R.id.iv_imagen_btc);

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

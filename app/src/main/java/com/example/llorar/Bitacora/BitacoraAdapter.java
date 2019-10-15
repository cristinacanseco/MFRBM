package com.example.llorar.Bitacora;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.llorar.R;

import java.util.ArrayList;

public class BitacoraAdapter extends RecyclerView.Adapter<BitacoraAdapter.ViewHolder> {

    ArrayList<Bitacora> bitacoras;
    Context context;

    public BitacoraAdapter(ArrayList<Bitacora> bitacoras, Context context) {
        this.bitacoras = bitacoras;
        this.context = context;
    }

    @NonNull
    @Override
    public BitacoraAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mis_bitacoras, null, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BitacoraAdapter.ViewHolder holder, int position) {
        Bitacora item = bitacoras.get(position);
        holder.img_bitacora.setImageResource(R.drawable.flor);
        holder.tv_nombre_bitacora.setText(item.getNombre_btc());
        holder.tv_lugar_bitacora.setText(item.getUbicacion_btc());
        holder.tv_numero_muestreos_bitacora.setText(item.getCantidad_btc() + " muestreo(s)");
        holder.tv_fecha_bitacora.setText(item.getFecha_btc());
    }

    @Override
    public int getItemCount() {
        return bitacoras.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_bitacora;
        TextView tv_nombre_bitacora;
        TextView tv_lugar_bitacora;
        TextView tv_numero_muestreos_bitacora;
        TextView tv_fecha_bitacora;

        public ViewHolder(View view) {
            super(view);
            img_bitacora = (ImageView) view.findViewById(R.id.imagen_btc);
            tv_nombre_bitacora =(TextView) view.findViewById(R.id.tv_nombre_bitacora);
            tv_lugar_bitacora = (TextView) view.findViewById(R.id.tv_lugar_bitacora);
            tv_numero_muestreos_bitacora = (TextView) view.findViewById(R.id.tv_numero_muestreos_bitacora);
            tv_fecha_bitacora = (TextView) view.findViewById(R.id.tv_fecha_bitacora);
        }
    }
}

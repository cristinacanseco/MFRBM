package com.example.llorar.Muestreo;

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

public class MuestreoAdapter extends RecyclerView.Adapter<MuestreoAdapter.ViewHolder> {

    ArrayList<Muestreo> muestreo;
    Context context;

    public MuestreoAdapter(ArrayList<Muestreo> muestreos, Context context) {
            this.muestreo = muestreos;
            this.context = context;
            }

    @NonNull
    @Override
    public MuestreoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_muestreo, null, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MuestreoAdapter.ViewHolder holder, int position) {
        Muestreo item = muestreo.get(position);
        holder.img_muestreo.setImageResource(R.drawable.cactus);
        holder.tv_nombre_muestreo.setText(item.getNombre_mtr());
        holder.tv_color_muestreo.setText(item.getColor_mtr ());
        holder.tv_dimension_muestreo.setText(item.getDimension_mtr());
        holder.tv_lugar_muestreo.setText(item.getLugar_mtr());
    }

    @Override
    public int getItemCount() {
            return muestreo.size();
            }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_muestreo;
        TextView tv_nombre_muestreo;
        TextView tv_color_muestreo;
        TextView tv_dimension_muestreo;
        TextView tv_lugar_muestreo;

        public ViewHolder(View view) {
            super(view);
            img_muestreo = (ImageView) view.findViewById(R.id.img_muestreo);
            tv_nombre_muestreo =(TextView) view.findViewById(R.id.tv_nombre_muestreos);
            tv_color_muestreo = (TextView) view.findViewById(R.id.tv_color_muestreo);
            tv_dimension_muestreo = (TextView) view.findViewById(R.id.tv_dimension_muestreo);
            tv_lugar_muestreo = (TextView) view.findViewById(R.id.tv_lugar_muestreo);
        }
    }
}


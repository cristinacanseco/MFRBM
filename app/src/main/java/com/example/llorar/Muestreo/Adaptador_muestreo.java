package com.example.llorar.Muestreo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.llorar.Bitacora.Bitacora;
import com.example.llorar.R;

import java.util.ArrayList;

public class Adaptador_muestreo extends BaseAdapter {

    private ArrayList<Muestreo> listItem;
    private Context context;

    public Adaptador_muestreo(Context context, ArrayList<Muestreo> listItem) {
        this.listItem = listItem;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Muestreo item = (Muestreo) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.item_muestreo, null);

        ImageView img_muestreo = (ImageView) view.findViewById(R.id.img_muestreo);
        TextView tv_nombre_muestreo =(TextView) view.findViewById(R.id.tv_nombre_muestreo);
        TextView tv_ubicacion_muestreo = (TextView) view.findViewById(R.id.tv_ubicacion_muestreo);
        TextView tv_dimension_muestreo = (TextView) view.findViewById(R.id.tv_dimension_muestreo);
        TextView tv_color_muestreo = (TextView) view.findViewById(R.id.tv_color_muestreo);

        img_muestreo.setImageResource(item.getImagen_muestreo());
        tv_nombre_muestreo.setText(item.getNombre_muestreo());
        tv_ubicacion_muestreo.setText(item.getGps_muestreo());
        //tv_dimension_muestreo.setText(item.getDimensiones_muestreo());
        tv_color_muestreo.setText(item.getColor_muestreo());

        return view;
    }
}

package com.example.llorar.Bitacora;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.llorar.R;

import java.util.ArrayList;

public class Adaptador_mi_bitacora extends BaseAdapter {

    private ArrayList<Bitacora> listItem;
    private Context context;

    public Adaptador_mi_bitacora(Context context, ArrayList<Bitacora> listItem) {
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
        Bitacora item = (Bitacora) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.item_mis_bitacoras, null);

        ImageView img_bitacora = (ImageView) view.findViewById(R.id.img_bitacora);
        TextView tv_nombre_bitacora =(TextView) view.findViewById(R.id.tv_nombre_bitacora);
        TextView tv_lugar_bitacora = (TextView) view.findViewById(R.id.tv_lugar_bitacora);
        TextView tv_numero_muestreos_bitacora = (TextView) view.findViewById(R.id.tv_numero_muestreos_bitacora);
        TextView tv_fecha_bitacora = (TextView) view.findViewById(R.id.tv_fecha_bitacora);

        img_bitacora.setImageResource(item.getImagen());
        tv_nombre_bitacora.setText(item.getNombre_bitacora());
        tv_lugar_bitacora.setText(item.getLugar_bitacora());
        tv_numero_muestreos_bitacora.setText(item.getCantidad_muestreos() + " muestreo(s)");
        tv_fecha_bitacora.setText(item.getFecha_bitacora());

        return view;
    }
}

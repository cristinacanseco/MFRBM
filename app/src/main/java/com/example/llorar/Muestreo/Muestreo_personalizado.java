package com.example.llorar.Muestreo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.llorar.R;

public class Muestreo_personalizado extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public Spinner sp_forma_muestreo_personalizado, sp_textura_muestreo_personalizado;
    public Muestreo muestreo_personalizado;
    public ImageView img_muestreo_personalizado;
    public TextView tv_color_muestreo_personalizado,tv_dimension_muestreo_personalizado,
            tv_localizacion_muestreo_personalizado, tv_nombre_muestreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestreo_personalizado);

        sp_forma_muestreo_personalizado = findViewById(R.id.sp_forma_muestreo_personalizado);
        sp_textura_muestreo_personalizado = findViewById(R.id.sp_textura_muestreo_personalizado);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Muestreo_personalizado.this, R.array.forma, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp_forma_muestreo_personalizado.setAdapter(adapter);
        sp_forma_muestreo_personalizado.setOnItemClickListener(this);

        ArrayAdapter<CharSequence> adapter_t = ArrayAdapter.createFromResource(Muestreo_personalizado.this, R.array.textura, android.R.layout.simple_spinner_item);
        adapter_t.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp_textura_muestreo_personalizado.setAdapter(adapter_t);
        sp_textura_muestreo_personalizado.setOnItemClickListener(this);

        muestreo_personalizado =(Muestreo) getIntent().getSerializableExtra("muestreo");

        img_muestreo_personalizado = findViewById(R.id.img_muestreo_personalizado);
        tv_color_muestreo_personalizado = findViewById(R.id.tv_color_muestreo_personalizado);
        tv_dimension_muestreo_personalizado = findViewById(R.id.tv_dimension_muestreo_personalizado);
        tv_localizacion_muestreo_personalizado = findViewById(R.id.tv_localizacion_muestreo_personalizado);
        tv_nombre_muestreo = findViewById(R.id.tv_nombre_bitacora);

        tv_nombre_muestreo.setText(muestreo_personalizado.getNombre_mtr());
        img_muestreo_personalizado.setBackgroundResource(Integer.parseInt(muestreo_personalizado.getImagen_mtr()));
        tv_color_muestreo_personalizado.setText(muestreo_personalizado.getColor_mtr().toString());
        tv_dimension_muestreo_personalizado.setText(muestreo_personalizado.getDimension_mtr().toString());
        tv_localizacion_muestreo_personalizado.setText(muestreo_personalizado.getLugar_mtr());

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if(view.getId() == R.id.sp_forma_muestreo_personalizado){
            String forma = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(this, forma, Toast.LENGTH_SHORT).show();
        }else if(view.getId() == R.id.sp_textura_muestreo_personalizado){
            String textura = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(this, textura, Toast.LENGTH_SHORT).show();
        }

    }
}

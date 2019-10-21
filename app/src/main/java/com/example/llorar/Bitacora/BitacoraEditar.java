package com.example.llorar.Bitacora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.llorar.Motor_DB.Crud;
import com.example.llorar.R;

import java.util.Map;

public class BitacoraEditar extends AppCompatActivity implements View.OnClickListener {

    public Crud crud;
    public TextView tv_fecha_bp, tv_hora_bp, tv_localizacion_bp, tv_muestreos_bp;
    public ImageView img_bp;
    public EditText et_nombre_bp, et_descripcion_bp;
    public Button btn_actualizar_bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitacora_editar);

        crud = new Crud(BitacoraEditar.this);

        et_nombre_bp = findViewById(R.id.et_nombre_bp);
        tv_fecha_bp = findViewById(R.id.tv_fecha_bp);
        img_bp = findViewById(R.id.img_bp);
        tv_hora_bp = findViewById(R.id.tv_hora_bp);
        tv_localizacion_bp = findViewById(R.id.tv_localizacion_bp);
        tv_muestreos_bp = findViewById(R.id.tv_muestreos_bp);
        et_descripcion_bp = findViewById(R.id.et_descripcion_bp);
        btn_actualizar_bp = findViewById(R.id.btn_actualizar_bp);
        btn_actualizar_bp.setOnClickListener(this);

        obtenerDatos(crud.generarBitacora((Map<String, Object>)getIntent().getSerializableExtra("map")));
    }

    private void obtenerDatos(Bitacora model) {
        et_nombre_bp.setText(model.getNombre_btc());
        tv_fecha_bp.setText(model.getDescripcion_btc());
        img_bp.setImageResource(R.drawable.flores1);
        tv_hora_bp.setText(model.getHora_btc());
        tv_localizacion_bp.setText(model.getUbicacion_btc());
        tv_muestreos_bp.setText(model.getCantidad_btc());
        et_descripcion_bp.setText(model.getDescripcion_btc());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_actualizar_bp:
                actualizarDatos(getIntent().getStringExtra("id_bitacora"));
                break;
            default:
                break;
        }
    }

    private void actualizarDatos(String id_bitacora) {
        String nombre_btc = (String) et_nombre_bp.getText().toString();
        String ubicacion_btc = (String) tv_localizacion_bp.getText().toString();
        String cantidad_btc= (String) tv_muestreos_bp.getText().toString();
        String fecha_btc= (String) tv_fecha_bp.getText().toString();
        String hora_btc= (String) tv_hora_bp.getText().toString();
        String imagen_btc=(String) img_bp.getDrawable().toString();
        String descripcion_btc= et_descripcion_bp.getText().toString();

        Bitacora bitacora = new Bitacora(nombre_btc, ubicacion_btc, cantidad_btc, fecha_btc,
                hora_btc, imagen_btc, descripcion_btc);
        crud.editarDatosBitacora(bitacora, id_bitacora);
    }
}

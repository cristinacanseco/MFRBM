package com.example.llorar.Sesiones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.llorar.Motor_DB.Crud;
import com.example.llorar.R;

public class IniciarSesion extends AppCompatActivity implements View.OnClickListener {

    public ImageView iv_regresar_is;
    public TextView tv_registrar_is, et_clave_is,et_correo_is;
    public Button btn_ingresar_is;
    public Crud crud;
    public String correo, clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        crud = new Crud(IniciarSesion.this);

        iv_regresar_is = findViewById(R.id.iv_regresar_is);
        iv_regresar_is.setOnClickListener(this);

        tv_registrar_is = findViewById(R.id.tv_registrar_is);
        tv_registrar_is.setOnClickListener(this);

        btn_ingresar_is = findViewById(R.id.btn_ingresar_is);
        btn_ingresar_is.setOnClickListener(this);

        et_correo_is = findViewById(R.id.et_correo_is);
        et_clave_is = findViewById(R.id.et_clave_is);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_regresar_is:
                startActivity(new Intent(IniciarSesion.this, MainActivity.class));
                break;
            case R.id.tv_registrar_is:
                startActivity(new Intent(IniciarSesion.this, Registro.class));
                break;
            case R.id.btn_ingresar_is:
                correo = et_correo_is.getText().toString().trim();
                clave = et_clave_is.getText().toString().trim();
                iniciarSesion();
            default:
                break;
        }
    }

    private void iniciarSesion() {
        crud.iniciarSesion(correo, clave);
    }
}

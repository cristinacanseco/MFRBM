package com.example.llorar.Sesiones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.llorar.Motor_DB.Crud;
import com.example.llorar.R;

public class InicioSesion extends AppCompatActivity  {

    public EditText et_correo_is, et_clave_is;
    public Button btn_ingresar_is;
    public TextView tv_registrarse;
    public Crud crud;
    public String correo, clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        crud = new Crud(InicioSesion.this);

        et_clave_is = findViewById(R.id.ed_clave_is);
        et_correo_is = findViewById(R.id.et_correo_is);
        tv_registrarse = findViewById(R.id.tv_registrarse);
        tv_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InicioSesion.this, RegistroUsuario.class));
            }
        });

        btn_ingresar_is = findViewById(R.id.btn_ingresar_is);
        
        btn_ingresar_is.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = et_correo_is.getText().toString().trim();
                clave = et_clave_is.getText().toString().trim();
                iniciarSesion();
            }
        });
        
    }

    private void iniciarSesion() {
        crud.iniciarSesion(correo, clave);
    }

}

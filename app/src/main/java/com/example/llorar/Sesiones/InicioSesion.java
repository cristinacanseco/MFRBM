package com.example.llorar.Sesiones;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.llorar.Bitacora.Mis_bitacoras;
import com.example.llorar.R;

public class InicioSesion extends AppCompatActivity {

    public EditText editTextCorreo, editTextClave;
    public String correo="", clave="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        editTextClave = findViewById(R.id.editTextClave);
        editTextCorreo = findViewById(R.id.editTextCorreo);

        correo = editTextCorreo.getText().toString();
        clave = editTextClave.getText().toString();

        if(correo!=null || clave!=null){
            Intent intent = new Intent(InicioSesion.this, Mis_bitacoras.class);
            intent.putExtra("correo_usuario", correo);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Error, datos incompletos", Toast.LENGTH_SHORT).show();
        }

    }
}

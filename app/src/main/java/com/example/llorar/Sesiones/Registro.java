package com.example.llorar.Sesiones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.llorar.Motor_DB.Crud;
import com.example.llorar.R;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    public Button btn_registrate_r;
    public ImageView iv_regresar_r;
    public TextView tv_inicia_ru;
    public EditText et_clave2_r, et_clave_r, et_correo_r,et_nombre_r,et_apellido_r;
    public String correo, clave, clave2, nombre, apellido;
    public Crud crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        crud = new Crud(Registro.this);

        iv_regresar_r = findViewById(R.id.iv_regresar_r);
        iv_regresar_r.setOnClickListener(this);

        tv_inicia_ru = findViewById(R.id.tv_inicia_ru);
        tv_inicia_ru.setOnClickListener(this);

        btn_registrate_r = findViewById(R.id.btn_registrate_r);
        btn_registrate_r.setOnClickListener(this);

        et_nombre_r = findViewById(R.id.et_nombre_r);
        et_apellido_r = findViewById(R.id.et_apellido_r);
        et_correo_r = findViewById(R.id.et_correo_r);
        et_clave_r = findViewById(R.id.et_clave_r);
        et_clave2_r = findViewById(R.id.et_clave2_r);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_regresar_r:
                startActivity(new Intent(Registro.this, MainActivity.class));
                break;
            case R.id.tv_inicia_ru:
                startActivity(new Intent(Registro.this, IniciarSesion.class));
                break;
            case R.id.btn_registrate_r:
                comprobarCampos();
                break;
            default:
                break;
        }
    }

    private void comprobarCampos() {
        correo = et_correo_r.getText().toString().trim();
        clave = et_clave_r.getText().toString().trim();
        clave2 = et_clave2_r.getText().toString().trim();
        nombre = et_nombre_r.getText().toString().trim();
        apellido = et_apellido_r.getText().toString().trim();

        if(correo.isEmpty() && clave.isEmpty() && nombre.isEmpty() && clave2.isEmpty() && apellido.isEmpty()){
            Toast.makeText(Registro.this, "Faltan todos los campos por completar", Toast.LENGTH_SHORT).show();
        }
        else if(correo.isEmpty()){
            et_correo_r.setError("Ingresa un correo electrónico");
            et_correo_r.requestFocus();
        }else if(clave.isEmpty()){
            et_clave_r.setError("Ingresa una clave");
            et_clave_r.requestFocus();
        }
        else if(clave2.isEmpty()){
            et_clave2_r.setError("Ingresa la confirmación de tu contraseña");
            et_clave2_r.requestFocus();
        }
        else if(nombre.isEmpty()) {
            et_nombre_r.setError("Ingresa tu nombre");
            et_nombre_r.requestFocus();
        }else if(apellido.isEmpty()){
            et_apellido_r.setError("Ingresa tu apellido");
            et_apellido_r.requestFocus();
        }
        else{
            if(clave.length() >= 6){
                if(clave2.length() >=6){
                    if(clave.equals(clave2)) {
                        registrarUsuario();
                    }else{
                        // Toast.makeText(RegistroUsuario.this, "Contraseñas inválidas, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                        crud.createAlert("Error", "No coinciden las contraseñas", "OK");
                    }
                }else{
                    et_clave2_r.setError("La clave tiene que tener al menos 6 caracteres");
                    et_clave2_r.requestFocus();
                    et_clave2_r.setText("");
                }
            }else {
                et_clave_r.setError("La clave tiene que tener al menos 6 caracteres");
                et_clave_r.requestFocus();
                et_clave_r.setText("");
            }
        }
    }

    private void registrarUsuario() {
        crud.registrarUsuario(new Usuario(nombre,apellido, correo,clave, false));
    }
}

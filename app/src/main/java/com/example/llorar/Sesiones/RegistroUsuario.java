package com.example.llorar.Sesiones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.llorar.Motor_DB.Crud;
import com.example.llorar.R;

public class RegistroUsuario extends AppCompatActivity {
    public EditText et_correo_ru, et_clave_ru, et_nombre_ru, et_apellido_ru, et_clave_confirma_ru;
    private String correo, clave, clave2, nombre, apellido;
    public Button btn_crear_ru;
    public TextView tv_iniciar_sesion;
    public Crud crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        crud = new Crud(RegistroUsuario.this);

        et_correo_ru = (EditText) findViewById(R.id.et_correo_ru);
        et_clave_ru = (EditText) findViewById(R.id.et_clave_ru);
        et_clave_confirma_ru = (EditText) findViewById(R.id.et_clave_confirma_ru);
        et_nombre_ru = (EditText) findViewById(R.id.et_nombre_ru);
        et_apellido_ru = (EditText) findViewById(R.id.et_apellido_ru);
        tv_iniciar_sesion = (TextView) findViewById(R.id.tv_iniciar_sesion);
        btn_crear_ru = (Button) findViewById(R.id.btn_crear_ru);

        tv_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroUsuario.this, InicioSesion.class);
                startActivity(intent);
            }
        });

        btn_crear_ru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = et_correo_ru.getText().toString().trim();
                clave = et_clave_ru.getText().toString().trim();
                clave2 = et_clave_confirma_ru.getText().toString().trim();
                nombre = et_nombre_ru.getText().toString().trim();
                apellido = et_apellido_ru.getText().toString().trim();

                if(correo.isEmpty() && clave.isEmpty() && nombre.isEmpty() && clave2.isEmpty() && apellido.isEmpty()){
                    Toast.makeText(RegistroUsuario.this, "Faltan todos los campos por completar", Toast.LENGTH_SHORT).show();
                }
                else if(correo.isEmpty()){
                    et_correo_ru.setError("Ingresa un correo electrónico");
                    et_correo_ru.requestFocus();
                }else if(clave.isEmpty()){
                    et_clave_ru.setError("Ingresa una clave");
                    et_clave_ru.requestFocus();
                }
                else if(clave2.isEmpty()){
                    et_clave_confirma_ru.setError("Ingresa la confirmación de tu contraseña");
                    et_clave_confirma_ru.requestFocus();
                }
                else if(nombre.isEmpty()) {
                    et_nombre_ru.setError("Ingresa tu nombre");
                    et_nombre_ru.requestFocus();
                }else if(apellido.isEmpty()){
                    et_apellido_ru.setError("Ingresa tu apellido");
                    et_apellido_ru.requestFocus();
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
                            et_clave_confirma_ru.setError("La clave tiene que tener al menos 6 caracteres");
                            et_clave_confirma_ru.requestFocus();
                            et_clave_confirma_ru.setText("");
                        }
                    }else {
                        et_clave_ru.setError("La clave tiene que tener al menos 6 caracteres");
                        et_clave_ru.requestFocus();
                        et_clave_ru.setText("");
                    }
                }
            }
        });
    }

    private void registrarUsuario() {

        crud.registrarUsuario(new Usuario(nombre,apellido, correo,clave, false));
    }


}

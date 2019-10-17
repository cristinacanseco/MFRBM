package com.example.llorar.Motor_DB;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.llorar.Bitacora.Bitacora;
import com.example.llorar.Bitacora.BitacoraAdapter;
import com.example.llorar.Bitacora.Mis_bitacoras;
import com.example.llorar.Muestreo.Muestreo;
import com.example.llorar.Sesiones.IniciarSesion;
import com.example.llorar.Sesiones.Usuario;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class Crud {

    public Context context;
    public FirebaseAuth mAuth;
    public FirebaseFirestore mFirestore;
    public String usuario_db="Usuario";
    public String bitacora_db="Bitacora";
    public String muestreo_db="Muestreo";
    public BitacoraAdapter adapterPrueba;
    public CollectionReference bitacoraRefC;
    public DocumentReference bitacoraRefD;

    public Crud(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }

    //S E S I O N E S

    public void registrarUsuario(final Usuario usuario ) {
        mAuth.createUserWithEmailAndPassword( usuario.getCorreo_usr(),usuario.getClave_usr()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String id= mAuth.getCurrentUser().getUid();

                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre_usr", usuario.getNombre_usr());
                    map.put("apellido_usr", usuario.getApellido_usr());
                    map.put("correo_usr", usuario.getCorreo_usr());
                    map.put("clave_usr", usuario.getClave_usr());
                    map.put("imagen_usr", usuario.getNombre_usr());
                    map.put("admin", usuario.getAdmin_usr());

                    mFirestore.collection(usuario_db).document(id).set(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    context.startActivity(new Intent(new Intent(context, Mis_bitacoras.class)));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    createAlert("Error", "No se agregó al usuario. \nVuelve a intentarlo\n "+ e.getMessage().toString(), "OK");
                                    // Log.e(TAG, "Mensaje: "+ e.getMessage().toString()+ " Localización: " +e.getLocalizedMessage().toString());
                                }
                            });
                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        createAlert("Error", "El correo ya existe. \nVuelve a intentarlo\n ", "OK");
                    }else{
                        createAlert("Error", "No se pudo regisrar al usuario.", "OK");
                    }
                }
            }
        });
    }

    public void iniciarSesion(String correo, String clave) {
        mAuth.signInWithEmailAndPassword(correo, clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    createAlert("Éxito", "Bienvenido ", "OK");
                    context.startActivity(new Intent(context, Mis_bitacoras.class));
                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        createAlert("Error", "No coindice la contraseña con el coreo \nVuelve a intentarlo\n ", "OK");

                    }else{
                        createAlert("Error", "No existe el usuario.", "OK");
                    }
                }
            }
        });
    }

    public void cerrarSesion(){
        mAuth.signOut();
        context.startActivity(new Intent (context, IniciarSesion.class));
    }


    //B I T Á C O R A S

    public void insertarDatoBitacora(Bitacora bitacora){
        String id= mAuth.getCurrentUser().getUid();
        if(bitacora.getNombre_btc().trim().isEmpty() ){
            createAlert("Error", "Agrega un nombre a la bitácora. \nVuelve a intentarlo\n ", "OK");
            return;
        }

        mFirestore.collection(usuario_db).document(id).collection(bitacora_db).add(generarMapBitacora(bitacora))
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            context.startActivity(new Intent(new Intent(context, Mis_bitacoras.class)));
                        }else{
                            createAlert("Error", "No se agregaron los datos de la bitácora. \nVuelve a intentarlo\n ", "OK");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        createAlert("Error", "Ups...hubo un problema. \nVuelve a intentarlo\n ", "OK");
                    }
        });
    }

    public void editarDatosBitacora(Bitacora bitacora, String id_bitacora){
        String id= mAuth.getCurrentUser().getUid();

        mFirestore.collection(usuario_db).document(id).collection(bitacora_db).document(id_bitacora).update(generarMapBitacora(bitacora))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        createAlert("Éxito", "Bitácora actualizada", "OK");
                        context.startActivity(new Intent(new Intent(context, Mis_bitacoras.class)));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        createAlert("Error", "No se actualizó la bitácora. \nVuelve a intentarlo\n "+ e.getMessage().toString(), "OK");
                        // Log.e(TAG, "Mensaje: "+ e.getMessage().toString()+ " Localización: " +e.getLocalizedMessage().toString());
                    }
                });

    }

    public void borrarBitacora(int id_bitacora){

        adapterPrueba.getSnapshots().getSnapshot(id_bitacora).getReference().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
                public void onSuccess(Void aVoid) {
                    createAlert("Éxito", "Bitácora eliminada exitosamente", "OK");
                    context.startActivity(new Intent(new Intent(context, Mis_bitacoras.class)));
                }
            })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        createAlert("Error", "\nNo se pudo eliminar la bitácora seleccionada. \nVuelve a intentarlo\n "+ e.getMessage().toString(), "OK");
                    }
        });

    }

    private Map<String, Object> generarMapBitacora(Bitacora bitacora) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre_btc", bitacora.getNombre_btc());
        map.put("ubicacion_btc", bitacora.getUbicacion_btc());
        map.put("cantidad_btc", bitacora.getCantidad_btc());
        map.put("fecha_btc", bitacora.getFecha_btc());
        map.put("hora_btc", bitacora.getHora_btc());
        map.put("imagen_btc", bitacora.getImagen_btc());
        map.put("descripcion_btc", bitacora.getDescripcion_btc());
        return map;
    }

    public void mostrarBitacoras(final RecyclerView rv_mis_bitacoras){
        String id= mAuth.getCurrentUser().getUid();
        bitacoraRefC = mFirestore.collection(usuario_db).document(id).collection(bitacora_db);

        Query query = bitacoraRefC.orderBy("nombre_btc");
        FirestoreRecyclerOptions<Bitacora> options = new FirestoreRecyclerOptions.Builder<Bitacora>()
                .setQuery(query, Bitacora.class)
                .build();
        adapterPrueba = new BitacoraAdapter(options);

        rv_mis_bitacoras.setAdapter(adapterPrueba);
    }

    public void obtenerBitacora(RecyclerView rv_bitacora_personalizada, String id_bitacora){
        String id= mAuth.getCurrentUser().getUid();

        mFirestore.collection(usuario_db).document(id).collection(bitacora_db).document(id_bitacora).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Toast.makeText(context, "ID: "+documentSnapshot.getId()+"\n"+documentSnapshot.getData(), Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(context, "Error.\nVuelve a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //M U E S T R E O S

    private Map<String, Object> generarMapMuestreo(Muestreo muestreo) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre_mtr", muestreo.getNombre_mtr());
        map.put("imagen_mtr", muestreo.getImagen_mtr());
        map.put("forma_mtr", muestreo.getForma_mtr());
        map.put("textura_mtr",muestreo.getTextura_mtr());
        map.put("color_mtr", muestreo.getColor_mtr());
        map.put("dimension_mtr", muestreo.getDimension_mtr());
        map.put("lugar_mtr", muestreo.getLugar_mtr());
        map.put("fecha_mtr",muestreo.getFecha_mtr());
        map.put("hora_mtr",muestreo.getHora_mtr());
        return map;
    }


    //O T R O S
    public void createAlert(String alertTitle, String alertMessage, String positiveText){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(alertTitle)
                .setMessage(alertMessage)
                .setPositiveButton(positiveText, null)
                .create().show();
    }

    public FirebaseAuth getmAuth() { return mAuth; }

    public FirebaseFirestore getmFirestore() { return mFirestore; }

    public BitacoraAdapter getAdapterPrueba() { return adapterPrueba; }
}

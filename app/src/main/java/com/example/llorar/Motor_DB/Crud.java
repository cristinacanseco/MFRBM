package com.example.llorar.Motor_DB;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.llorar.Bitacora.Bitacora;
import com.example.llorar.Bitacora.BitacoraAdapter;
import com.example.llorar.Bitacora.Mis_bitacoras;
import com.example.llorar.Muestreo.Muestreo;
import com.example.llorar.Muestreo.MuestreoAdapter;
import com.example.llorar.Sesiones.InicioSesion;
import com.example.llorar.Sesiones.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Crud {

    public Context context;
    public FirebaseAuth mAuth;
    public FirebaseFirestore mFirestore;
    public ArrayList<Bitacora> bitacoras;
    public ArrayList<Muestreo> muestreos;
    public String usuario_db="Usuario";
    public String bitacora_db="Bitacora";
    public String muestreo_db="Muestreo";

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
        context.startActivity(new Intent (context, InicioSesion.class));
    }


    //B I T Á C O R A S

    public void insertarDatoBitacora(Bitacora bitacora){
        String id= mAuth.getCurrentUser().getUid();

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
                }) ;
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

    public void borrarBitacora(String id_bitacora){
        String id= mAuth.getCurrentUser().getUid();

        mFirestore.collection(usuario_db).document(id).collection(bitacora_db).document(id_bitacora).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        createAlert("Éxito", "Bitácora eliminada exitosamente", "OK");
                        context.startActivity(new Intent(new Intent(context, Mis_bitacoras.class)));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        createAlert("Error", "No se pudo eliminar la bitácora seleccionada. \nVuelve a intentarlo\n "+ e.getMessage().toString(), "OK");
                        // Log.e(TAG, "Mensaje: "+ e.getMessage().toString()+ " Localización: " +e.getLocalizedMessage().toString());
                    }
                });
    }

    public void mostrarTodasBitacoras(final RecyclerView rv_mis_bitacoras){
        String id= mAuth.getCurrentUser().getUid();
        bitacoras = new ArrayList<>();

        CollectionReference reference = mFirestore.collection(usuario_db).document(id).collection(bitacora_db);
        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size() == 0){
                    createAlert("Error", "No hay datos. \nVuelve a intentarlo\n ", "OK");
                }else {
                    for (QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots) {
                        bitacoras.add(documentSnapshots.toObject(Bitacora.class));
                        Log.e(TAG, "Mensaje:");
                    }
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                BitacoraAdapter bitacoraAdapter = new BitacoraAdapter(bitacoras,context);
                rv_mis_bitacoras.setAdapter(bitacoraAdapter);
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
        return map;
    }

    public String obtenerIdBitacora(){
        String id_bitacora = "";
        return id_bitacora;
    }


    //M U E S T R E O S

    public void insertarDatoMuestreo(Muestreo muestreo, String id_bitacora){
        String id= mAuth.getCurrentUser().getUid();

        mFirestore.collection(usuario_db).document(id).collection(bitacora_db).document(id_bitacora).set(generarMapMuestreo(muestreo))
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
    }

    public void editarDatoMuestreo(Muestreo muestreo, String id_bitacora, String id_muestreo){
        String id= mAuth.getCurrentUser().getUid();

        mFirestore.collection(usuario_db).document(id).collection(bitacora_db).document(id_bitacora)
                .collection(muestreo_db).document(id_muestreo).update(generarMapMuestreo(muestreo))
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
    }

    public void borrarMuestreo(String id_bitacora, String id_muestreo){
        String id= mAuth.getCurrentUser().getUid();

        mFirestore.collection(usuario_db).document(id).collection(bitacora_db).document(id_bitacora).collection(muestreo_db).document(id_muestreo).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        createAlert("Éxito", "Bitácora eliminada exitosamente", "OK");
                        context.startActivity(new Intent(new Intent(context, Mis_bitacoras.class)));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        createAlert("Error", "No se pudo eliminar la bitácora seleccionada. \nVuelve a intentarlo\n "+ e.getMessage().toString(), "OK");
                        // Log.e(TAG, "Mensaje: "+ e.getMessage().toString()+ " Localización: " +e.getLocalizedMessage().toString());
                    }
                });

        if(!hayMasNodos(id_bitacora)){
            borrarBitacora(id_bitacora);
        }
    }

    public void mostrarTodosMuestreos(final RecyclerView rv_mis_muestreos, String id_bitacora){
        String id= mAuth.getCurrentUser().getUid();
        muestreos = new ArrayList<>();

        CollectionReference reference = mFirestore.collection(usuario_db).document(id)
                .collection(bitacora_db).document(id_bitacora).collection(muestreo_db);
        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshots: queryDocumentSnapshots){
                    muestreos.add(documentSnapshots.toObject(Muestreo.class));
                    Log.e(TAG, "Mensaje2: ");
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                MuestreoAdapter muestreosAdapter = new MuestreoAdapter(muestreos,context);
                rv_mis_muestreos.setAdapter(muestreosAdapter);
            }
        });
    }

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

    private boolean hayMasNodos(String id_bitacora) {
        return true;
    }

}

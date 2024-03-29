package com.example.llorar.Motor_DB;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.llorar.Bitacora.Bitacora;
import com.example.llorar.Bitacora.BitacoraAdapter;
import com.example.llorar.Bitacora.Mis_bitacoras;
import com.example.llorar.Muestreo.Mis_muestreos;
import com.example.llorar.Muestreo.Muestreo;
import com.example.llorar.Muestreo.MuestreoAdapter;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class Crud {

    public Context context;
    public FirebaseAuth mAuth;
    public FirebaseFirestore mFirestore;
    public String usuario_db = "Usuario";
    public String bitacora_db = "Bitacora";
    public String muestreo_db = "Muestreo";
    public BitacoraAdapter adapterBitacora;
    public CollectionReference collectionReference;
    public MuestreoAdapter adapterMuestreo;


    public Crud(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }

    //S E S I O N E S

    public void registrarUsuario(final Usuario usuario) {
        mAuth.createUserWithEmailAndPassword(usuario.getCorreo_usr(), usuario.getClave_usr()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = mAuth.getCurrentUser().getUid();

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
                                    createAlert("Error", "No se agregó al usuario. \nVuelve a intentarlo\n " + e.getMessage().toString(), "OK");
                                    // Log.e(TAG, "Mensaje: "+ e.getMessage().toString()+ " Localización: " +e.getLocalizedMessage().toString());
                                }
                            });
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        createAlert("Error", "El correo ya existe. \nVuelve a intentarlo\n ", "OK");
                    } else {
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
                if (task.isSuccessful()) {
                    //createAlert("Éxito", "Bienvenido ", "OK");
                    context.startActivity(new Intent(context, Mis_bitacoras.class));
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        createAlert("Error", "No coindice la contraseña con el coreo \nVuelve a intentarlo\n ", "OK");

                    } else {
                        createAlert("Error", "No existe el usuario.", "OK");
                    }
                }
            }
        });
    }

    public void cerrarSesion() {
        mAuth.signOut();
        context.startActivity(new Intent(context, IniciarSesion.class));
    }


    //B I T Á C O R A S

    public void insertarDatoBitacora(Bitacora bitacora) {
        String id = mAuth.getCurrentUser().getUid();
        if (bitacora.getNombre_btc().trim().isEmpty()) {
            createAlert("Error", "Agrega un nombre a la bitácora. \nVuelve a intentarlo\n ", "OK");
            return;
        }

        mFirestore.collection(usuario_db).document(id).collection(bitacora_db).add(generarMapBitacora(bitacora))
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            context.startActivity(new Intent(new Intent(context, Mis_bitacoras.class)));
                        } else {
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

    public void editarDatosBitacora(Bitacora bitacora, String id_bitacora) {
        String id = mAuth.getCurrentUser().getUid();

        mFirestore.collection(usuario_db).document(id).collection(bitacora_db).document(id_bitacora).update(generarMapBitacora(bitacora))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        createAlert("Éxito", "Bitácora actualizada", "OK");
                        context.startActivity(new Intent(new Intent(context, Mis_bitacoras.class)));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                createAlert("Error", "No se actualizó la bitácora. \nVuelve a intentarlo\n " + e.getMessage().toString(), "OK");
            }
        });

    }

    public void borrarBitacora(int id_bitacora) {
        adapterBitacora.getSnapshots().getSnapshot(id_bitacora).getReference().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                createAlert("Éxito", "Bitácora eliminada exitosamente", "OK");
                context.startActivity(new Intent(new Intent(context, Mis_bitacoras.class)));
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        createAlert("Error", "\nNo se pudo eliminar la bitácora seleccionada. \nVuelve a intentarlo\n " + e.getMessage().toString(), "OK");
                    }
                });
    }

    public Bitacora generarBitacora(Map<String, Object> map){

        String nombre_btc = (String) map.get("nombre_btc");
        String ubicacion_btc = (String) map.get("ubicacion_btc");
        String cantidad_btc= (String) map.get("cantidad_btc");
        String fecha_btc= (String)map.get("fecha_btc");
        String hora_btc= (String) map.get("hora_btc");
        String imagen_btc=(String)map.get("imagen_btc");
        String descripcion_btc= (String)map.get("descripcion_btc");

        return new Bitacora (nombre_btc,ubicacion_btc,cantidad_btc,fecha_btc,hora_btc,imagen_btc,descripcion_btc);

    }

    public void mostrarBitacoras(final RecyclerView rv_mis_bitacoras){
        String id= mAuth.getCurrentUser().getUid();
        collectionReference = mFirestore.collection(usuario_db).document(id).collection(bitacora_db);

        Query query = collectionReference.orderBy("nombre_btc");
        FirestoreRecyclerOptions<Bitacora> options = new FirestoreRecyclerOptions.Builder<Bitacora>()
                .setQuery(query, Bitacora.class)
                .build();
        adapterBitacora = new BitacoraAdapter(options);

        rv_mis_bitacoras.setAdapter(adapterBitacora);
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


    //M U E S T R E O S
    public void insertarDatoMuestreo(Muestreo muestreo, String id_bitacora) {
        String id = mAuth.getCurrentUser().getUid();
        if (muestreo.getNombre_mtr().trim().isEmpty()) {
            createAlert("Error", "Agrega un nombre al muestreo. \nVuelve a intentarlo\n ", "OK");
            return;
        }

        mFirestore.collection(usuario_db).document(id).collection(bitacora_db).document(id_bitacora).collection(muestreo_db).add(generarMapMuestreo(muestreo))
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            context.startActivity(new Intent(new Intent(context, Mis_muestreos.class)));
                        } else {
                            createAlert("Error", "No se agregaron los datos al muestreo. \nVuelve a intentarlo\n ", "OK");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                createAlert("Error", "Ups...hubo un problema. \nVuelve a intentarlo\n ", "OK");
            }
        });
    }

    public void editarDatosMuestreo(Muestreo muestreo, String id_bitacora, String id_muestreo) {
        String id = mAuth.getCurrentUser().getUid();

        mFirestore.collection(usuario_db).document(id).collection(bitacora_db).document(id_bitacora).collection(muestreo_db).document(id_muestreo).update(generarMapMuestreo(muestreo))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        createAlert("Éxito", "¡Muestreo actualizado!", "OK");
                        context.startActivity(new Intent(new Intent(context, Mis_muestreos.class)));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                createAlert("Error", "No se actualizó el muestreo. \nVuelve a intentarlo\n " + e.getMessage().toString(), "OK");
            }
        });

    }

    public void borrarMuestreo( int id_muestreo) {
         adapterMuestreo.getSnapshots().getSnapshot(id_muestreo).getReference().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                createAlert("Éxito", "Muestreo eliminado exitosamente", "OK");
                context.startActivity(new Intent(new Intent(context, Mis_muestreos.class)));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                createAlert("Error", "\nNo se pudo eliminar el muestreo seleccionado. \nVuelve a intentarlo\n " + e.getMessage().toString(), "OK");
            }
        });
    }

    public Muestreo generarMuestreo(Map<String, Object> map){

        String nombre_mtr = (String) map.get("nombre_mtr");
        String imagen_mtr = (String) map.get("imagen_mtr");
        String forma_mtr= (String) map.get("forma_mtr");
        String fecha_btc= (String)map.get("fecha_btc");
        String textura_mtr= (String) map.get("textura_mtr");
        String color_mtr=(String)map.get("color_mtr");
        String dimension_mtr= (String)map.get("dimension_mtr");
        String lugar_mtr= (String)map.get("lugar_mtr");
        String hora_mtr= (String)map.get("hora_mtr");
        String fecha_mtr= (String)map.get("fecha_mtr");

        return new Muestreo (nombre_mtr,imagen_mtr,forma_mtr,fecha_btc,textura_mtr,color_mtr,dimension_mtr,lugar_mtr,fecha_mtr,hora_mtr);

    }

    public void mostrarMuestreo(final RecyclerView rv_mis_muestreos, String id_bitacora){


        String id= mAuth.getCurrentUser().getUid();
        createAlert("Error", "ID: "+id, "OK");
        collectionReference = mFirestore.collection(usuario_db).document(id).collection(bitacora_db).document(id_bitacora).collection(muestreo_db);

        Query query = collectionReference.orderBy("nombre_mtr");
        FirestoreRecyclerOptions<Muestreo> options = new FirestoreRecyclerOptions.Builder<Muestreo>()
                .setQuery(query, Muestreo.class)
                .build();
        adapterMuestreo = new MuestreoAdapter(options);

        rv_mis_muestreos.setAdapter(adapterMuestreo);
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

    public BitacoraAdapter getAdapterPrueba() { return adapterBitacora; }

    public MuestreoAdapter getAdapterMuestreo() { return adapterMuestreo; }
}

package com.example.biblioteca;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.biblioteca.modelos.rol;
import com.example.biblioteca.modelos.usuario;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {

    Button iniciar, crear, Bcrear;
    EditText usuario, contrasena;
    boolean bien = false;
    String s_usuario, s_contrasena;
    String[] u = {"", ""};
    Spinner rol;
    UserAPI userAPI;

    List<rol> roles;


    ArrayAdapter<String> rolesAdapter; // Agregar un adaptador para el Spinner de roles

    @SuppressLint({"SuspiciousIndentation", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciar = findViewById(R.id.crear_e);
        usuario = findViewById(R.id.usuario);
        contrasena = findViewById(R.id.contrasena);
        Bcrear = findViewById(R.id.Bcrear);



        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuarioTexto = usuario.getText().toString();
                String contrasenaTexto = contrasena.getText().toString();
                if (usuarioTexto.equals("") && contrasenaTexto.equals("")) {
                    Intent cambio = new Intent(login.this, MainActivity.class);
                    cambio.putExtra("clave", u);
                    startActivity(cambio);
                    usuario.setText("");
                    contrasena.setText("");
                } else {
                    Toast.makeText(login.this, "Usuario o Contraseña inválidos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Bcrear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Dialog cambio = new Dialog(login.this);
                cambio.setContentView(R.layout.activity_crear_usuario);

                EditText cedula = cambio.findViewById(R.id.cedula);
                EditText nombre = cambio.findViewById(R.id.nombre);
                EditText apellido = cambio.findViewById(R.id.apellido);
                EditText email = cambio.findViewById(R.id.email);
                Spinner rolSpinner = cambio.findViewById(R.id.rol);
                rol=rolSpinner;
                EditText telefono = cambio.findViewById(R.id.telefono);
                EditText Contrasena = cambio.findViewById(R.id.contrasena);

                Button crear = cambio.findViewById(R.id.Bcrear);

                cambio.show();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(UserAPI.API)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                userAPI = retrofit.create(UserAPI.class);

                cargarRoles();

                crear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Obtener los valores de los EditText y Spinner
                        String cedulavalue = cedula.getText().toString();
                        String nombrevalue = nombre.getText().toString();
                        String apellidovalue = apellido.getText().toString();
                        String emailvalue = email.getText().toString();
                        String rolvalue = rolSpinner.getSelectedItem().toString();
                        String telefonovalue = telefono.getText().toString();
                        String contrasenavalue = Contrasena.getText().toString();

                        // Crear un objeto de tipo usuario con los valores obtenidos
                        usuario nuevoUsuario = new usuario();
                        nuevoUsuario.setCedula(cedulavalue);
                        nuevoUsuario.setNombre(nombrevalue);
                        nuevoUsuario.setApellidos(apellidovalue);
                        nuevoUsuario.setemail(emailvalue);

                        for (rol r : roles) {
                            if(r.getDescripcion().toLowerCase().equals(rolvalue.toLowerCase())){
                                nuevoUsuario.setRol(r.getId());
                                break;
                            }
                        }

                        nuevoUsuario.setTelefono(telefonovalue);
                        nuevoUsuario.setestado("activo");
                        nuevoUsuario.setContrasena(contrasenavalue);

                        // Llamar al método createusuario de la interfaz UserAPI para crear el usuario
                        Call<usuario> call = userAPI.createusuario(nuevoUsuario);
                        call.enqueue(new Callback<usuario>() {
                            @Override
                            public void onResponse(Call<usuario> call, Response<usuario> response) {
                                if (response.isSuccessful()) {
                                    // El usuario se ha creado exitosamente
                                    Toast.makeText(login.this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show();
                                    cambio.dismiss();
                                } else {
                                    // Ocurrió un error al crear el usuario
                                    Toast.makeText(login.this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<usuario> call, Throwable t) {
                                // Error de red al crear el usuario
                                Toast.makeText(login.this, "Error de red al crear el usuario", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


            }
        });
    }

    private void cargarRoles() {
        Call<List<rol>> call = userAPI.rolesobtenidos();

        call.enqueue(new Callback<List<rol>>() {
            @Override
            public void onResponse(Call<List<rol>> call, Response<List<rol>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    roles = response.body();
                    actualizarSpinnerRoles(roles);
                } else {
                    Toast.makeText(login.this, "Error al cargar los roles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<rol>> call, Throwable t) {
                Toast.makeText(login.this, "Error de red al cargar los roles", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarSpinnerRoles(List<rol> roles) {
        // Obtén los nombres de los roles y actualiza el adaptador del Spinner
        List<String> nombresRoles = new ArrayList<>();
        for (rol r : roles) {
            nombresRoles.add(r.getDescripcion());
        }

        // Crea un ArrayAdapter para el Spinner y configúralo con los nombres de los roles
        rolesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresRoles);
        rolesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asigna el adaptador al Spinner de roles
        rol.setAdapter(rolesAdapter);
    }
}

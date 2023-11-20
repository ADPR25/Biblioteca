package com.example.biblioteca.ui.slideshow;

        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Spinner;

        import androidx.annotation.NonNull;
        import androidx.fragment.app.Fragment;
        import androidx.lifecycle.ViewModelProvider;

        import com.example.biblioteca.R;
        import com.example.biblioteca.UserAPI;
        import com.example.biblioteca.databinding.FragmentSlideshowBinding;
        import com.example.biblioteca.modelos.estado_equipo;
        import com.example.biblioteca.modelos.estado_prestamo;
        import com.example.biblioteca.modelos.rol;
        import com.example.biblioteca.modelos.tipo_equipo;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

public class SlideshowFragment extends Fragment {
    UserAPI userAPI;

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Spinner spinner = root.findViewById(R.id.spinner);
        EditText tipo_de_equipo = root.findViewById(R.id.tipo_de_equipo);
        Button crear_e = root.findViewById(R.id.crear_e);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserAPI.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userAPI = retrofit.create(UserAPI.class);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle spinner item selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // No action needed
            }
        });

        crear_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedOption = spinner.getSelectedItem().toString();

                switch (selectedOption) {
                    case "rol":
                        // Create a role
                        String roleName = tipo_de_equipo.getText().toString().trim();
                        if (!roleName.isEmpty()) {
                            rol newRole = new rol();
                            newRole.setDescripcion(roleName);

                            Call<rol> call = userAPI.createrol(newRole);

                            call.enqueue(new Callback<rol>() {
                                @Override
                                public void onResponse(Call<rol> call, Response<rol> response) {
                                    if (response.isSuccessful()) {
                                        // Role created successfully
                                        rol resultRole = response.body();
                                    } else {
                                        // Handle unsuccessful response
                                    }
                                }

                                @Override
                                public void onFailure(Call<rol> call, Throwable t) {
                                    // Handle failure
                                }
                            });
                        } else {
                            // Handle case where the field is empty
                        }
                        break;

                    case "estado equipo":
                        // Create estado_equipo
                        String estadoEquipoName = tipo_de_equipo.getText().toString().trim();
                        if (!estadoEquipoName.isEmpty()) {
                            estado_equipo newEstadoEquipo = new estado_equipo();
                            newEstadoEquipo.setDescripcion(estadoEquipoName);

                            Call<estado_equipo> callEstadoEquipo = userAPI.createestado(newEstadoEquipo);

                            callEstadoEquipo.enqueue(new Callback<estado_equipo>() {
                                @Override
                                public void onResponse(Call<estado_equipo> call, Response<estado_equipo> response) {
                                    if (response.isSuccessful()) {
                                        // Estado_equipo created successfully
                                        estado_equipo resultEstadoEquipo = response.body();
                                    } else {
                                        // Handle unsuccessful response
                                    }
                                }

                                @Override
                                public void onFailure(Call<estado_equipo> call, Throwable t) {
                                    // Handle failure
                                }
                            });
                        } else {
                            // Handle case where the field is empty
                        }
                        break;

                    case "tipo de equipo":
                        String tipo_e_value = tipo_de_equipo.getText().toString().trim(); // Usa trim() para eliminar espacios en blanco al inicio y al final
                        if (!tipo_e_value.isEmpty()) {
                            tipo_equipo nuevo_tipo = new tipo_equipo();
                            nuevo_tipo.setDescripcion(tipo_e_value);

                            Call<tipo_equipo> call = userAPI.createtipo(nuevo_tipo);

                            call.enqueue(new Callback<tipo_equipo>() {
                                @Override
                                public void onResponse(Call<tipo_equipo> call, Response<tipo_equipo> response) {
                                    if (response.isSuccessful()) {
                                        tipo_equipo resultado = response.body();
                                    } else {
                                    }
                                }

                                @Override
                                public void onFailure(Call<tipo_equipo> call, Throwable t) {
                                }
                            });
                        } else {
                        }
                        break;

                    case "estado prestamo":
                        // Create estado_prestamo
                        String estadoPrestamoName = tipo_de_equipo.getText().toString().trim();
                        if (!estadoPrestamoName.isEmpty()) {
                            estado_prestamo newEstadoPrestamo = new estado_prestamo();
                            newEstadoPrestamo.setDescripcion(estadoPrestamoName);

                            Call<estado_prestamo> callEstadoPrestamo = userAPI.createestado_p(newEstadoPrestamo);

                            callEstadoPrestamo.enqueue(new Callback<estado_prestamo>() {
                                @Override
                                public void onResponse(Call<estado_prestamo> call, Response<estado_prestamo> response) {
                                    if (response.isSuccessful()) {
                                        // Estado_prestamo created successfully
                                        estado_prestamo resultEstadoPrestamo = response.body();
                                    } else {
                                        // Handle unsuccessful response
                                    }
                                }

                                @Override
                                public void onFailure(Call<estado_prestamo> call, Throwable t) {
                                    // Handle failure
                                }
                            });
                        } else {
                            // Handle case where the field is empty
                        }
                        break;
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

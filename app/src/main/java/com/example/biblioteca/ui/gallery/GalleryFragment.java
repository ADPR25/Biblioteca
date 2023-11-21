package com.example.biblioteca.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.biblioteca.R;
import com.example.biblioteca.databinding.FragmentGalleryBinding;
import com.example.biblioteca.modelos.estado_equipo;
import com.example.biblioteca.modelos.tipo_equipo;
import com.example.biblioteca.modelos.equipos;
import com.example.biblioteca.UserAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private Spinner tipoEquipoSpinner;
    private Spinner estadoEquipoSpinner;
    private List<tipo_equipo> tiposEquipos;
    private List<estado_equipo> estadosEquipos;
    private ArrayAdapter<String> tiposEquiposAdapter;
    private ArrayAdapter<String> estadosEquiposAdapter;
    private UserAPI userAPI;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tipoEquipoSpinner = root.findViewById(R.id.tipo_de_equipo);
        estadoEquipoSpinner = root.findViewById(R.id.estado_equipo);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserAPI.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userAPI = retrofit.create(UserAPI.class);

        cargarTiposEquipos();
        cargarEstadosEquipos();

        root.findViewById(R.id.crear_e).setOnClickListener(v -> crearEquipo());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void cargarTiposEquipos() {
        Call<List<tipo_equipo>> call = userAPI.tipo_equipo_obtenidos();

        call.enqueue(new Callback<List<tipo_equipo>>() {
            @Override
            public void onResponse(Call<List<tipo_equipo>> call, Response<List<tipo_equipo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tiposEquipos = response.body();
                    actualizarSpinnertipos(tiposEquipos);
                } else {
                    Toast.makeText(requireContext(), "Error al cargar los tipos de equipos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<tipo_equipo>> call, Throwable t) {
                Toast.makeText(requireContext(), "Error de red al cargar los tipos de equipos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarEstadosEquipos() {
        Call<List<estado_equipo>> call = userAPI.estado_e_obtenidos();

        call.enqueue(new Callback<List<estado_equipo>>() {
            @Override
            public void onResponse(Call<List<estado_equipo>> call, Response<List<estado_equipo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    estadosEquipos = response.body();
                    actualizarSpinnerEstados(estadosEquipos);
                } else {
                    Toast.makeText(requireContext(), "Error al cargar los estados de equipos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<estado_equipo>> call, Throwable t) {
                Toast.makeText(requireContext(), "Error de red al cargar los estados de equipos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarSpinnertipos(List<tipo_equipo> tiposEquipos) {
        List<String> nombresTiposEquipos = new ArrayList<>();
        for (tipo_equipo tipo : tiposEquipos) {
            nombresTiposEquipos.add(tipo.getDescripcion());
        }

        tiposEquiposAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nombresTiposEquipos);
        tiposEquiposAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tipoEquipoSpinner.setAdapter(tiposEquiposAdapter);
    }

    private void actualizarSpinnerEstados(List<estado_equipo> estadosEquipos) {
        List<String> nombresEstadosEquipos = new ArrayList<>();
        for (estado_equipo estado : estadosEquipos) {
            nombresEstadosEquipos.add(estado.getDescripcion());
        }

        estadosEquiposAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nombresEstadosEquipos);
        estadosEquiposAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        estadoEquipoSpinner.setAdapter(estadosEquiposAdapter);

        estadoEquipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int idEstadoSeleccionado = estadosEquipos.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private void crearEquipo() {
        String tipoEquipo = tipoEquipoSpinner.getSelectedItem().toString();
        String estadoEquipo = estadoEquipoSpinner.getSelectedItem().toString();
        String serial = ((EditText) requireView().findViewById(R.id.serial)).getText().toString();
        String referencia = ((EditText) requireView().findViewById(R.id.referencia)).getText().toString();
        String codigo = ((EditText) requireView().findViewById(R.id.codigo)).getText().toString();


            equipos newEquipo = new equipos();
            for (tipo_equipo tipo : tiposEquipos) {
                if (tipo.getDescripcion().toLowerCase().equals(tipoEquipo.toLowerCase())) {
                    newEquipo.tipo = tipo.getId();
                    break;
                }
            }

            for (estado_equipo estado : estadosEquipos) {
                if (estado.getDescripcion().toLowerCase().equals(estadoEquipo.toLowerCase())) {
                    newEquipo.estado = estado.getId();
                    break;
                }
            }


            newEquipo.setSerial(serial);
            newEquipo.setReferencia(referencia);
            newEquipo.setCodigo(codigo);

            Call<equipos> call = userAPI.createequipo(newEquipo);

            call.enqueue(new Callback<equipos>() {
                @Override
                public void onResponse(Call<equipos> call, Response<equipos> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(requireContext(), "Equipo creado exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Error al crear el equipo", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<equipos> call, Throwable t) {
                    Toast.makeText(requireContext(), "Error de red al crear el equipo", Toast.LENGTH_SHORT).show();
                }
            });

    }
}

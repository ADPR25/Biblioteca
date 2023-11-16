package com.example.biblioteca.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.biblioteca.R;
import com.example.biblioteca.UserAPI;
import com.example.biblioteca.databinding.FragmentSlideshowBinding;
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

        EditText tipo_de_equipo = root.findViewById(R.id.tipo_de_equipo);
        Button crear_e = root.findViewById(R.id.crear_e);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserAPI.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userAPI = retrofit.create(UserAPI.class);

        // Dentro del método onClick
        crear_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tipo_e_value = tipo_de_equipo.getText().toString();
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
                        // Maneja el error de la solicitud aquí
                    }
                });
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
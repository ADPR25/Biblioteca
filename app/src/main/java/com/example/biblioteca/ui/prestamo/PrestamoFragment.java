package com.example.biblioteca.ui.prestamo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.biblioteca.R;
import com.example.biblioteca.databinding.FragmentPrestamoBinding;
import com.example.biblioteca.modelos.prestamo;
import com.example.biblioteca.modelos.tipo_equipo;
import com.example.biblioteca.UserAPI;


import java.util.ArrayList;
import java.util.List;


import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrestamoFragment extends Fragment {

    private FragmentPrestamoBinding binding;
    private EditText startDateEditText, startTimeEditText, endDateEditText, endTimeEditText;

    private Spinner tipoEquipoSpinner;

    private List<tipo_equipo> tiposEquipos;

    private ArrayAdapter<String> tiposEquiposAdapter;

    private UserAPI userAPI;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PrestamoViewModel homeViewModel =
                new ViewModelProvider(this).get(PrestamoViewModel.class);

        binding = FragmentPrestamoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtener referencias a los elementos en el diseño
        startDateEditText = root.findViewById(R.id.startDate);
        startTimeEditText = root.findViewById(R.id.startTime);
        endDateEditText = root.findViewById(R.id.endDate);
        endTimeEditText = root.findViewById(R.id.endTime);
        Button crearPrestamoButton = root.findViewById(R.id.crear_e);
        EditText cantidad = root.findViewById(R.id.cantidad);

        // Configurar el OnClickListener para la fecha de inicio
        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(startDateEditText);
            }
        });

        // Configurar el OnClickListener para la hora de inicio
        startTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(startTimeEditText);
            }
        });

        // Configurar el OnClickListener para la fecha final
        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(endDateEditText);
            }
        });

        // Configurar el OnClickListener para la hora final
        endTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(endTimeEditText);
            }
        });

        tipoEquipoSpinner = root.findViewById(R.id.tipo_de_equipo);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserAPI.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userAPI = retrofit.create(UserAPI.class);

        cargarTiposEquipos();


        // En tu método onCreateView o donde sea apropiado
        crearPrestamoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos de texto y spinner
                String fechaInicio = startDateEditText.getText().toString() + " " + startTimeEditText.getText().toString();
                String fechaFinal = endDateEditText.getText().toString() + " " + endTimeEditText.getText().toString();
                String tipoEquipo = tipoEquipoSpinner.getSelectedItem().toString();


                // Crear un objeto prestamo con los datos
                prestamo nuevoPrestamo = new prestamo();
                nuevoPrestamo.setFecha_prestamo(fechaInicio);
                nuevoPrestamo.setFecha_devolucion(fechaFinal);
                nuevoPrestamo.setTipo_equipo(tipoEquipo);
                nuevoPrestamo.setCantidad(cantidad);

                // Hacer la llamada a la API para crear el préstamo
                Call<prestamo> call = userAPI.createprestamo(nuevoPrestamo);

                call.enqueue(new Callback<prestamo>() {
                    @Override
                    public void onResponse(Call<prestamo> call, Response<prestamo> response) {
                        if (response.isSuccessful()) {
                            // La creación del préstamo fue exitosa
                            Toast.makeText(requireContext(), "Préstamo creado exitosamente", Toast.LENGTH_SHORT).show();
                        } else {
                            // Hubo un problema en la respuesta
                            Toast.makeText(requireContext(), "Error al crear el préstamo", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<prestamo> call, Throwable t) {
                        // Hubo un error en la red
                        Toast.makeText(requireContext(), "Error de red al crear el préstamo", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        return root;
    }

    private void showDatePickerDialog(final EditText dateEditText) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getParentFragment().getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = String.format(Locale.US, "%02d/%02d/%04d", dayOfMonth, (monthOfYear + 1), year);
                        dateEditText.setText(selectedDate);
                    }
                },
                mYear,
                mMonth,
                mDay
        );

        datePickerDialog.show();
    }

    private void showTimePickerDialog(final EditText timeEditText) {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getParentFragment().getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String selectedTime = String.format(Locale.US, "%02d:%02d", hourOfDay, minute);
                        timeEditText.setText(selectedTime);
                    }
                },
                mHour,
                mMinute,
                false
        );

        timePickerDialog.show();
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

    private void actualizarSpinnertipos(List<tipo_equipo> tiposEquipos) {
        List<String> nombresTiposEquipos = new ArrayList<>();
        for (tipo_equipo tipo : tiposEquipos) {
            nombresTiposEquipos.add(tipo.getDescripcion());
        }

        tiposEquiposAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nombresTiposEquipos);
        tiposEquiposAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tipoEquipoSpinner.setAdapter(tiposEquiposAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

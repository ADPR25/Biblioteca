package com.example.biblioteca.ui.prestamo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.biblioteca.R;
import com.example.biblioteca.databinding.FragmentPrestamoBinding;
import com.example.biblioteca.modelos.prestamo;
import com.example.biblioteca.modelos.tipo_equipo;
import com.example.biblioteca.UserAPI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    private ArrayAdapter<String> tiposEquiposAdapter;
    private UserAPI userAPI;
    private List<tipo_equipo> tiposEquipos;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_prestamo, container, false);

        startDateEditText = root.findViewById(R.id.startDate);
        startTimeEditText = root.findViewById(R.id.startTime);
        endDateEditText = root.findViewById(R.id.endDate);
        endTimeEditText = root.findViewById(R.id.endTime);
        Button crearPrestamoButton = root.findViewById(R.id.crear_e);
        EditText cantidadText = root.findViewById(R.id.cantidad);
        tipoEquipoSpinner = root.findViewById(R.id.tipo_de_equipo);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserAPI.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userAPI = retrofit.create(UserAPI.class);

        cargarTiposEquipos();

        startDateEditText.setOnClickListener(v -> showDatePickerDialog(startDateEditText));
        startTimeEditText.setOnClickListener(v -> showTimePickerDialog(startTimeEditText));
        endDateEditText.setOnClickListener(v -> showDatePickerDialog(endDateEditText));
        endTimeEditText.setOnClickListener(v -> showTimePickerDialog(endTimeEditText));
        crearPrestamoButton.setOnClickListener(v -> crearPrestamo(Integer.parseInt(cantidadText.getText().toString())));

        return root;
    }

    private void showDatePickerDialog(final EditText dateEditText) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    String selectedDate = String.format(Locale.US, "%02d/%02d/%04d", dayOfMonth, (monthOfYear + 1), year);
                    dateEditText.setText(selectedDate);
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
                requireContext(),
                (view, hourOfDay, minute) -> {
                    String selectedTime = String.format(Locale.US, "%02d:%02d", hourOfDay, minute);
                    timeEditText.setText(selectedTime);
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
                    actualizarSpinnerTipos(tiposEquipos);
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

    private void actualizarSpinnerTipos(List<tipo_equipo> tiposEquipos) {
        List<String> nombresTiposEquipos = new ArrayList<>();
        for (tipo_equipo tipo : tiposEquipos) {
            nombresTiposEquipos.add(tipo.getDescripcion());
        }

        tiposEquiposAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nombresTiposEquipos);
        tiposEquiposAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tipoEquipoSpinner.setAdapter(tiposEquiposAdapter);
    }

    private void crearPrestamo(int cantidad) {
        String fechaInicioInput = startDateEditText.getText().toString();
        String horaInicioInput = startTimeEditText.getText().toString();
        String fechaFinalInput = endDateEditText.getText().toString();
        String horaFinalInput = endTimeEditText.getText().toString();
        String tipoSeleccionado = tipoEquipoSpinner.getSelectedItem().toString();

        if (fechaInicioInput.isEmpty() || horaInicioInput.isEmpty() || fechaFinalInput.isEmpty() || horaFinalInput.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }


        Date fechaHoraInicioStr = formatearFechaHora(fechaInicioInput, horaInicioInput);
        Date fechaHoraFinalStr = formatearFechaHora(fechaFinalInput, horaFinalInput);
        prestamo nuevoPrestamo = new prestamo();
        nuevoPrestamo.setFechaInicio(fechaHoraInicioStr);
        nuevoPrestamo.setFechaFinal(fechaHoraFinalStr);

        for (tipo_equipo tipo : tiposEquipos) {
            if (tipo.getDescripcion().toLowerCase().equals(tipoEquipoSpinner.getSelectedItem())) {
                Log.d("tipoid","tipo"+tipo.getId());
                nuevoPrestamo.tipo = tipo.getId();
                break;
            }
        }


        nuevoPrestamo.setCantidad(cantidad);

        Call<prestamo> call = userAPI.createprestamo(nuevoPrestamo);
        call.enqueue(new Callback<prestamo>() {
            @Override
            public void onResponse(Call<prestamo> call, Response<prestamo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(requireContext(), "Préstamo creado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Error al crear el préstamo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<prestamo> call, Throwable t) {
                Toast.makeText(requireContext(), "Error de red al crear el préstamo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Date formatearFechaHora(String fecha, String hora) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String fechaHoraStr = fecha + " " + hora;
        try {
            return dateFormat.parse(fechaHoraStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

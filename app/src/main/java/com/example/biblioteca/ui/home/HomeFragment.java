package com.example.biblioteca.ui.home;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.biblioteca.R;
import com.example.biblioteca.base_de_datos;
import com.example.biblioteca.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Spinner tipo_equipo = root.findViewById(R.id.tipo_e);
        EditText cedula = root.findViewById(R.id.cedula);
        EditText cantidad = root.findViewById(R.id.cantidad_equipos);
        EditText fecha_prestamo = root.findViewById(R.id.fecha_prestamo);
        EditText fecha_devolucion = root.findViewById(R.id.fecha_devolucion);
        EditText hora_prestamo = root.findViewById(R.id.hora_prestamo);
        EditText hora_devolucion = root.findViewById(R.id.hora_devolucion);
        Button prestar = root.findViewById(R.id.prestar_button);
        Button cancelar = root.findViewById(R.id.cancelar_button);

        prestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipo_equipovalue = tipo_equipo.getSelectedItem().toString();
                String cedulavalue = cedula.getText().toString();
                String cantidadvalue = cantidad.getText().toString();
                String fecha_prestamovalue = fecha_prestamo.getText().toString();
                String fecha_devolucionvalue = fecha_devolucion.getText().toString();
                String hora_prestamovalue = hora_prestamo.getText().toString();
                String hora_devolucionvalue = hora_devolucion.getText().toString();


                base_de_datos dbHelper = new base_de_datos(requireContext(), "base_de_datos", 1);

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("tipo_equipo", tipo_equipovalue);
                values.put("cedula", cedulavalue);
                values.put("cantidad", cantidadvalue);
                values.put("fecha_prestamo", fecha_prestamovalue);
                values.put("fecha_devolucion", fecha_devolucionvalue);
                values.put("hora_prestamo", hora_prestamovalue);
                values.put("hora_devolucion", hora_devolucionvalue);

                long newRowId = db.insert("prestamo", null, values);
                db.close();

                if (newRowId != -1) {
                    cedula.setText("");
                    cantidad.setText("");
                    fecha_prestamo.setText("");
                    fecha_devolucion.setText("");
                    hora_devolucion.setText("");
                    hora_prestamo.setText("");
                    Toast.makeText(requireContext(), "Datos guardados exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Error al guardar los datos", Toast.LENGTH_SHORT).show();
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
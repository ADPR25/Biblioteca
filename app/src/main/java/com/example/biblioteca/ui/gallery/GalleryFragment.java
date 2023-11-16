package com.example.biblioteca.ui.gallery;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.biblioteca.R;
import com.example.biblioteca.base_de_datos;
import com.example.biblioteca.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {


    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Spinner tipo_equipo = root.findViewById(R.id.tipo_e);
        Spinner estado = root.findViewById(R.id.estado);
        TextView txt = root.findViewById(R.id.selected2);
        ImageView img = root.findViewById(R.id.imageView2);
        ScrollView clase = root.findViewById(R.id.contenedor);
        Button borrar = root.findViewById(R.id.button2);
        Button guardar = root.findViewById(R.id.button);
        EditText serial = root.findViewById(R.id.serial);
        EditText referencia = root.findViewById(R.id.referencia);
        EditText codigo = root.findViewById(R.id.codigo);
        tipo_equipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        clase.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        clase.setVisibility(View.VISIBLE);
                        txt.setText(parent.getSelectedItem().toString());
                        img.setImageResource(R.drawable.video2);
                        break;
                    case 2:
                        clase.setVisibility(View.VISIBLE);
                        txt.setText(parent.getSelectedItem().toString());
                        img.setImageResource(R.drawable.pc);
                        break;
                    case 3:
                        clase.setVisibility(View.VISIBLE);
                        txt.setText(parent.getSelectedItem().toString());
                        img.setImageResource(R.drawable.images);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serial.setText(null);
                referencia.setText(null);
                codigo.setText(null);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipo_equipovalue = tipo_equipo.getSelectedItem().toString();
                String serialValue = serial.getText().toString();
                String referenciaValue = referencia.getText().toString();
                String codigoValue = codigo.getText().toString();
                String estadoValue = estado.getSelectedItem().toString();

                base_de_datos dbHelper = new base_de_datos(requireContext(), "base_de_datos", 1);

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("tipo_equipo", tipo_equipovalue);
                values.put("serial", serialValue);
                values.put("descripcion", referenciaValue);
                values.put("codigo", codigoValue);
                values.put("estado", estadoValue);
                long newRowId = db.insert("equipos", null, values);
                db.close();

                if (newRowId != -1) {
                    serial.setText("");
                    referencia.setText("");
                    codigo.setText("");
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

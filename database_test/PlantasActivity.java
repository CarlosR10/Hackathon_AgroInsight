package com.example.database_test;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PlantasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantas);

        ImageView imageView = findViewById(R.id.imageViewPlanta);
        TextView textViewNombre = findViewById(R.id.textViewNombrePlanta);
        TextView textViewFecha = findViewById(R.id.textViewFechaPlanta);
        TextView textViewTipoPlaga = findViewById(R.id.textViewTipoPlaga);  // Añadir TextView para tipo_plaga

        // Obtener los datos de la planta desde el Intent
        int idPlanta = getIntent().getIntExtra("idPlanta", -1);
        String nombreComun = getIntent().getStringExtra("nombreComun");
        byte[] imagen = getIntent().getByteArrayExtra("imagen");
        String fechaGuardado = getIntent().getStringExtra("fechaGuardado");
        String tipoPlaga = getIntent().getStringExtra("tipoPlaga");

        // Mostrar los datos de la planta en la interfaz de usuario
        if (imagen != null && imagen.length > 0) {
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(imagen, 0, imagen.length));
        } else {
            imageView.setImageResource(R.drawable.ic_arrow);  // Imagen de recurso predeterminado si no hay imagen
        }
        textViewNombre.setText(nombreComun);
        textViewFecha.setText(fechaGuardado);
        textViewTipoPlaga.setText(tipoPlaga);  // Mostrar tipo_plaga
    }
}

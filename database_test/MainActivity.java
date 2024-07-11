package com.example.database_test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.database_test.DatabaseManager.TABLA_PLANTAS;
import static com.example.database_test.DatabaseManager.COLUMNA_ID_PLANTA;
import static com.example.database_test.DatabaseManager.COLUMNA_NOMBRE_COMUN;
import static com.example.database_test.DatabaseManager.COLUMNA_IMAGEN;
import static com.example.database_test.DatabaseManager.COLUMNA_FECHA_GUARDADO;
import static com.example.database_test.DatabaseManager.COLUMNA_TIPO_PLAGA;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    public static final String PREFS_NAME = "MyPrefs";
    public static final String PREF_USER_ID = "userId";
    private PlantaAdapter adapter;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DatabaseManager(this);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        TextView homeOption = findViewById(R.id.homeOption);
        TextView plagasOption = findViewById(R.id.plagasOption);
        TextView nombreUsuario = findViewById(R.id.nombre_usuario);
        LinearLayout perfilLayout = findViewById(R.id.Perfil);
        Spinner filtroSpinner = findViewById(R.id.filtro_carrusel);
        RecyclerView listViewPlantas = findViewById(R.id.ListaPlantas);

        // Establecer homeOption como seleccionado por defecto
        homeOption.setSelected(true);

        FuncionesBasicas.setupNavigationButtons(this, homeOption, plagasOption);

        // Mostrar el nombre del usuario
        FuncionesBasicas.mostrarNombreUsuario(this, sharedPreferences, nombreUsuario);

        // Configurar el PopupMenu para el perfil
        perfilLayout.setOnClickListener(v -> FuncionesBasicas.mostrarMenuPerfil(MainActivity.this, v, sharedPreferences));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREF_USER_ID, 1);
        editor.apply();

        List<Planta> plantas = obtenerPlantas();
        adapter = new PlantaAdapter(this, plantas);
        listViewPlantas.setAdapter(adapter);

        // Configurar el RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listViewPlantas.setLayoutManager(layoutManager);

        // Configurar el Spinner con las opciones
        List<String> opciones = new ArrayList<>();
        opciones.add(getString(R.string.spinner_filtro));
        opciones.add(getString(R.string.spinner_alfabetico));
        opciones.add(getString(R.string.spinner_mas_viejo));
        opciones.add(getString(R.string.spinner_mas_nuevo));

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filtroSpinner.setAdapter(spinnerAdapter);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(listViewPlantas);
    }

    private List<Planta> obtenerPlantas() {
        List<Planta> plantas = new ArrayList<>();
        SQLiteDatabase db = dbManager.getReadableDatabase();
        Cursor cursor = db.query(TABLA_PLANTAS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int idPlanta = cursor.getInt(cursor.getColumnIndex(COLUMNA_ID_PLANTA));
                @SuppressLint("Range") String nombreComun = cursor.getString(cursor.getColumnIndex(COLUMNA_NOMBRE_COMUN));
                @SuppressLint("Range") byte[] imagen = cursor.getBlob(cursor.getColumnIndex(COLUMNA_IMAGEN));
                @SuppressLint("Range") String fechaGuardado = cursor.getString(cursor.getColumnIndex(COLUMNA_FECHA_GUARDADO));
                @SuppressLint("Range") String tipoPlaga = cursor.getString(cursor.getColumnIndex(COLUMNA_TIPO_PLAGA));

                plantas.add(new Planta(idPlanta, nombreComun, imagen, fechaGuardado, tipoPlaga));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return plantas;
    }
}

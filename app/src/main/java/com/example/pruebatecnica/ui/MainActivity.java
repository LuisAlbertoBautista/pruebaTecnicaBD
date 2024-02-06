package com.example.pruebatecnica.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pruebatecnica.databinding.ActivityMainBinding;
import com.example.pruebatecnica.ui.catalogo.viewmodels.CatalogoViewModel;
import com.example.pruebatecnica.ui.consulta.Consulta;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private CatalogoViewModel catalogoViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        catalogoViewModel = new ViewModelProvider(this).get(CatalogoViewModel.class);

        binding.btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catalogoViewModel.getCatalogo(MainActivity.this);
            }
        });

        binding.btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goConsultar();
            }
        });

        catalogoViewModel.getSucces().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    mostrarMensajeRapido("Base de datos actualizada con exito");
                else {
                    mostrarMensajeRapido("Ha ocurrido un error al actualizar la base de datos" +
                            "");
                }
            }
        });
    }

    private void mostrarMensajeRapido(String mensaje) {
        // Mostrar el Toast
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    private void goConsultar(){
        Intent intent = new Intent(this, Consulta.class);
        startActivity(intent);
    }

}
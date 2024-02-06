package com.example.pruebatecnica.ui.consulta;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.pruebatecnica.databinding.ActivityConsultaBinding;
import com.example.pruebatecnica.ui.consulta.adapters.CatalogoAdapter;
import com.example.pruebatecnica.ui.consulta.interfaces.EventosClick;
import com.example.pruebatecnica.ui.consulta.models.ItemCatalogo;
import com.example.pruebatecnica.ui.consulta.viewmodels.ConsultaViewModel;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class Consulta extends AppCompatActivity implements EventosClick.ClickElementInterface{
    private ActivityConsultaBinding binding;
    private ConsultaViewModel consultaViewModel;
    private CatalogoAdapter catalogoAdapter;

    private ItemCatalogo itemCatalogo;

    private static final int REQUEST_STORAGE_PERMISSION = 101;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permiso concedido, puedes realizar acciones relacionadas con la escritura en la memoria aquí
                } else {
                    // Permiso denegado, puedes informar al usuario o realizar alguna acción adicional
                }
            });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConsultaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        consultaViewModel = new ViewModelProvider(this).get(ConsultaViewModel.class);

        consultaViewModel.getData(this);

        consultaViewModel.getAbastecimientos().observe(this, new Observer<List>() {
            @Override
            public void onChanged(List list) {
                if (list.size()>0) {
                    catalogoAdapter = new CatalogoAdapter(Consulta.this, list);
                    binding.recycler.setAdapter(catalogoAdapter);
                }else {
                    mostrarMensajeRapido("Descarga primero el catalogo");
                }
            }
        });
        consultaViewModel.getStatusUpdate().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    catalogoAdapter.actualizarLista(consultaViewModel.getAbastecimientos().getValue());
                }
            }
        });
    }

    public void mostrarMensajeRapido(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }
    private void checkExternalStoragePermission() {
        Boolean isPermissionWrite = false;
        checkAndRequestStoragePermission();
        dispatchTakePictureIntent();
    }

    private void checkAndRequestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION:
                // Verificar si el permiso fue concedido
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    // Permiso denegado, puedes informar al usuario o realizar alguna acción adicional
                }
                break;
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("datos", data.getExtras().toString());
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);
            itemCatalogo.setRutaImagen(base64Image);
            consultaViewModel.setUpdateItem(itemCatalogo, Consulta.this);
        }
    }


    @Override
    public void clickCamera(ItemCatalogo itemCatalogo) throws JSONException {
        this.itemCatalogo = itemCatalogo;
        checkExternalStoragePermission();
    }

    @Override
    public void clickElementInterface(String elemnt, ItemCatalogo itemCatalogo) throws JSONException {
        consultaViewModel.setUpdateItem(itemCatalogo, Consulta.this);
    }
}
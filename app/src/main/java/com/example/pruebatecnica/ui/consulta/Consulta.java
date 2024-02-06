package com.example.pruebatecnica.ui.consulta;

import static android.content.ContentValues.TAG;

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
import android.util.Log;

import com.example.pruebatecnica.R;
import com.example.pruebatecnica.databinding.ActivityConsultaBinding;
import com.example.pruebatecnica.databinding.ActivityMainBinding;
import com.example.pruebatecnica.ui.catalogo.viewmodels.CatalogoViewModel;
import com.example.pruebatecnica.ui.consulta.adapters.CatalogoAdapter;
import com.example.pruebatecnica.ui.consulta.interfaces.EventosClick;
import com.example.pruebatecnica.ui.consulta.models.ItemCatalogo;
import com.example.pruebatecnica.ui.consulta.viewmodels.ConsultaViewModel;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Consulta extends AppCompatActivity implements EventosClick.ClickElementInterface{
    private ActivityConsultaBinding binding;
    private ConsultaViewModel consultaViewModel;
    private CatalogoAdapter catalogoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConsultaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initRecyclerView();

        consultaViewModel = new ViewModelProvider(this).get(ConsultaViewModel.class);

        consultaViewModel.getData(this);

        consultaViewModel.getAbastecimientos().observe(this, new Observer<List>() {
            @Override
            public void onChanged(List list) {
                catalogoAdapter = new CatalogoAdapter(Consulta.this, list);
                binding.recycler.setAdapter(catalogoAdapter);
            }
        });
    }

void initRecyclerView(){

}
    private void checkExternalStoragePermission() {
        Boolean isPermissionWrite = false;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Permission not granted WRITE_EXTERNAL_STORAGE.");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // le mandamos algun mensaje al usuario si es necesario pero para este caso de prueba lo dejamos asi
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
                isPermissionWrite = true;
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Permission not granted CAMERA.");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, 226);
                if (isPermissionWrite)
                    dispatchTakePictureIntent();
            }
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
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
        }
    }


    @Override
    public void clickCamera() throws JSONException {
            checkExternalStoragePermission();
    }

    @Override
    public void clickElementInterface(String elemnt, ItemCatalogo itemCatalogo) throws JSONException {

    }
}
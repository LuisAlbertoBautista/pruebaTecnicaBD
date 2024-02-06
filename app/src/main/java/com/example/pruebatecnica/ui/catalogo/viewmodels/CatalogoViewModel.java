package com.example.pruebatecnica.ui.catalogo.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pruebatecnica.preferences.MySettings;
import com.example.pruebatecnica.rest.ServiceApi;
import com.example.pruebatecnica.ui.catalogo.database.AbastecimientoRepository;
import com.example.pruebatecnica.ui.catalogo.models.Abastecimiento;
import com.example.pruebatecnica.ui.catalogo.models.ApiResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CatalogoViewModel extends ViewModel  {

    private Context context;
    private AbastecimientoRepository abastecimientoRepository;
    private MutableLiveData <Boolean> succes =new MutableLiveData<>();


    public void processFinish( JSONObject data, Context context){
        Gson gson = new Gson();
        ApiResponse apiResponse = gson.fromJson(String.valueOf(data), ApiResponse.class);
        List<Abastecimiento> abastecimientos = apiResponse.getAbastecimientoList();
        Log.d("Abastecimiemto", String.valueOf(abastecimientos.size()));
        abastecimientoRepository = new AbastecimientoRepository(context);
        abastecimientoRepository.open();
        if (!MySettings.getBooleanPrefVal(context, "update")){
            MySettings.setBooleanPrefVal(context, "update", true);
            abastecimientoRepository.insertAbastecimientos(abastecimientos);
        }
        else
            abastecimientoRepository.actualizarBaseDeDatos(abastecimientos);
        setSucces(true);
    }
    public LiveData<Boolean> getSucces() {
        return succes;
    }

    public void setSucces(Boolean value) {
        succes.setValue(value);
    }
}

package com.example.pruebatecnica.ui.catalogo.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pruebatecnica.preferences.MySettings;
import com.example.pruebatecnica.rest.ServiceApi;
import com.example.pruebatecnica.ui.catalogo.database.AbastecimientoRepository;
import com.example.pruebatecnica.ui.catalogo.models.ApiResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class CatalogoViewModel extends ViewModel implements ServiceApi.ResponseHttp {
    private ServiceApi serviceApi;
    private Context context;
    private AbastecimientoRepository abastecimientoRepository;
    private MutableLiveData <Boolean> succes =new MutableLiveData<>();
    public void getCatalogo(Context context){
        serviceApi = new ServiceApi(context,true);
        abastecimientoRepository = new AbastecimientoRepository(context);
        this.context = context;
        serviceApi.startConnection("firebase/api/catalogos/Sanit_abastecimiento");

    }

    @Override
    public void processFinish(int code, JSONObject data) throws Exception {
        Gson gson = new Gson();
        ApiResponse apiResponse = gson.fromJson(String.valueOf(data), ApiResponse.class);
        if (!MySettings.getBooleanPrefVal(this.context, "update")){
            MySettings.setBooleanPrefVal(this.context, "update", true);
            abastecimientoRepository.insertAbastecimientos(apiResponse.getAbastecimientoList());
        }
        else
            abastecimientoRepository.actualizarBaseDeDatos(apiResponse.getAbastecimientoList());
        setSucces(true);
    }
    public LiveData<Boolean> getSucces() {
        return succes;
    }

    public void setSucces(Boolean value) {
        succes.setValue(value);
    }

    @Override
    public void onFail() throws JSONException {

    }
}

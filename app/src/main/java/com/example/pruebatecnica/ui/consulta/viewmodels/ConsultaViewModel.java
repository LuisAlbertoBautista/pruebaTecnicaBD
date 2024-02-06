package com.example.pruebatecnica.ui.consulta.viewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pruebatecnica.ui.catalogo.database.AbastecimientoRepository;
import com.example.pruebatecnica.ui.catalogo.models.Abastecimiento;
import com.example.pruebatecnica.ui.consulta.database.ConsultRepository;
import com.example.pruebatecnica.ui.consulta.models.ItemCatalogo;

import java.util.ArrayList;
import java.util.List;

public class ConsultaViewModel extends ViewModel {
    private ConsultRepository consultRepository;
    private AbastecimientoRepository abastecimientoRepository;

    private MutableLiveData <List<ItemCatalogo>> itemCatalogoList = new MutableLiveData<>();
    public void getData(Context context){
        abastecimientoRepository = new AbastecimientoRepository(context);
        List<Abastecimiento> abastecimientos = abastecimientoRepository.getAllAbastecimientos();
        List<ItemCatalogo> itemCatalogos = new ArrayList<>();
        if (abastecimientos.size()> 0) {
            for (Abastecimiento abastecimiento : abastecimientos){
                ItemCatalogo itemCatalogo =  new ItemCatalogo();
                itemCatalogo.setNombre(abastecimiento.getTipoAbastecimiento());
                itemCatalogo.setId(abastecimiento.getIdAbastecimiento());
                itemCatalogos.add(itemCatalogo);
            }
            setUpdateOpcionUserInitial(itemCatalogos);
        }
    }

    private void setUpdateOpcionUserInitial(List<ItemCatalogo> items){
        List< ItemCatalogo> itemCatalogoList = consultRepository.getAllItemCatalogo();
        if (itemCatalogoList.size() > 0){
            this.itemCatalogoList.setValue(itemCatalogoList);
        }else {
            consultRepository.insertOpciones(items);
            this.itemCatalogoList.setValue(items);
        }
    }


    public MutableLiveData<List<ItemCatalogo>> getAbastecimientos() {
        return itemCatalogoList;
    }

    public void setAbastecimientos(MutableLiveData<List<ItemCatalogo>> itemCatalogoList) {
        this.itemCatalogoList = itemCatalogoList;
    }
}

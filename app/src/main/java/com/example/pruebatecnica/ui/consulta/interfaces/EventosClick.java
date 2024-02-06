package com.example.pruebatecnica.ui.consulta.interfaces;

import com.example.pruebatecnica.ui.consulta.models.ItemCatalogo;

import org.json.JSONException;
import org.json.JSONObject;

public interface EventosClick {
        interface ClickElementInterface{
                void clickCamera() throws JSONException;
                void clickElementInterface(String elemnt, ItemCatalogo itemCatalogo) throws JSONException;
        }
}

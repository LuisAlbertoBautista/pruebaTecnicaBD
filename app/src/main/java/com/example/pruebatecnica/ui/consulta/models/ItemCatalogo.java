package com.example.pruebatecnica.ui.consulta.models;

import com.example.pruebatecnica.preferences.MySettings;

public class ItemCatalogo {
    private int Id;
    private String nombre;
    private String opcionSeleccionada = "N/A";
    private String rutaImagen  = MySettings.getIconBase64();

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getOpcionSeleccionada() {
        return opcionSeleccionada;
    }

    public void setOpcionSeleccionada(String opcionSeleccionada) {
        this.opcionSeleccionada = opcionSeleccionada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
}

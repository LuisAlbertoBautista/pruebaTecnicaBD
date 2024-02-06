package com.example.pruebatecnica.ui.catalogo.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {

    @SerializedName("Sanit_abastecimiento")
    private List<Abastecimiento> abastecimientoList;

    @SerializedName("success")
    private int success;

    @SerializedName("message")
    private String message;

    public List<Abastecimiento> getAbastecimientoList() {
        return abastecimientoList;
    }

    public void setAbastecimientoList(List<Abastecimiento> list) {
        this.abastecimientoList = list;
    }

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}


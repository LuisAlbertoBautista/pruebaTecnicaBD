package com.example.pruebatecnica.ui.catalogo.models;

import com.google.gson.annotations.SerializedName;

public class Abastecimiento {

    @SerializedName("idAbastecimiento")
    private int idAbastecimiento;

    @SerializedName("tipoAbastecimiento")
    private String tipoAbastecimiento;

    @SerializedName("usuarioCreacion")
    private String usuarioCreacion;

    @SerializedName("usuarioModificacion")
    private String usuarioModificacion;

    @SerializedName("usuarioEliminacion")
    private String usuarioEliminacion;

    @SerializedName("fechaCreacion")
    private String fechaCreacion;

    @SerializedName("fechaModificacion")
    private String fechaModificacion;

    @SerializedName("fechaEliminacion")
    private String fechaEliminacion;

    public int getIdAbastecimiento() {
        return idAbastecimiento;
    }

    public String getTipoAbastecimiento() {
        return tipoAbastecimiento;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public String getUsuarioEliminacion() {
        return usuarioEliminacion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public String getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setIdAbastecimiento(int idAbastecimiento) {
        this.idAbastecimiento = idAbastecimiento;
    }

    public void setTipoAbastecimiento(String tipoAbastecimiento) {
        this.tipoAbastecimiento = tipoAbastecimiento;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public void setUsuarioEliminacion(String usuarioEliminacion) {
        this.usuarioEliminacion = usuarioEliminacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public void setFechaEliminacion(String fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }
}

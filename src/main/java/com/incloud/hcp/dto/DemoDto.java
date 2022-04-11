package com.incloud.hcp.dto;

public class DemoDto {
    private String accion;
    private String valor;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "DemoDto{" +
                "accion='" + accion + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}

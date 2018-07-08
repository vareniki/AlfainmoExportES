package com.alfainmo.beans;

public class MyInmuebleTipoPuerta extends AbstractPgObject {

    private int inmueble_id;
    private String tipo_puerta_id;
    private String marca_act;

    public int getInmueble_id() {
        return inmueble_id;
    }

    public void setInmueble_id(int inmueble_id) {
        this.inmueble_id = inmueble_id;
    }

    public String getTipo_puerta_id() {
        return tipo_puerta_id;
    }

    public void setTipo_puerta_id(String tipo_puerta_id) {
        this.tipo_puerta_id = tipo_puerta_id;
    }

    public String getMarca_act() {
        return marca_act;
    }

    public void setMarca_act(String marca_act) {
        this.marca_act = marca_act;
    }

}

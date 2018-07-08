package com.alfainmo.beans;

/**
 * Created by dmonje on 18/02/14.
 */
public class MyInmuebleTipoSuelo extends AbstractPgObject {
    private int inmueble_id;
    private String tipo_suelo_id;
    private String marca_act;

    public int getInmueble_id() {
        return inmueble_id;
    }

    public void setInmueble_id(int inmueble_id) {
        this.inmueble_id = inmueble_id;
    }

    public String getTipo_suelo_id() {
        return tipo_suelo_id;
    }

    public void setTipo_suelo_id(String tipo_suelo_id) {
        this.tipo_suelo_id = tipo_suelo_id;
    }

    public String getMarca_act() {
        return marca_act;
    }

    public void setMarca_act(String marca_act) {
        this.marca_act = marca_act;
    }

}

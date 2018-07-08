package com.alfainmo.beans;

import java.util.Date;

/**
 * Created by dmonje on 11/02/14.
 */
public class PgTerrenoDb extends AbstractPgObject {
    private int id;
    private int inmueble_id;
    private String tipo_terreno_id;
    private int kilometro;
    private int numero;
    private int numero_parcela;
    private int area_total;
    private String sector;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInmueble_id() {
        return inmueble_id;
    }

    public void setInmueble_id(int inmueble_id) {
        this.inmueble_id = inmueble_id;
    }

    public String getTipo_terreno_id() {
        return tipo_terreno_id;
    }

    public void setTipo_terreno_id(String tipo_terreno_id) {
        this.tipo_terreno_id = tipo_terreno_id;
    }

    public int getKilometro() {
        return kilometro;
    }

    public void setKilometro(int kilometro) {
        this.kilometro = kilometro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getNumero_parcela() {
        return numero_parcela;
    }

    public void setNumero_parcela(int numero_parcela) {
        this.numero_parcela = numero_parcela;
    }

    public int getArea_total() {
        return area_total;
    }

    public void setArea_total(int area_total) {
        this.area_total = area_total;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}

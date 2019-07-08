package com.alfainmo.beans;

/**
 * Created by dmonje on 11/02/14.
 */
public class MyOtroDb extends AbstractPgObject {
    private int id;
    private int inmueble_id;
    private String tipo_otro_id;
    private int area_total;
    private int anio_construccion;
    private int area_total_util;
    private int altura;
    private String accesible_24h;
    private String vigilado_24h;
    private String zona_carga_descarga;

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

    public int getArea_total() {
        return area_total;
    }

    public void setArea_total(int area_total) {
        this.area_total = area_total;
    }

    public String getTipo_otro_id() {
        return tipo_otro_id;
    }

    public void setTipo_otro_id(String tipo_otro_id) {
        this.tipo_otro_id = tipo_otro_id;
    }

    public int getAnio_construccion() {
        return anio_construccion;
    }

    public void setAnio_construccion(int anio_construccion) {
        this.anio_construccion = anio_construccion;
    }

    public int getArea_total_util() {
        return area_total_util;
    }

    public void setArea_total_util(int area_total_util) {
        this.area_total_util = area_total_util;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public String getAccesible_24h() {
        return accesible_24h;
    }

    public void setAccesible_24h(String accesible_24h) {
        this.accesible_24h = accesible_24h;
    }

    public String getVigilado_24h() {
        return vigilado_24h;
    }

    public void setVigilado_24h(String vigilado_24h) {
        this.vigilado_24h = vigilado_24h;
    }

    public String getZona_carga_descarga() {
        return zona_carga_descarga;
    }

    public void setZona_carga_descarga(String zona_carga_descarga) {
        this.zona_carga_descarga = zona_carga_descarga;
    }
}

package com.alfainmo.beans;

/**
 * Created by dmonje on 11/02/14.
 */
public class PgGarajeDb extends AbstractPgObject {
    private int id;
    private int inmueble_id;
    private String tipo_garaje_id;
    private String estado_conservacion_id;
    private int numero_plazas;
    private int area_total;
    private double gastos_comunidad;
    private String plaza_cubierta;
    private String plaza_doble;
    private String con_ascensor;
    private String con_puerta_automatica;
    private String con_camaras_seguridad;
    private String con_personal_seguridad;
    private String con_alarma;
    private String con_vigilancia_24h;

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

    public String getTipo_garaje_id() {
        return tipo_garaje_id;
    }

    public void setTipo_garaje_id(String tipo_garaje_id) {
        this.tipo_garaje_id = tipo_garaje_id;
    }

    public String getEstado_conservacion_id() {
        return estado_conservacion_id;
    }

    public void setEstado_conservacion_id(String estado_conservacion_id) {
        this.estado_conservacion_id = estado_conservacion_id;
    }

    public int getNumero_plazas() {
        return numero_plazas;
    }

    public void setNumero_plazas(int numero_plazas) {
        this.numero_plazas = numero_plazas;
    }

    public int getArea_total() {
        return area_total;
    }

    public void setArea_total(int area_total) {
        this.area_total = area_total;
    }

    public double getGastos_comunidad() {
        return gastos_comunidad;
    }

    public void setGastos_comunidad(double gastos_comunidad) {
        this.gastos_comunidad = gastos_comunidad;
    }

    public String getPlaza_cubierta() {
        return plaza_cubierta;
    }

    public void setPlaza_cubierta(String plaza_cubierta) {
        this.plaza_cubierta = plaza_cubierta;
    }

    public String getPlaza_doble() {
        return plaza_doble;
    }

    public void setPlaza_doble(String plaza_doble) {
        this.plaza_doble = plaza_doble;
    }

    public String getCon_ascensor() {
        return con_ascensor;
    }

    public void setCon_ascensor(String con_ascensor) {
        this.con_ascensor = con_ascensor;
    }

    public String getCon_puerta_automatica() {
        return con_puerta_automatica;
    }

    public void setCon_puerta_automatica(String con_puerta_automatica) {
        this.con_puerta_automatica = con_puerta_automatica;
    }

    public String getCon_camaras_seguridad() {
        return con_camaras_seguridad;
    }

    public void setCon_camaras_seguridad(String con_camaras_seguridad) {
        this.con_camaras_seguridad = con_camaras_seguridad;
    }

    public String getCon_personal_seguridad() {
        return con_personal_seguridad;
    }

    public void setCon_personal_seguridad(String con_personal_seguridad) {
        this.con_personal_seguridad = con_personal_seguridad;
    }

    public String getCon_alarma() {
        return con_alarma;
    }

    public void setCon_alarma(String con_alarma) {
        this.con_alarma = con_alarma;
    }

    public String getCon_vigilancia_24h() {
        return con_vigilancia_24h;
    }

    public void setCon_vigilancia_24h(String con_vigilancia_24h) {
        this.con_vigilancia_24h = con_vigilancia_24h;
    }
}

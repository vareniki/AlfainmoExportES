package com.alfainmo.beans;

/**
 * Created by dmonje on 11/02/14.
 */
public class PgLocalDb extends AbstractPgObject {
    private int id;
    private int inmueble_id;
    private String bloque;
    private int area_total_construida;
    private int plazas_parking;
    private int numero_aseos;
    private int plantas_edificio;
    private int anio_construccion;
    private double gastos_comunidad;
    private String ultima_oportunidad;
    private String estado_conservacion_id;
    private String tipo_calefaccion_id;
    private String tipo_agua_caliente_id;
    private String tipo_aa_id;
    private String localizacion_local_id;
    private String con_salida_humos;
    private String con_almacen;
    private String con_cocina_equipada;
    private String con_puerta_seguridad;
    private String con_alarma;
    private String calificacion_energetica_id;
    private int indice_energetico;
    private String subtipo_calefaccion;
    private int area_total_util;
    private int m_lineales_fachada;
    private int m_lineales_escaparate;
    private String con_videovigilancia;
    private String con_vigilancia_24h;
    private String con_camaras_seguridad;
    private String puerta;

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

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public int getArea_total_construida() {
        return area_total_construida;
    }

    public void setArea_total_construida(int area_total_construida) {
        this.area_total_construida = area_total_construida;
    }

    public int getPlazas_parking() {
        return plazas_parking;
    }

    public void setPlazas_parking(int plazas_parking) {
        this.plazas_parking = plazas_parking;
    }

    public int getNumero_aseos() {
        return numero_aseos;
    }

    public void setNumero_aseos(int numero_aseos) {
        this.numero_aseos = numero_aseos;
    }

    public int getPlantas_edificio() {
        return plantas_edificio;
    }

    public void setPlantas_edificio(int plantas_edificio) {
        this.plantas_edificio = plantas_edificio;
    }

    public int getAnio_construccion() {
        return anio_construccion;
    }

    public void setAnio_construccion(int anio_construccion) {
        this.anio_construccion = anio_construccion;
    }

    public double getGastos_comunidad() {
        return gastos_comunidad;
    }

    public void setGastos_comunidad(double gastos_comunidad) {
        this.gastos_comunidad = gastos_comunidad;
    }

    public String getUltima_oportunidad() {
        return ultima_oportunidad;
    }

    public void setUltima_oportunidad(String ultima_oportunidad) {
        this.ultima_oportunidad = ultima_oportunidad;
    }

    public String getEstado_conservacion_id() {
        return estado_conservacion_id;
    }

    public void setEstado_conservacion_id(String estado_conservacion_id) {
        this.estado_conservacion_id = estado_conservacion_id;
    }

    public String getTipo_calefaccion_id() {
        return tipo_calefaccion_id;
    }

    public void setTipo_calefaccion_id(String tipo_calefaccion_id) {
        this.tipo_calefaccion_id = tipo_calefaccion_id;
    }

    public String getTipo_agua_caliente_id() {
        return tipo_agua_caliente_id;
    }

    public void setTipo_agua_caliente_id(String tipo_agua_caliente_id) {
        this.tipo_agua_caliente_id = tipo_agua_caliente_id;
    }

    public String getTipo_aa_id() {
        return tipo_aa_id;
    }

    public void setTipo_aa_id(String tipo_aa_id) {
        this.tipo_aa_id = tipo_aa_id;
    }

    public String getLocalizacion_local_id() {
        return localizacion_local_id;
    }

    public void setLocalizacion_local_id(String localizacion_local_id) {
        this.localizacion_local_id = localizacion_local_id;
    }

    public String getCon_salida_humos() {
        return con_salida_humos;
    }

    public void setCon_salida_humos(String con_salida_humos) {
        this.con_salida_humos = con_salida_humos;
    }

    public String getCon_almacen() {
        return con_almacen;
    }

    public void setCon_almacen(String con_almacen) {
        this.con_almacen = con_almacen;
    }

    public String getCon_cocina_equipada() {
        return con_cocina_equipada;
    }

    public void setCon_cocina_equipada(String con_cocina_equipada) {
        this.con_cocina_equipada = con_cocina_equipada;
    }

    public String getCon_puerta_seguridad() {
        return con_puerta_seguridad;
    }

    public void setCon_puerta_seguridad(String con_puerta_seguridad) {
        this.con_puerta_seguridad = con_puerta_seguridad;
    }

    public String getCon_alarma() {
        return con_alarma;
    }

    public void setCon_alarma(String con_alarma) {
        this.con_alarma = con_alarma;
    }

    public String getCalificacion_energetica_id() {
        return calificacion_energetica_id;
    }

    public void setCalificacion_energetica_id(String calificacion_energetica_id) {
        this.calificacion_energetica_id = calificacion_energetica_id;
    }

    public int getIndice_energetico() {
        return indice_energetico;
    }

    public void setIndice_energetico(int indice_energetico) {
        this.indice_energetico = indice_energetico;
    }

    public String getSubtipo_calefaccion() {
        return subtipo_calefaccion;
    }

    public void setSubtipo_calefaccion(String subtipo_calefaccion) {
        this.subtipo_calefaccion = subtipo_calefaccion;
    }

    public int getArea_total_util() {
        return area_total_util;
    }

    public void setArea_total_util(int area_total_util) {
        this.area_total_util = area_total_util;
    }

    public int getM_lineales_fachada() {
        return m_lineales_fachada;
    }

    public void setM_lineales_fachada(int m_lineales_fachada) {
        this.m_lineales_fachada = m_lineales_fachada;
    }

    public int getM_lineales_escaparate() {
        return m_lineales_escaparate;
    }

    public void setM_lineales_escaparate(int m_lineales_escaparate) {
        this.m_lineales_escaparate = m_lineales_escaparate;
    }

    public String getCon_videovigilancia() {
        return con_videovigilancia;
    }

    public void setCon_videovigilancia(String con_videovigilancia) {
        this.con_videovigilancia = con_videovigilancia;
    }

    public String getCon_vigilancia_24h() {
        return con_vigilancia_24h;
    }

    public void setCon_vigilancia_24h(String con_vigilancia_24h) {
        this.con_vigilancia_24h = con_vigilancia_24h;
    }

    public String getCon_camaras_seguridad() {
        return con_camaras_seguridad;
    }

    public void setCon_camaras_seguridad(String con_camaras_seguridad) {
        this.con_camaras_seguridad = con_camaras_seguridad;
    }

    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }
}

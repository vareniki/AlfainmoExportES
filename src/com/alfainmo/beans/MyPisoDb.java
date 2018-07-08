package com.alfainmo.beans;

/**
 * Created by dmonje on 11/02/14.
 */
public class MyPisoDb extends AbstractPgObject {
    private int id;
    private int inmueble_id;
    private String tipo_piso_id;
    private int plantas_edificio;
    private String bloque;
    private String escalera;
    private String piso;
    private String puerta;
    private String urbanizacion;
    private String estado_conservacion_id;
    private int numero_habitaciones;
    private int numero_banos;
    private int area_total_construida;
    private int area_salon;
    private double gastos_comunidad;
    private int numero_armarios;
    private int area_terraza;
    private String con_parking;
    private String tipo_calefaccion_id;
    private String tipo_agua_caliente_id;
    private int anio_construccion;
    private String tipo_aa_id;
    private String interior_exterior_id;
    private String tipo_orientacion_id;
    private String tipo_equipamiento_id;
    private String tipo_tendedero_id;
    private String con_piscina;
    private String con_areas_verdes;
    private String con_conserje;
    private String con_trastero;
    private String con_puerta_seguridad;
    private String con_alarma;
    private String con_tenis;
    private String con_squash;
    private String con_futbol;
    private String con_baloncesto;
    private String con_gimnasio;
    private String con_padel;
    private String con_golf;
    private String con_zonas_infantiles;
    private String calificacion_energetica_id;
    private String subtipo_calefaccion;
    private int area_total_util;
    private int numero_aseos;
    private String con_camaras_seguridad;
    private String con_vigilancia_24h;
    private String es_vpo;
    private int indice_energetico;
    private int plazas_parking;
    private int numero_ascensores;


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

    public String getTipo_piso_id() {
        return tipo_piso_id;
    }

    public void setTipo_piso_id(String tipo_piso_id) {
        this.tipo_piso_id = tipo_piso_id;
    }

    public int getPlantas_edificio() {
        return plantas_edificio;
    }

    public void setPlantas_edificio(int plantas_edificio) {
        this.plantas_edificio = plantas_edificio;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public String getEscalera() {
        return escalera;
    }

    public void setEscalera(String escalera) {
        this.escalera = escalera;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }

    public String getUrbanizacion() {
        return urbanizacion;
    }

    public void setUrbanizacion(String urbanizacion) {
        this.urbanizacion = urbanizacion;
    }

    public String getEstado_conservacion_id() {
        return estado_conservacion_id;
    }

    public void setEstado_conservacion_id(String estado_conservacion_id) {
        this.estado_conservacion_id = estado_conservacion_id;
    }

    public int getNumero_habitaciones() {
        return numero_habitaciones;
    }

    public void setNumero_habitaciones(int numero_habitaciones) {
        this.numero_habitaciones = numero_habitaciones;
    }

    public int getNumero_banos() {
        return numero_banos;
    }

    public void setNumero_banos(int numero_banos) {
        this.numero_banos = numero_banos;
    }

    public int getArea_total_construida() {
        return area_total_construida;
    }

    public void setArea_total_construida(int area_total_construida) {
        this.area_total_construida = area_total_construida;
    }

    public int getArea_salon() {
        return area_salon;
    }

    public void setArea_salon(int area_salon) {
        this.area_salon = area_salon;
    }

    public double getGastos_comunidad() {
        return gastos_comunidad;
    }

    public void setGastos_comunidad(double gastos_comunidad) {
        this.gastos_comunidad = gastos_comunidad;
    }

    public int getNumero_armarios() {
        return numero_armarios;
    }

    public void setNumero_armarios(int numero_armarios) {
        this.numero_armarios = numero_armarios;
    }

    public int getArea_terraza() {
        return area_terraza;
    }

    public void setArea_terraza(int area_terraza) {
        this.area_terraza = area_terraza;
    }

    public String getCon_parking() {
        return con_parking;
    }

    public void setCon_parking(String con_parking) {
        this.con_parking = con_parking;
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

    public int getAnio_construccion() {
        return anio_construccion;
    }

    public void setAnio_construccion(int anio_construccion) {
        this.anio_construccion = anio_construccion;
    }

    public String getTipo_aa_id() {
        return tipo_aa_id;
    }

    public void setTipo_aa_id(String tipo_aa_id) {
        this.tipo_aa_id = tipo_aa_id;
    }

    public String getInterior_exterior_id() {
        return interior_exterior_id;
    }

    public void setInterior_exterior_id(String interior_exterior_id) {
        this.interior_exterior_id = interior_exterior_id;
    }

    public String getTipo_orientacion_id() {
        return tipo_orientacion_id;
    }

    public void setTipo_orientacion_id(String tipo_orientacion_id) {
        this.tipo_orientacion_id = tipo_orientacion_id;
    }

    public String getTipo_equipamiento_id() {
        return tipo_equipamiento_id;
    }

    public void setTipo_equipamiento_id(String tipo_equipamiento_id) {
        this.tipo_equipamiento_id = tipo_equipamiento_id;
    }

    public String getTipo_tendedero_id() {
        return tipo_tendedero_id;
    }

    public void setTipo_tendedero_id(String tipo_tendedero_id) {
        this.tipo_tendedero_id = tipo_tendedero_id;
    }

    public String getCon_piscina() {
        return con_piscina;
    }

    public void setCon_piscina(String con_piscina) {
        this.con_piscina = con_piscina;
    }

    public String getCon_areas_verdes() {
        return con_areas_verdes;
    }

    public void setCon_areas_verdes(String con_areas_verdes) {
        this.con_areas_verdes = con_areas_verdes;
    }

    public String getCon_conserje() {
        return con_conserje;
    }

    public void setCon_conserje(String con_conserje) {
        this.con_conserje = con_conserje;
    }

    public String getCon_trastero() {
        return con_trastero;
    }

    public void setCon_trastero(String con_trastero) {
        this.con_trastero = con_trastero;
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

    public String getCon_tenis() {
        return con_tenis;
    }

    public void setCon_tenis(String con_tenis) {
        this.con_tenis = con_tenis;
    }

    public String getCon_squash() {
        return con_squash;
    }

    public void setCon_squash(String con_squash) {
        this.con_squash = con_squash;
    }

    public String getCon_futbol() {
        return con_futbol;
    }

    public void setCon_futbol(String con_futbol) {
        this.con_futbol = con_futbol;
    }

    public String getCon_baloncesto() {
        return con_baloncesto;
    }

    public void setCon_baloncesto(String con_baloncesto) {
        this.con_baloncesto = con_baloncesto;
    }

    public String getCon_gimnasio() {
        return con_gimnasio;
    }

    public void setCon_gimnasio(String con_gimnasio) {
        this.con_gimnasio = con_gimnasio;
    }

    public String getCon_padel() {
        return con_padel;
    }

    public void setCon_padel(String con_padel) {
        this.con_padel = con_padel;
    }

    public String getCon_golf() {
        return con_golf;
    }

    public void setCon_golf(String con_golf) {
        this.con_golf = con_golf;
    }

    public String getCon_zonas_infantiles() {
        return con_zonas_infantiles;
    }

    public void setCon_zonas_infantiles(String con_zonas_infantiles) {
        this.con_zonas_infantiles = con_zonas_infantiles;
    }

    public String getCalificacion_energetica_id() {
        return calificacion_energetica_id;
    }

    public void setCalificacion_energetica_id(String calificacion_energetica_id) {
        this.calificacion_energetica_id = calificacion_energetica_id;
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

    public int getNumero_aseos() {
        return numero_aseos;
    }

    public void setNumero_aseos(int numero_aseos) {
        this.numero_aseos = numero_aseos;
    }

    public String getCon_camaras_seguridad() {
        return con_camaras_seguridad;
    }

    public void setCon_camaras_seguridad(String con_camaras_seguridad) {
        this.con_camaras_seguridad = con_camaras_seguridad;
    }

    public String getCon_vigilancia_24h() {
        return con_vigilancia_24h;
    }

    public void setCon_vigilancia_24h(String con_vigilancia_24h) {
        this.con_vigilancia_24h = con_vigilancia_24h;
    }

    public String getEs_vpo() {
        return es_vpo;
    }

    public void setEs_vpo(String es_vpo) {
        this.es_vpo = es_vpo;
    }

    public int getIndice_energetico() {
        return indice_energetico;
    }

    public void setIndice_energetico(int indice_energetico) {
        this.indice_energetico = indice_energetico;
    }

    public int getPlazas_parking() {
        return plazas_parking;
    }

    public void setPlazas_parking(int plazas_parking) {
        this.plazas_parking = plazas_parking;
    }

    public int getNumero_ascensores() {
        return numero_ascensores;
    }

    public void setNumero_ascensores(int numero_ascensores) {
        this.numero_ascensores = numero_ascensores;
    }
}

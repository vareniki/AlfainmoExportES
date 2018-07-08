package com.alfainmo.beans;

import java.util.Date;

/**
 * @author dmonje
 */
public class MyInmuebleDb extends AbstractPgObject {
    private int id;
    private int agencia_id;
    private int numero_agencia;
    private int codigo;
    private String tipo_inmueble_id;
    private String es_venta;
    private String es_alquiler;
    private String es_traspaso;
    private String es_opcion_compra;
    private String es_promocion;
    private String nombre_promocion;
    private String entrega_promocion;

    private double precio_venta;
    private double precio_alquiler;
    private double precio_traspaso;
    private double precio_venta_ini;
    private double precio_alquiler_ini;
    private double precio_traspaso_ini;
    private double precio_particular;
    private String moneda_id;

    private Date fecha_captacion;

    private int pais_id;
    private int poblacion_id;

    private double coord_x;
    private double coord_y;
    private String nombre_calle;
    private int numero_calle;
    private String codigo_postal;
    private String poblacion;
    private String provincia;
    private String zona;
    private String ref_catastral;

    private String telefono_ip;

    private String descripcion;
    private String descripcion_abreviada;
    private String observaciones_pvi;
    private String video;
    private String tour_virtual;

    private String modified;

    private String web;
    private String marca_act;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgencia_id() {
        return agencia_id;
    }

    public void setAgencia_id(int agencia_id) {
        this.agencia_id = agencia_id;
    }

    public String getTipo_inmueble_id() {
        return tipo_inmueble_id;
    }

    public void setTipo_inmueble_id(String tipo_inmueble_id) {
        this.tipo_inmueble_id = tipo_inmueble_id;
    }

    public String getEs_venta() {
        return es_venta;
    }

    public void setEs_venta(String es_venta) {
        this.es_venta = es_venta;
    }

    public String getEs_alquiler() {
        return es_alquiler;
    }

    public void setEs_alquiler(String es_alquiler) {
        this.es_alquiler = es_alquiler;
    }

    public String getEs_traspaso() {
        return es_traspaso;
    }

    public void setEs_traspaso(String es_traspaso) {
        this.es_traspaso = es_traspaso;
    }

    public String getEs_opcion_compra() {
        return es_opcion_compra;
    }

    public void setEs_opcion_compra(String es_opcion_compra) {
        this.es_opcion_compra = es_opcion_compra;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public double getPrecio_alquiler() {
        return precio_alquiler;
    }

    public void setPrecio_alquiler(double precio_alquiler) {
        this.precio_alquiler = precio_alquiler;
    }

    public double getPrecio_traspaso() {
        return precio_traspaso;
    }

    public void setPrecio_traspaso(double precio_traspaso) {
        this.precio_traspaso = precio_traspaso;
    }

    public int getPais_id() {
        return pais_id;
    }

    public void setPais_id(int pais_id) {
        this.pais_id = pais_id;
    }

    public double getCoord_x() {
        return coord_x;
    }

    public void setCoord_x(double coord_x) {
        this.coord_x = coord_x;
    }

    public double getCoord_y() {
        return coord_y;
    }

    public void setCoord_y(double coord_y) {
        this.coord_y = coord_y;
    }

    public String getNombre_calle() {
        return nombre_calle;
    }

    public void setNombre_calle(String nombre_calle) {
        this.nombre_calle = nombre_calle;
    }

    public int getNumero_calle() {
        return numero_calle;
    }

    public void setNumero_calle(int numero_calle) {
        this.numero_calle = numero_calle;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion_abreviada() {
        return descripcion_abreviada;
    }

    public void setDescripcion_abreviada(String descripcion_abreviada) {
        this.descripcion_abreviada = descripcion_abreviada;
    }

    public String getObservaciones_pvi() {
        return observaciones_pvi;
    }

    public void setObservaciones_pvi(String observaciones_pvi) {
        this.observaciones_pvi = observaciones_pvi;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTour_virtual() {
        return tour_virtual;
    }

    public void setTour_virtual(String tour_virtual) {
        this.tour_virtual = tour_virtual;
    }

    public String getEs_promocion() {
        return es_promocion;
    }

    public void setEs_promocion(String es_promocion) {
        this.es_promocion = es_promocion;
    }

    public String getNombre_promocion() {
        return nombre_promocion;
    }

    public void setNombre_promocion(String nombre_promocion) {
        this.nombre_promocion = nombre_promocion;
    }

    public String getEntrega_promocion() {
        return entrega_promocion;
    }

    public void setEntrega_promocion(String entrega_promocion) {
        this.entrega_promocion = entrega_promocion;
    }

    public int getNumero_agencia() {
        return numero_agencia;
    }

    public void setNumero_agencia(int numero_agencia) {
        this.numero_agencia = numero_agencia;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getPrecio_venta_ini() {
        return precio_venta_ini;
    }

    public void setPrecio_venta_ini(double precio_venta_ini) {
        this.precio_venta_ini = precio_venta_ini;
    }

    public double getPrecio_alquiler_ini() {
        return precio_alquiler_ini;
    }

    public void setPrecio_alquiler_ini(double precio_alquiler_ini) {
        this.precio_alquiler_ini = precio_alquiler_ini;
    }

    public double getPrecio_traspaso_ini() {
        return precio_traspaso_ini;
    }

    public void setPrecio_traspaso_ini(double precio_traspaso_ini) {
        this.precio_traspaso_ini = precio_traspaso_ini;
    }

    public int getPoblacion_id() {
        return poblacion_id;
    }

    public void setPoblacion_id(int poblacion_id) {
        this.poblacion_id = poblacion_id;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getMoneda_id() {
        return moneda_id;
    }

    public void setMoneda_id(String moneda_id) {
        this.moneda_id = moneda_id;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Date getFecha_captacion() {
        return fecha_captacion;
    }

    public void setFecha_captacion(Date fecha_captacion) {
        this.fecha_captacion = fecha_captacion;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getMarca_act() {
        return marca_act;
    }

    public void setMarca_act(String marca_act) {
        this.marca_act = marca_act;
    }

    public double getPrecio_particular() {
        return precio_particular;
    }

    public void setPrecio_particular(double precio_particular) {
        this.precio_particular = precio_particular;
    }

    public String getRef_catastral() {
        return ref_catastral;
    }

    public void setRef_catastral(String ref_catastral) {
        this.ref_catastral = ref_catastral;
    }


    public String getTelefono_ip() {
        return telefono_ip;
    }

    public void setTelefono_ip(String telefono_ip) {
        this.telefono_ip = telefono_ip;
    }
}

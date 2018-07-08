package com.alfainmo.beans;

import java.util.Date;

/**
 * Created by dmonje on 09/02/14.
 */
public class PgInmuebleDb extends AbstractPgObject {
    private int id;
    private int agencia_id;
    private String tipo_inmueble_id;
    private String es_venta;
    private String es_alquiler;
    private String es_traspaso;
    private String es_opcion_compra;
    private double precio_venta;
    private double precio_alquiler;
    private double precio_traspaso;
    private double precio_venta_ini;
    private double precio_alquiler_ini;
    private double precio_traspaso_ini;
    private double precio_particular;

    private int pais_id;
    private double coord_x;
    private double coord_y;
    private String nombre_calle;
    private int numero_calle;
    private String codigo_postal;
    private String poblacion;
    private String provincia;
    private String tipo_contrato_id;
    private String medio_captacion_id;
    private Date fecha_captacion;
    private Date fecha_baja;
    private String motivo_baja_id;
    private String llaves;
    private String ref_catastral;
    private String registro_de;
    private Integer registro_numero;
    private Integer registro_tomo;
    private Integer registro_finca;
    private Integer registro_folio;
    private Integer registro_libro;

    private Double honor_agencia;
    private Double honor_colaborador;
    private String descripcion;
    private String estado_inmueble_id;
    private String cartel;
    private String honor_agencia_unid;
    private String honor_colaborador_unid;
    private int agente_id;
    private Date created;
    private Date updated;
    private Date modified;
    private String nombre_colaborador;
    private boolean sin_ref_catastral;
    private String descripcion_abreviada;
    private String observaciones_pvi;
    private String video;
    private String tour_virtual;
    private String es_promocion;
    private String nombre_promocion;
    private String entrega_promocion;
    private Double honor_compartidos;
    private int numero_agencia;
    private int codigo;
    private String zona;

    private String calidad_precio;
    private String moneda_id;

    private int poblacion_id;

    private String telefono_ip;

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

    public String getTipo_contrato_id() {
        return tipo_contrato_id;
    }

    public void setTipo_contrato_id(String tipo_contrato_id) {
        this.tipo_contrato_id = tipo_contrato_id;
    }

    public String getMedio_captacion_id() {
        return medio_captacion_id;
    }

    public void setMedio_captacion_id(String medio_captacion_id) {
        this.medio_captacion_id = medio_captacion_id;
    }

    public Date getFecha_captacion() {
        return fecha_captacion;
    }

    public void setFecha_captacion(Date fecha_captacion) {
        this.fecha_captacion = fecha_captacion;
    }

    public Date getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public String getMotivo_baja_id() {
        return motivo_baja_id;
    }

    public void setMotivo_baja_id(String motivo_baja_id) {
        this.motivo_baja_id = motivo_baja_id;
    }

    public String getLlaves() {
        return llaves;
    }

    public void setLlaves(String llaves) {
        this.llaves = llaves;
    }

    public String getRef_catastral() {
        return ref_catastral;
    }

    public void setRef_catastral(String ref_catastral) {
        this.ref_catastral = ref_catastral;
    }

    public String getRegistro_de() {
        return registro_de;
    }

    public void setRegistro_de(String registro_de) {
        this.registro_de = registro_de;
    }

    public Integer getRegistro_numero() {
        return registro_numero;
    }

    public void setRegistro_numero(Integer registro_numero) {
        this.registro_numero = registro_numero;
    }

    public Integer getRegistro_tomo() {
        return registro_tomo;
    }

    public void setRegistro_tomo(Integer registro_tomo) {
        this.registro_tomo = registro_tomo;
    }

    public Integer getRegistro_finca() {
        return registro_finca;
    }

    public void setRegistro_finca(Integer registro_finca) {
        this.registro_finca = registro_finca;
    }

    public Integer getRegistro_folio() {
        return registro_folio;
    }

    public void setRegistro_folio(Integer registro_folio) {
        this.registro_folio = registro_folio;
    }

    public Double getHonor_agencia() {
        return honor_agencia;
    }

    public void setHonor_agencia(Double honor_agencia) {
        this.honor_agencia = honor_agencia;
    }

    public Double getHonor_colaborador() {
        return honor_colaborador;
    }

    public void setHonor_colaborador(Double honor_colaborador) {
        this.honor_colaborador = honor_colaborador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado_inmueble_id() {
        return estado_inmueble_id;
    }

    public void setEstado_inmueble_id(String estado_inmueble_id) {
        this.estado_inmueble_id = estado_inmueble_id;
    }

    public String getCartel() {
        return cartel;
    }

    public void setCartel(String cartel) {
        this.cartel = cartel;
    }

    public String getHonor_agencia_unid() {
        return honor_agencia_unid;
    }

    public void setHonor_agencia_unid(String honor_agencia_unid) {
        this.honor_agencia_unid = honor_agencia_unid;
    }

    public String getHonor_colaborador_unid() {
        return honor_colaborador_unid;
    }

    public void setHonor_colaborador_unid(String honor_colaborador_unid) {
        this.honor_colaborador_unid = honor_colaborador_unid;
    }

    public int getAgente_id() {
        return agente_id;
    }

    public void setAgente_id(int agente_id) {
        this.agente_id = agente_id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getNombre_colaborador() {
        return nombre_colaborador;
    }

    public void setNombre_colaborador(String nombre_colaborador) {
        this.nombre_colaborador = nombre_colaborador;
    }

    public boolean isSin_ref_catastral() {
        return sin_ref_catastral;
    }

    public void setSin_ref_catastral(boolean sin_ref_catastral) {
        this.sin_ref_catastral = sin_ref_catastral;
    }

    public String getDescripcion_abreviada() {
        return descripcion_abreviada;
    }

    public void setDescripcion_abreviada(String descripcion_abreviada) {
        this.descripcion_abreviada = descripcion_abreviada;
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

    public Double getHonor_compartidos() {
        return honor_compartidos;
    }

    public void setHonor_compartidos(Double honor_compartidos) {
        this.honor_compartidos = honor_compartidos;
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

    public String getCalidad_precio() {
        return calidad_precio;
    }

    public void setCalidad_precio(String calidad_precio) {
        this.calidad_precio = calidad_precio;
    }

    public int getPoblacion_id() {
        return poblacion_id;
    }

    public void setPoblacion_id(int poblacion_id) {
        this.poblacion_id = poblacion_id;
    }

    public Integer getRegistro_libro() {
        return registro_libro;
    }

    public void setRegistro_libro(Integer registro_libro) {
        this.registro_libro = registro_libro;
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

    public double getPrecio_particular() {
        return precio_particular;
    }

    public void setPrecio_particular(double precio_particular) {
        this.precio_particular = precio_particular;
    }

    public String getObservaciones_pvi() {
        return observaciones_pvi;
    }

    public void setObservaciones_pvi(String observaciones_pvi) {
        this.observaciones_pvi = observaciones_pvi;
    }

    public String getTelefono_ip() {
        return telefono_ip;
    }

    public void setTelefono_ip(String telefono_ip) {
        this.telefono_ip = telefono_ip;
    }
}

package com.alfainmo.beans;

import java.util.Date;

/**
 * Created by dmonje on 11/02/14.
 */
public class PgPropietarioDb extends AbstractPgObject {
    private int id;
    private int inmueble_id;
    private int pais_id;
    private String codigo_postal;
    private String poblacion;
    private String provincia;
    private String direccion;
    private String nombre_contacto;
    private String email_contacto;
    private String telefono1_contacto;
    private String telefono2_contacto;
    private String dni;
    private String observaciones;
    private Date created;
    private Date updated;
    private Date modified;

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

    public int getPais_id() {
        return pais_id;
    }

    public void setPais_id(int pais_id) {
        this.pais_id = pais_id;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre_contacto() {
        return nombre_contacto;
    }

    public void setNombre_contacto(String nombre_contacto) {
        this.nombre_contacto = nombre_contacto;
    }

    public String getEmail_contacto() {
        return email_contacto;
    }

    public void setEmail_contacto(String email_contacto) {
        this.email_contacto = email_contacto;
    }

    public String getTelefono1_contacto() {
        return telefono1_contacto;
    }

    public void setTelefono1_contacto(String telefono1_contacto) {
        this.telefono1_contacto = telefono1_contacto;
    }

    public String getTelefono2_contacto() {
        return telefono2_contacto;
    }

    public void setTelefono2_contacto(String telefono2_contacto) {
        this.telefono2_contacto = telefono2_contacto;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
}

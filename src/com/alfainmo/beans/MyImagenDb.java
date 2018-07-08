package com.alfainmo.beans;

/**
 * Created by dmonje on 09/02/14.
 */
public class MyImagenDb extends AbstractPgObject {
    private int id;
    private String path;
    private String fichero;
    private String tipo_imagen_id;
    private int inmueble_id;
    private String descripcion;
    private int orden;
    private String marca_act;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFichero() {
        return fichero;
    }

    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    public String getTipo_imagen_id() {
        return tipo_imagen_id;
    }

    public void setTipo_imagen_id(String tipo_imagen_id) {
        this.tipo_imagen_id = tipo_imagen_id;
    }

    public int getInmueble_id() {
        return inmueble_id;
    }

    public void setInmueble_id(int inmueble_id) {
        this.inmueble_id = inmueble_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getMarca_act() {
        return marca_act;
    }

    public void setMarca_act(String marca_act) {
        this.marca_act = marca_act;
    }
}

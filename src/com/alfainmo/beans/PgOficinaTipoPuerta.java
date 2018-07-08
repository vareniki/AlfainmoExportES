package com.alfainmo.beans;

/**
 * Created by dmonje on 18/02/14.
 */
public class PgOficinaTipoPuerta extends AbstractPgObject {
    private int oficina_id;
    private String tipo_puerta_id;

    public int getOficina_id() {
        return oficina_id;
    }

    public void setOficina_id(int oficina_id) {
        this.oficina_id = oficina_id;
    }

    public String getTipo_puerta_id() {
        return tipo_puerta_id;
    }

    public void setTipo_puerta_id(String tipo_puerta_id) {
        this.tipo_puerta_id = tipo_puerta_id;
    }
}

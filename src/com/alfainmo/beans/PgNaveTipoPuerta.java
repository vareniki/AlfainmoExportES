package com.alfainmo.beans;

/**
 * Created by dmonje on 18/02/14.
 */
public class PgNaveTipoPuerta extends AbstractPgObject {
    private int nave_id;
    private String tipo_puerta_id;

    public int getNave_id() {
        return nave_id;
    }

    public void setNave_id(int nave_id) {
        this.nave_id = nave_id;
    }

    public String getTipo_puerta_id() {
        return tipo_puerta_id;
    }

    public void setTipo_puerta_id(String tipo_puerta_id) {
        this.tipo_puerta_id = tipo_puerta_id;
    }
}

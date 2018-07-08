package com.alfainmo.beans;

/**
 * Created by dmonje on 18/02/14.
 */
public class PgLocalTipoPuerta extends AbstractPgObject {
    private int local_id;
    private String tipo_puerta_id;

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public String getTipo_puerta_id() {
        return tipo_puerta_id;
    }

    public void setTipo_puerta_id(String tipo_puerta_id) {
        this.tipo_puerta_id = tipo_puerta_id;
    }
}

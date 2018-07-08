package com.alfainmo.beans;

/**
 * Created by dmonje on 18/02/14.
 */
public class PgPisoTipoPuerta extends AbstractPgObject {
    private int piso_id;
    private String tipo_puerta_id;

    public int getPiso_id() {
        return piso_id;
    }

    public void setPiso_id(int piso_id) {
        this.piso_id = piso_id;
    }

    public String getTipo_puerta_id() {
        return tipo_puerta_id;
    }

    public void setTipo_puerta_id(String tipo_puerta_id) {
        this.tipo_puerta_id = tipo_puerta_id;
    }
}

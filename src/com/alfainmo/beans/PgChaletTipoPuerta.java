package com.alfainmo.beans;

/**
 * Created by dmonje on 18/02/14.
 */
public class PgChaletTipoPuerta extends AbstractPgObject {
    private int chalet_id;
    private String tipo_puerta_id;

    public int getChalet_id() {
        return chalet_id;
    }

    public void setChalet_id(int chalet_id) {
        this.chalet_id = chalet_id;
    }

    public String getTipo_puerta_id() {
        return tipo_puerta_id;
    }

    public void setTipo_puerta_id(String tipo_puerta_id) {
        this.tipo_puerta_id = tipo_puerta_id;
    }
}

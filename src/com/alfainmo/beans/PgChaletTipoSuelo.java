package com.alfainmo.beans;

/**
 * Created by dmonje on 18/02/14.
 */
public class PgChaletTipoSuelo extends AbstractPgObject {
    private int chalet_id;
    private String tipo_suelo_id;

    public int getChalet_id() {
        return chalet_id;
    }

    public void setChalet_id(int chalet_id) {
        this.chalet_id = chalet_id;
    }

    public String getTipo_suelo_id() {
        return tipo_suelo_id;
    }

    public void setTipo_suelo_id(String tipo_suelo_id) {
        this.tipo_suelo_id = tipo_suelo_id;
    }
}

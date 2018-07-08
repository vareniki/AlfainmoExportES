package com.alfainmo.beans;

/**
 * Created by dmonje on 18/02/14.
 */
public class PgNaveTipoSuelo extends AbstractPgObject {
    private int nave_id;
    private String tipo_suelo_id;

    public int getNave_id() {
        return nave_id;
    }

    public void setNave_id(int nave_id) {
        this.nave_id = nave_id;
    }

    public String getTipo_suelo_id() {
        return tipo_suelo_id;
    }

    public void setTipo_suelo_id(String tipo_suelo_id) {
        this.tipo_suelo_id = tipo_suelo_id;
    }
}

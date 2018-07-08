package com.alfainmo.beans;

/**
 * Created by dmonje on 18/02/14.
 */
public class PgPisoTipoSuelo extends AbstractPgObject {
    private int piso_id;
    private String tipo_suelo_id;

    public int getPiso_id() {
        return piso_id;
    }

    public void setPiso_id(int piso_id) {
        this.piso_id = piso_id;
    }

    public String getTipo_suelo_id() {
        return tipo_suelo_id;
    }

    public void setTipo_suelo_id(String tipo_suelo_id) {
        this.tipo_suelo_id = tipo_suelo_id;
    }
}

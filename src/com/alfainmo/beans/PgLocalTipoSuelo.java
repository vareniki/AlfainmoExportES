package com.alfainmo.beans;

/**
 * Created by dmonje on 18/02/14.
 */
public class PgLocalTipoSuelo extends AbstractPgObject {
    private int local_id;
    private String tipo_suelo_id;

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public String getTipo_suelo_id() {
        return tipo_suelo_id;
    }

    public void setTipo_suelo_id(String tipo_suelo_id) {
        this.tipo_suelo_id = tipo_suelo_id;
    }
}

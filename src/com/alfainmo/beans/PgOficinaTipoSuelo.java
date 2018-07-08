package com.alfainmo.beans;

/**
 * Created by dmonje on 18/02/14.
 */
public class PgOficinaTipoSuelo extends AbstractPgObject {
    private int oficina_id;
    private String tipo_suelo_id;

    public int getOficina_id() {
        return oficina_id;
    }

    public void setOficina_id(int oficina_id) {
        this.oficina_id = oficina_id;
    }

    public String getTipo_suelo_id() {
        return tipo_suelo_id;
    }

    public void setTipo_suelo_id(String tipo_suelo_id) {
        this.tipo_suelo_id = tipo_suelo_id;
    }
}

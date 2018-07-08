package com.alfainmo.beans;

/**
 * Created by dmonje on 18/02/14.
 */
public class PgOficinaTipoVentana extends AbstractPgObject {
    private int oficina_id;
    private String tipo_ventana_id;

    public int getOficina_id() {
        return oficina_id;
    }

    public void setOficina_id(int oficina_id) {
        this.oficina_id = oficina_id;
    }

    public String getTipo_ventana_id() {
        return tipo_ventana_id;
    }

    public void setTipo_ventana_id(String tipo_ventana_id) {
        this.tipo_ventana_id = tipo_ventana_id;
    }
}

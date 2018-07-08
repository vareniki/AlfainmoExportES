package com.alfainmo.beans;

/**
 * Created by dmonje on 18/02/14.
 */
public class PgLocalTipoVentana extends AbstractPgObject {
    private int local_id;
    private String tipo_ventana_id;

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public String getTipo_ventana_id() {
        return tipo_ventana_id;
    }

    public void setTipo_ventana_id(String tipo_ventana_id) {
        this.tipo_ventana_id = tipo_ventana_id;
    }
}

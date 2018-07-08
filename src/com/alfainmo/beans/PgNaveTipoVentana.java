package com.alfainmo.beans;

/**
 * Created by dmonje on 18/02/14.
 */
public class PgNaveTipoVentana extends AbstractPgObject {
    private int nave_id;
    private String tipo_ventana_id;

    public int getNave_id() {
        return nave_id;
    }

    public void setNave_id(int nave_id) {
        this.nave_id = nave_id;
    }

    public String getTipo_ventana_id() {
        return tipo_ventana_id;
    }

    public void setTipo_ventana_id(String tipo_ventana_id) {
        this.tipo_ventana_id = tipo_ventana_id;
    }
}

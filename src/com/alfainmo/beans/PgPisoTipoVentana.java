package com.alfainmo.beans;

/**
 * Created by dmonje on 18/02/14.
 */
public class PgPisoTipoVentana extends AbstractPgObject {
    private int piso_id;
    private String tipo_ventana_id;

    public int getPiso_id() {
        return piso_id;
    }

    public void setPiso_id(int piso_id) {
        this.piso_id = piso_id;
    }

    public String getTipo_ventana_id() {
        return tipo_ventana_id;
    }

    public void setTipo_ventana_id(String tipo_ventana_id) {
        this.tipo_ventana_id = tipo_ventana_id;
    }
}

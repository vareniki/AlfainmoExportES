package com.alfainmo.beans;

/**
 * Created by dmonje on 18/02/14.
 */
public class PgChaletTipoVentana extends AbstractPgObject {
    private int chalet_id;
    private String tipo_ventana_id;

    public int getChalet_id() {
        return chalet_id;
    }

    public void setChalet_id(int chalet_id) {
        this.chalet_id = chalet_id;
    }

    public String getTipo_ventana_id() {
        return tipo_ventana_id;
    }

    public void setTipo_ventana_id(String tipo_ventana_id) {
        this.tipo_ventana_id = tipo_ventana_id;
    }
}

package com.alfainmo.beans;

/**
 * Created by dmonje on 19/05/14.
 */
public class PgAgenciaPortalDb extends AbstractPgObject {
    private int agencia_id;
    private String portal_id;

    public int getAgencia_id() {
        return agencia_id;
    }

    public void setAgencia_id(int agencia_id) {
        this.agencia_id = agencia_id;
    }

    public String getPortal_id() {
        return portal_id;
    }

    public void setPortal_id(String portal_id) {
        this.portal_id = portal_id;
    }
}

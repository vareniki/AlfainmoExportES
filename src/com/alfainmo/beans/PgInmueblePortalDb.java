package com.alfainmo.beans;

/**
 * Created by dmonje on 19/05/14.
 */
public class PgInmueblePortalDb extends AbstractPgObject {
    private int inmueble_id;
    private String portal_id;

    public int getInmueble_id() {
        return inmueble_id;
    }

    public void setInmueble_id(int inmueble_id) {
        this.inmueble_id = inmueble_id;
    }

    public String getPortal_id() {
        return portal_id;
    }

    public void setPortal_id(String portal_id) {
        this.portal_id = portal_id;
    }
}

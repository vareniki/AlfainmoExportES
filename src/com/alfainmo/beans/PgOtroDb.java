package com.alfainmo.beans;

/**
 * Created by dmonje on 11/02/14.
 */
public class PgOtroDb extends AbstractPgObject {
    private int id;
    private int inmueble_id;
    private int area_total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInmueble_id() {
        return inmueble_id;
    }

    public void setInmueble_id(int inmueble_id) {
        this.inmueble_id = inmueble_id;
    }

    public int getArea_total() {
        return area_total;
    }

    public void setArea_total(int area_total) {
        this.area_total = area_total;
    }
}

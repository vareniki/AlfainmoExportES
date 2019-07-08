package com.alfainmo.beans;

/**
 * @author dmonje
 */
public class MyInmuebleDbPortal extends MyInmuebleDb implements Comparable<MyInmuebleDbPortal> {
    private int orderBy;
    private String exclusivo;
    private String destacado;

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public String getExclusivo() {
        return exclusivo;
    }

    public void setExclusivo(String exclusivo) {
        this.exclusivo = exclusivo;
    }

    public String getDestacado() {
        return destacado;
    }

    public void setDestacado(String destacado) {
        this.destacado = destacado;
    }

    @Override
    public int compareTo(MyInmuebleDbPortal o) {

        int compareVal1 = this.orderBy;
        int compareVal2 = o.orderBy;

        return Integer.compare(compareVal1, compareVal2);
    }

}
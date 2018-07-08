package com.alfainmo.beans;

/**
 * @author dmonje
 */
public class MyInmuebleDbPortal extends MyInmuebleDb implements Comparable<MyInmuebleDbPortal> {
    private int orderBy;

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public int compareTo(MyInmuebleDbPortal o) {

        int compareVal1 = this.orderBy;
        int compareVal2 = o.orderBy;

        return Integer.compare(compareVal1, compareVal2);
    }

}
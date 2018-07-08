
package com.alfainmo.beans;

import java.util.List;

/**
 * @author dmonje
 */
public class PgAgenciaInfo {
    private PgAgenciaDb agenciaDb;
    private List<PgAgenciaPortalDb> portales = null;

    public PgAgenciaDb getAgenciaDb() {
        return agenciaDb;
    }

    public void setAgenciaDb(PgAgenciaDb agenciaDb) {
        this.agenciaDb = agenciaDb;
    }

    public List<PgAgenciaPortalDb> getPortales() {
        return portales;
    }

    public void setPortales(List<PgAgenciaPortalDb> portales) {
        this.portales = portales;
    }

}

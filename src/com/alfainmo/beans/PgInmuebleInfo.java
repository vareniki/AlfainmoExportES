package com.alfainmo.beans;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by dmonje on 14/05/14.
 */
public class PgInmuebleInfo {

    private PgInmuebleDb inmuebleDb;
    private PgPisoDb pisoDb = null;
    private List<PgPisoTipoPuerta> pisoTiposPuerta = null;
    private List<PgPisoTipoSuelo> pisoTiposSuelo = null;
    private List<PgPisoTipoVentana> pisoTiposVentana = null;

    private PgChaletDb chaletDb = null;
    private List<PgChaletTipoPuerta> chaletTiposPuerta = null;
    private List<PgChaletTipoSuelo> chaletTiposSuelo = null;
    private List<PgChaletTipoVentana> chaletTiposVentana = null;

    private PgLocalDb localDb = null;
    private List<PgLocalTipoPuerta> localTiposPuerta = null;
    private List<PgLocalTipoSuelo> localTiposSuelo = null;
    private List<PgLocalTipoVentana> localTiposVentana = null;

    private PgOficinaDb oficinaDb = null;
    private List<PgOficinaTipoPuerta> oficinaTiposPuerta = null;
    private List<PgOficinaTipoSuelo> oficinaTiposSuelo = null;
    private List<PgOficinaTipoVentana> oficinaTiposVentana = null;

    private PgGarajeDb garajeDb = null;
    private PgTerrenoDb terrenoDb = null;

    private PgNaveDb naveDb = null;
    private List<PgNaveTipoPuerta> naveTiposPuerta = null;
    private List<PgNaveTipoSuelo> naveTiposSuelo = null;
    private List<PgNaveTipoVentana> naveTiposVentana = null;

    private PgOtroDb otroDb = null;
    private PgPropietarioDb propietarioDb = null;

    private List<PgImagenDb> imagenes = null;

    private List<PgInmueblePortalDb> portales = null;
    private List<PgInmueblePortalDb> noPortales = null;
    private List<PgInmueblePortalDb> centralPortales = null;

    public PgInmuebleDb getInmuebleDb() {
        return inmuebleDb;
    }

    public void setInmuebleDb(PgInmuebleDb inmuebleDb) {
        this.inmuebleDb = inmuebleDb;
    }

    public PgPisoDb getPisoDb() {
        return pisoDb;
    }

    public void setPisoDb(PgPisoDb pisoDb) {
        this.pisoDb = pisoDb;
    }

    public PgChaletDb getChaletDb() {
        return chaletDb;
    }

    public void setChaletDb(PgChaletDb chaletDb) {
        this.chaletDb = chaletDb;
    }

    public PgLocalDb getLocalDb() {
        return localDb;
    }

    public void setLocalDb(PgLocalDb localDb) {
        this.localDb = localDb;
    }

    public PgOficinaDb getOficinaDb() {
        return oficinaDb;
    }

    public void setOficinaDb(PgOficinaDb oficinaDb) {
        this.oficinaDb = oficinaDb;
    }

    public PgGarajeDb getGarajeDb() {
        return garajeDb;
    }

    public void setGarajeDb(PgGarajeDb garajeDb) {
        this.garajeDb = garajeDb;
    }

    public PgTerrenoDb getTerrenoDb() {
        return terrenoDb;
    }

    public void setTerrenoDb(PgTerrenoDb terrenoDb) {
        this.terrenoDb = terrenoDb;
    }

    public PgNaveDb getNaveDb() {
        return naveDb;
    }

    public void setNaveDb(PgNaveDb naveDb) {
        this.naveDb = naveDb;
    }

    public PgOtroDb getOtroDb() {
        return otroDb;
    }

    public void setOtroDb(PgOtroDb otroDb) {
        this.otroDb = otroDb;
    }

    public List<PgImagenDb> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<PgImagenDb> imagenes) {
        this.imagenes = imagenes;
    }

    public PgPropietarioDb getPropietarioDb() {
        return propietarioDb;
    }

    public void setPropietarioDb(PgPropietarioDb propietarioDb) {
        this.propietarioDb = propietarioDb;
    }

    public List<PgPisoTipoPuerta> getPisoTiposPuerta() {
        return pisoTiposPuerta;
    }

    public void setPisoTiposPuerta(List<PgPisoTipoPuerta> pisoTiposPuerta) {
        this.pisoTiposPuerta = pisoTiposPuerta;
    }

    public List<PgPisoTipoSuelo> getPisoTiposSuelo() {
        return pisoTiposSuelo;
    }

    public void setPisoTiposSuelo(List<PgPisoTipoSuelo> pisoTiposSuelo) {
        this.pisoTiposSuelo = pisoTiposSuelo;
    }

    public List<PgPisoTipoVentana> getPisoTiposVentana() {
        return pisoTiposVentana;
    }

    public void setPisoTiposVentana(List<PgPisoTipoVentana> pisoTiposVentana) {
        this.pisoTiposVentana = pisoTiposVentana;
    }

    public List<PgChaletTipoPuerta> getChaletTiposPuerta() {
        return chaletTiposPuerta;
    }

    public void setChaletTiposPuerta(List<PgChaletTipoPuerta> chaletTiposPuerta) {
        this.chaletTiposPuerta = chaletTiposPuerta;
    }

    public List<PgChaletTipoSuelo> getChaletTiposSuelo() {
        return chaletTiposSuelo;
    }

    public void setChaletTiposSuelo(List<PgChaletTipoSuelo> chaletTiposSuelo) {
        this.chaletTiposSuelo = chaletTiposSuelo;
    }

    public List<PgChaletTipoVentana> getChaletTiposVentana() {
        return chaletTiposVentana;
    }

    public void setChaletTiposVentana(List<PgChaletTipoVentana> chaletTiposVentana) {
        this.chaletTiposVentana = chaletTiposVentana;
    }

    public List<PgLocalTipoPuerta> getLocalTiposPuerta() {
        return localTiposPuerta;
    }

    public void setLocalTiposPuerta(List<PgLocalTipoPuerta> localTiposPuerta) {
        this.localTiposPuerta = localTiposPuerta;
    }

    public List<PgLocalTipoSuelo> getLocalTiposSuelo() {
        return localTiposSuelo;
    }

    public void setLocalTiposSuelo(List<PgLocalTipoSuelo> localTiposSuelo) {
        this.localTiposSuelo = localTiposSuelo;
    }

    public List<PgLocalTipoVentana> getLocalTiposVentana() {
        return localTiposVentana;
    }

    public void setLocalTiposVentana(List<PgLocalTipoVentana> localTiposVentana) {
        this.localTiposVentana = localTiposVentana;
    }

    public List<PgOficinaTipoPuerta> getOficinaTiposPuerta() {
        return oficinaTiposPuerta;
    }

    public void setOficinaTiposPuerta(List<PgOficinaTipoPuerta> oficinaTiposPuerta) {
        this.oficinaTiposPuerta = oficinaTiposPuerta;
    }

    public List<PgOficinaTipoSuelo> getOficinaTiposSuelo() {
        return oficinaTiposSuelo;
    }

    public void setOficinaTiposSuelo(List<PgOficinaTipoSuelo> oficinaTiposSuelo) {
        this.oficinaTiposSuelo = oficinaTiposSuelo;
    }

    public List<PgOficinaTipoVentana> getOficinaTiposVentana() {
        return oficinaTiposVentana;
    }

    public void setOficinaTiposVentana(List<PgOficinaTipoVentana> oficinaTiposVentana) {
        this.oficinaTiposVentana = oficinaTiposVentana;
    }

    public List<PgNaveTipoPuerta> getNaveTiposPuerta() {
        return naveTiposPuerta;
    }

    public void setNaveTiposPuerta(List<PgNaveTipoPuerta> naveTiposPuerta) {
        this.naveTiposPuerta = naveTiposPuerta;
    }

    public List<PgNaveTipoSuelo> getNaveTiposSuelo() {
        return naveTiposSuelo;
    }

    public void setNaveTiposSuelo(List<PgNaveTipoSuelo> naveTiposSuelo) {
        this.naveTiposSuelo = naveTiposSuelo;
    }

    public List<PgNaveTipoVentana> getNaveTiposVentana() {
        return naveTiposVentana;
    }

    public void setNaveTiposVentana(List<PgNaveTipoVentana> naveTiposVentana) {
        this.naveTiposVentana = naveTiposVentana;
    }

    public List<PgInmueblePortalDb> getPortales() {
        return portales;
    }

    public void setPortales(List<PgInmueblePortalDb> portales) {
        this.portales = portales;
    }

    public List<PgInmueblePortalDb> getNoPortales() {
        return noPortales;
    }

    public void setNoPortales(List<PgInmueblePortalDb> noPortales) {
        this.noPortales = noPortales;
    }

    public List<PgInmueblePortalDb> getCentralPortales() {
        return centralPortales;
    }

    public void setCentralPortales(List<PgInmueblePortalDb> centralPortales) {
        this.centralPortales = centralPortales;
    }

    /**
     * @param field
     * @return
     */
    public String getFieldValue(String field) {

        if (field == null) {
            return "";
        }

        String result = "";

        Method method = null;
        Object obj = null;

        field = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
        try {
            switch (inmuebleDb.getTipo_inmueble_id()) {
                case "01":
                    method = PgPisoDb.class.getMethod(field);
                    obj = pisoDb;
                    break;
                case "02":
                    method = PgChaletDb.class.getMethod(field);
                    obj = chaletDb;
                    break;
                case "03":
                    method = PgLocalDb.class.getMethod(field);
                    obj = localDb;
                    break;
                case "04":
                    method = PgOficinaDb.class.getMethod(field);
                    obj = oficinaDb;
                    break;
                case "05":
                    method = PgGarajeDb.class.getMethod(field);
                    obj = garajeDb;
                    break;
                case "06":
                    method = PgTerrenoDb.class.getMethod(field);
                    obj = terrenoDb;
                    break;
                case "07":
                    method = PgNaveDb.class.getMethod(field);
                    obj = naveDb;
                    break;
                case "08":
                    method = PgOtroDb.class.getMethod(field);
                    obj = otroDb;
                    break;
            }
            if (method != null) {
                Object val = method.invoke(obj);
                if (val != null) {
                    result = val.toString();
                }
            }

        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            result = "";
        }

        return result;
    }

}

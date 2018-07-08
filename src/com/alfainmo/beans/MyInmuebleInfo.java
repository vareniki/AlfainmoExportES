package com.alfainmo.beans;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author dmonje
 */
public class MyInmuebleInfo {

    private MyInmuebleDb inmuebleDb;

    private MyPisoDb pisoDb = null;
    private MyChaletDb chaletDb = null;
    private MyLocalDb localDb = null;
    private MyOficinaDb oficinaDb = null;
    private MyGarajeDb garajeDb = null;
    private MyTerrenoDb terrenoDb = null;
    private MyNaveDb naveDb = null;
    private MyOtroDb otroDb = null;
    private MyPropietarioDb propietarioDb = null;

    private List<MyInmuebleTipoPuerta> tiposPuerta = null;
    private List<MyInmuebleTipoSuelo> tiposSuelo = null;
    private List<MyInmuebleTipoVentana> tiposVentana = null;

    private List<MyImagenDb> imagenes = null;
    private List<MyInmueblePortalDb> portales = null;
    private List<MyInmueblePortalDb> noPortales = null;

    private MyAgenciaDb agencia = null;

    public MyInmuebleDb getInmuebleDb() {
        return inmuebleDb;
    }

    public void setInmuebleDb(MyInmuebleDb inmuebleDb) {
        this.inmuebleDb = inmuebleDb;
    }

    public MyPisoDb getPisoDb() {
        return pisoDb;
    }

    public void setPisoDb(MyPisoDb pisoDb) {
        this.pisoDb = pisoDb;
    }

    public MyChaletDb getChaletDb() {
        return chaletDb;
    }

    public void setChaletDb(MyChaletDb chaletDb) {
        this.chaletDb = chaletDb;
    }

    public MyLocalDb getLocalDb() {
        return localDb;
    }

    public void setLocalDb(MyLocalDb localDb) {
        this.localDb = localDb;
    }

    public MyOficinaDb getOficinaDb() {
        return oficinaDb;
    }

    public void setOficinaDb(MyOficinaDb oficinaDb) {
        this.oficinaDb = oficinaDb;
    }

    public MyGarajeDb getGarajeDb() {
        return garajeDb;
    }

    public void setGarajeDb(MyGarajeDb garajeDb) {
        this.garajeDb = garajeDb;
    }

    public MyTerrenoDb getTerrenoDb() {
        return terrenoDb;
    }

    public void setTerrenoDb(MyTerrenoDb terrenoDb) {
        this.terrenoDb = terrenoDb;
    }

    public MyNaveDb getNaveDb() {
        return naveDb;
    }

    public void setNaveDb(MyNaveDb naveDb) {
        this.naveDb = naveDb;
    }

    public MyOtroDb getOtroDb() {
        return otroDb;
    }

    public void setOtroDb(MyOtroDb otroDb) {
        this.otroDb = otroDb;
    }

    public List<MyImagenDb> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<MyImagenDb> imagenes) {
        this.imagenes = imagenes;
    }

    public MyPropietarioDb getPropietarioDb() {
        return propietarioDb;
    }

    public void setPropietarioDb(MyPropietarioDb propietarioDb) {
        this.propietarioDb = propietarioDb;
    }

    public List<MyInmuebleTipoPuerta> getTiposPuerta() {
        return tiposPuerta;
    }

    public void setTiposPuerta(List<MyInmuebleTipoPuerta> tiposPuerta) {
        this.tiposPuerta = tiposPuerta;
    }

    public List<MyInmuebleTipoSuelo> getTiposSuelo() {
        return tiposSuelo;
    }

    public void setTiposSuelo(List<MyInmuebleTipoSuelo> tiposSuelo) {
        this.tiposSuelo = tiposSuelo;
    }

    public List<MyInmuebleTipoVentana> getTiposVentana() {
        return tiposVentana;
    }

    public void setTiposVentana(List<MyInmuebleTipoVentana> tiposVentana) {
        this.tiposVentana = tiposVentana;
    }

    public List<MyInmueblePortalDb> getPortales() {
        return portales;
    }

    public void setPortales(List<MyInmueblePortalDb> portales) {
        this.portales = portales;
    }

    public List<MyInmueblePortalDb> getNoPortales() {
        return noPortales;
    }

    public void setNoPortales(List<MyInmueblePortalDb> noPortales) {
        this.noPortales = noPortales;
    }

    public MyAgenciaDb getAgencia() {
        return agencia;
    }

    public void setAgencia(MyAgenciaDb agencia) {
        this.agencia = agencia;
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
                    method = MyPisoDb.class.getMethod(field);
                    obj = pisoDb;
                    break;
                case "02":
                    method = MyChaletDb.class.getMethod(field);
                    obj = chaletDb;
                    break;
                case "03":
                    method = MyLocalDb.class.getMethod(field);
                    obj = localDb;
                    break;
                case "04":
                    method = MyOficinaDb.class.getMethod(field);
                    obj = oficinaDb;
                    break;
                case "05":
                    method = MyGarajeDb.class.getMethod(field);
                    obj = garajeDb;
                    break;
                case "06":
                    method = MyTerrenoDb.class.getMethod(field);
                    obj = terrenoDb;
                    break;
                case "07":
                    method = MyNaveDb.class.getMethod(field);
                    obj = naveDb;
                    break;
                case "08":
                    method = MyOtroDb.class.getMethod(field);
                    obj = otroDb;
                    break;
            }
            if (method != null && obj != null) {
                Object val = method.invoke(obj);
                if (val != null) {
                    result = val.toString();
                }
            }

        } catch (NullPointerException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            result = "";
        }

        return result;
    }

    /**
     * @param field
     * @return
     */
    public String getFieldValueTF(String field) {

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
                    method = MyPisoDb.class.getMethod(field);
                    obj = pisoDb;
                    break;
                case "02":
                    method = MyChaletDb.class.getMethod(field);
                    obj = chaletDb;
                    break;
                case "03":
                    method = MyLocalDb.class.getMethod(field);
                    obj = localDb;
                    break;
                case "04":
                    method = MyOficinaDb.class.getMethod(field);
                    obj = oficinaDb;
                    break;
                case "05":
                    method = MyGarajeDb.class.getMethod(field);
                    obj = garajeDb;
                    break;
                case "06":
                    method = MyTerrenoDb.class.getMethod(field);
                    obj = terrenoDb;
                    break;
                case "07":
                    method = MyNaveDb.class.getMethod(field);
                    obj = naveDb;
                    break;
                case "08":
                    method = MyOtroDb.class.getMethod(field);
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

        if (result.equals("1") || result.equals("t")) {
            result = "t";
        } else {
            result = "f";
        }

        return result;
    }
}

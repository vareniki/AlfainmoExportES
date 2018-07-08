package com.alfainmo.portales;

import com.alfainmo.beans.MyAgenciaDb;
import com.alfainmo.beans.MyChaletDb;
import com.alfainmo.beans.MyGarajeDb;
import com.alfainmo.beans.MyImagenDb;
import com.alfainmo.beans.MyInmuebleDb;
import com.alfainmo.beans.MyInmuebleInfo;
import com.alfainmo.beans.MyInmueblePortalDb;
import com.alfainmo.beans.MyLocalDb;
import com.alfainmo.beans.MyNaveDb;
import com.alfainmo.beans.MyOficinaDb;
import com.alfainmo.beans.MyOtroDb;
import com.alfainmo.beans.MyPisoDb;
import com.alfainmo.beans.MyInmuebleTipoPuerta;
import com.alfainmo.beans.MyInmuebleTipoSuelo;
import com.alfainmo.beans.MyInmuebleTipoVentana;
import com.alfainmo.beans.MyPropietarioDb;
import com.alfainmo.beans.MyTerrenoDb;
import com.alfainmo.extra.AlfaException;
import com.alfainmo.util.BdUtils;

/**
 * @author dmonje
 */
public class AbstractExport2PrtPago extends AbstractExport2Prt {

    public AbstractExport2PrtPago(BdUtils bdUtils, String pathDestino, String prefijo) {
        super(bdUtils, pathDestino, prefijo);
    }

    /**
     * @param inmuebleDb
     * @return
     * @throws AlfaException
     */
    protected MyInmuebleInfo cargarInmuebleInfo(MyInmuebleDb inmuebleDb) throws AlfaException {
        MyInmuebleInfo result = new MyInmuebleInfo();
        result.setInmuebleDb(inmuebleDb);

        result.setAgencia(bdUtils.getData("SELECT * FROM agencias WHERE id=" + inmuebleDb.getAgencia_id(), MyAgenciaDb.class));

        switch (inmuebleDb.getTipo_inmueble_id()) {
            case "01":
                result.setPisoDb(bdUtils.getData("SELECT * FROM pisos WHERE inmueble_id=" + inmuebleDb.getId(), MyPisoDb.class));
                break;
            case "02":
                result.setChaletDb(bdUtils.getData("SELECT * FROM chalets WHERE inmueble_id=" + inmuebleDb.getId(), MyChaletDb.class));
                break;
            case "03":
                result.setLocalDb(bdUtils.getData("SELECT * FROM locales WHERE inmueble_id=" + inmuebleDb.getId(), MyLocalDb.class));
                break;
            case "04":
                result.setOficinaDb(bdUtils.getData("SELECT * FROM oficinas WHERE inmueble_id=" + inmuebleDb.getId(), MyOficinaDb.class));
                break;
            case "05":
                result.setGarajeDb(bdUtils.getData("SELECT * FROM garajes WHERE inmueble_id=" + inmuebleDb.getId(), MyGarajeDb.class));
                break;
            case "06":
                result.setTerrenoDb(bdUtils.getData("SELECT * FROM terrenos WHERE inmueble_id=" + inmuebleDb.getId(), MyTerrenoDb.class));
                break;
            case "07":
                result.setNaveDb(bdUtils.getData("SELECT * FROM naves WHERE inmueble_id=" + inmuebleDb.getId(), MyNaveDb.class));
                break;
            case "08":
                result.setOtroDb(bdUtils.getData("SELECT * FROM otros WHERE inmueble_id=" + inmuebleDb.getId(), MyOtroDb.class));
                break;
        }
        result.setTiposPuerta(bdUtils.getDataList("SELECT * FROM inmuebles_tipo_puerta WHERE inmueble_id=" + inmuebleDb.getId(), MyInmuebleTipoPuerta.class));
        result.setTiposSuelo(bdUtils.getDataList("SELECT * FROM inmuebles_tipo_suelo WHERE inmueble_id=" + inmuebleDb.getId(), MyInmuebleTipoSuelo.class));
        result.setTiposVentana(bdUtils.getDataList("SELECT * FROM inmuebles_tipo_ventana WHERE inmueble_id=" + inmuebleDb.getId(), MyInmuebleTipoVentana.class));

        result.setPropietarioDb(bdUtils.getData("SELECT * FROM propietarios WHERE inmueble_id=" + inmuebleDb.getId(), MyPropietarioDb.class));
        result.setImagenes(bdUtils.getDataList("SELECT * FROM imagenes WHERE inmueble_id=" + inmuebleDb.getId() + " ORDER BY orden", MyImagenDb.class));
        result.setPortales(bdUtils.getDataList("SELECT * FROM inmuebles_portal WHERE inmueble_id=" + inmuebleDb.getId(), MyInmueblePortalDb.class));

        return result;
    }

}

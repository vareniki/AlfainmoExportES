package com.alfainmo.process;

import com.alfainmo.beans.PgAgenciaInfo;
import com.alfainmo.beans.PgInmuebleInfo;
import com.alfainmo.beans.PgAgenciaDb;
import com.alfainmo.beans.PgAgenciaPortalDb;
import com.alfainmo.beans.PgChaletDb;
import com.alfainmo.beans.PgChaletTipoPuerta;
import com.alfainmo.beans.PgChaletTipoSuelo;
import com.alfainmo.beans.PgChaletTipoVentana;
import com.alfainmo.beans.PgGarajeDb;
import com.alfainmo.beans.PgImagenDb;
import com.alfainmo.beans.PgInmuebleDb;
import com.alfainmo.beans.PgInmueblePortalDb;
import com.alfainmo.beans.PgLocalDb;
import com.alfainmo.beans.PgLocalTipoPuerta;
import com.alfainmo.beans.PgLocalTipoSuelo;
import com.alfainmo.beans.PgLocalTipoVentana;
import com.alfainmo.beans.PgNaveDb;
import com.alfainmo.beans.PgNaveTipoPuerta;
import com.alfainmo.beans.PgNaveTipoSuelo;
import com.alfainmo.beans.PgNaveTipoVentana;
import com.alfainmo.beans.PgOficinaDb;
import com.alfainmo.beans.PgOficinaTipoPuerta;
import com.alfainmo.beans.PgOficinaTipoSuelo;
import com.alfainmo.beans.PgOficinaTipoVentana;
import com.alfainmo.beans.PgOtroDb;
import com.alfainmo.beans.PgPisoDb;
import com.alfainmo.beans.PgPisoTipoPuerta;
import com.alfainmo.beans.PgPisoTipoSuelo;
import com.alfainmo.beans.PgPisoTipoVentana;
import com.alfainmo.beans.PgPropietarioDb;
import com.alfainmo.beans.PgTerrenoDb;
import com.alfainmo.extra.AlfaException;
import com.alfainmo.util.BdUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author dmonje
 */
public class AbstractExport2Web {

    protected final BdUtils bdFnt, bdDst;
    protected final String marcaAct;

    public AbstractExport2Web(BdUtils bdFnt, BdUtils bdDst) {
        this.bdFnt = bdFnt;
        this.bdDst = bdDst;

        this.marcaAct = (new SimpleDateFormat("yyMMdd")).format(Calendar.getInstance().getTime());
    }

    /**
     * @param inmuebleDb
     * @return
     * @throws AlfaException
     */
    protected PgInmuebleInfo cargarInmuebleInfo(PgInmuebleDb inmuebleDb) throws AlfaException {
        PgInmuebleInfo result = new PgInmuebleInfo();
        result.setInmuebleDb(inmuebleDb);

        switch (inmuebleDb.getTipo_inmueble_id()) {
            case "01":
                result.setPisoDb(bdFnt.getData("SELECT * FROM pisos WHERE inmueble_id=" + inmuebleDb.getId(), PgPisoDb.class));
                if (result.getPisoDb() != null) {
                    result.setPisoTiposPuerta(bdFnt.getDataList("SELECT * FROM pisos_tipo_puerta WHERE piso_id=" + result.getPisoDb().getId(), PgPisoTipoPuerta.class));
                    result.setPisoTiposSuelo(bdFnt.getDataList("SELECT * FROM pisos_tipo_suelo WHERE piso_id=" + result.getPisoDb().getId(), PgPisoTipoSuelo.class));
                    result.setPisoTiposVentana(bdFnt.getDataList("SELECT * FROM pisos_tipo_ventana WHERE piso_id=" + result.getPisoDb().getId(), PgPisoTipoVentana.class));
                }
                break;
            case "02":
                result.setChaletDb(bdFnt.getData("SELECT * FROM chalets WHERE inmueble_id=" + inmuebleDb.getId(), PgChaletDb.class));
                if (result.getChaletDb() != null) {
                    result.setChaletTiposPuerta(bdFnt.getDataList("SELECT * FROM chalets_tipo_puerta WHERE chalet_id=" + result.getChaletDb().getId(), PgChaletTipoPuerta.class));
                    result.setChaletTiposSuelo(bdFnt.getDataList("SELECT * FROM chalets_tipo_suelo WHERE chalet_id=" + result.getChaletDb().getId(), PgChaletTipoSuelo.class));
                    result.setChaletTiposVentana(bdFnt.getDataList("SELECT * FROM chalets_tipo_ventana WHERE chalet_id=" + result.getChaletDb().getId(), PgChaletTipoVentana.class));
                }
                break;
            case "03":
                result.setLocalDb(bdFnt.getData("SELECT * FROM locales WHERE inmueble_id=" + inmuebleDb.getId(), PgLocalDb.class));
                if (result.getLocalDb() != null) {
                    result.setLocalTiposPuerta(bdFnt.getDataList("SELECT * FROM locales_tipo_puerta WHERE local_id=" + result.getLocalDb().getId(), PgLocalTipoPuerta.class));
                    result.setLocalTiposSuelo(bdFnt.getDataList("SELECT * FROM locales_tipo_suelo WHERE local_id=" + result.getLocalDb().getId(), PgLocalTipoSuelo.class));
                    result.setLocalTiposVentana(bdFnt.getDataList("SELECT * FROM locales_tipo_ventana WHERE local_id=" + result.getLocalDb().getId(), PgLocalTipoVentana.class));
                }
                break;
            case "04":
                result.setOficinaDb(bdFnt.getData("SELECT * FROM oficinas WHERE inmueble_id=" + inmuebleDb.getId(), PgOficinaDb.class));
                if (result.getOficinaDb() != null) {
                    result.setOficinaTiposPuerta(bdFnt.getDataList("SELECT * FROM oficinas_tipo_puerta WHERE oficina_id=" + result.getOficinaDb().getId(), PgOficinaTipoPuerta.class));
                    result.setOficinaTiposSuelo(bdFnt.getDataList("SELECT * FROM oficinas_tipo_suelo WHERE oficina_id=" + result.getOficinaDb().getId(), PgOficinaTipoSuelo.class));
                    result.setOficinaTiposVentana(bdFnt.getDataList("SELECT * FROM oficinas_tipo_ventana WHERE oficina_id=" + result.getOficinaDb().getId(), PgOficinaTipoVentana.class));
                }
                break;
            case "05":
                result.setGarajeDb(bdFnt.getData("SELECT * FROM garajes WHERE inmueble_id=" + inmuebleDb.getId(), PgGarajeDb.class));
                break;
            case "06":
                result.setTerrenoDb(bdFnt.getData("SELECT * FROM terrenos WHERE inmueble_id=" + inmuebleDb.getId(), PgTerrenoDb.class));
                break;
            case "07":
                result.setNaveDb(bdFnt.getData("SELECT * FROM naves WHERE inmueble_id=" + inmuebleDb.getId(), PgNaveDb.class));
                if (result.getNaveDb() != null) {
                    result.setNaveTiposPuerta(bdFnt.getDataList("SELECT * FROM naves_tipo_puerta WHERE nave_id=" + result.getNaveDb().getId(), PgNaveTipoPuerta.class));
                    result.setNaveTiposSuelo(bdFnt.getDataList("SELECT * FROM naves_tipo_suelo WHERE nave_id=" + result.getNaveDb().getId(), PgNaveTipoSuelo.class));
                    result.setNaveTiposVentana(bdFnt.getDataList("SELECT * FROM naves_tipo_ventana WHERE nave_id=" + result.getNaveDb().getId(), PgNaveTipoVentana.class));
                }
                break;
            case "08":
                result.setOtroDb(bdFnt.getData("SELECT * FROM otros WHERE inmueble_id=" + inmuebleDb.getId(), PgOtroDb.class));
                break;
        }

        result.setPropietarioDb(bdFnt.getData("SELECT * FROM propietarios WHERE inmueble_id=" + inmuebleDb.getId(), PgPropietarioDb.class));
        result.setImagenes(bdFnt.getDataList("SELECT * FROM imagenes WHERE inmueble_id=" + inmuebleDb.getId() + " ORDER BY orden", PgImagenDb.class));
        result.setPortales(bdFnt.getDataList("SELECT * FROM inmuebles_portal WHERE inmueble_id=" + inmuebleDb.getId(), PgInmueblePortalDb.class));
        result.setNoPortales(bdFnt.getDataList("SELECT * FROM inmuebles_portal_no WHERE inmueble_id=" + inmuebleDb.getId(), PgInmueblePortalDb.class));
        result.setCentralPortales(bdFnt.getDataList("SELECT * FROM inmuebles_portal_central WHERE inmueble_id=" + inmuebleDb.getId(), PgInmueblePortalDb.class));

        return result;
    }

    /**
     * @param agenciaDb
     * @return
     * @throws com.alfainmo.extra.AlfaException
     */
    protected PgAgenciaInfo cargarAgenciaInfo(PgAgenciaDb agenciaDb) throws AlfaException {
        PgAgenciaInfo result = new PgAgenciaInfo();
        result.setAgenciaDb(agenciaDb);
        result.setPortales(bdFnt.getDataList("SELECT * FROM agencias_portal WHERE agencia_id=" + agenciaDb.getId(), PgAgenciaPortalDb.class));

        return result;
    }
}

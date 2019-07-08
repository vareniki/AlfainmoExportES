package com.alfainmo.process;

import com.alfainmo.beans.PgAgenciaInfo;
import com.alfainmo.beans.PgInmuebleInfo;
import com.alfainmo.beans.MyAgenciaDb;
import com.alfainmo.beans.MyAgenciaPortalDb;
import com.alfainmo.beans.MyChaletDb;
import com.alfainmo.beans.MyGarajeDb;
import com.alfainmo.beans.MyImagenDb;
import com.alfainmo.beans.MyInmuebleDb;
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
import com.alfainmo.beans.PgAgenciaDb;
import com.alfainmo.beans.PgAgenciaPortalDb;
import com.alfainmo.beans.PgChaletTipoPuerta;
import com.alfainmo.beans.PgChaletTipoSuelo;
import com.alfainmo.beans.PgChaletTipoVentana;
import com.alfainmo.beans.PgImagenDb;
import com.alfainmo.beans.PgInmuebleDb;
import com.alfainmo.beans.PgInmueblePortalDb;
import com.alfainmo.beans.PgLocalTipoPuerta;
import com.alfainmo.beans.PgLocalTipoSuelo;
import com.alfainmo.beans.PgLocalTipoVentana;
import com.alfainmo.beans.PgNaveTipoPuerta;
import com.alfainmo.beans.PgNaveTipoSuelo;
import com.alfainmo.beans.PgNaveTipoVentana;
import com.alfainmo.beans.PgOficinaTipoPuerta;
import com.alfainmo.beans.PgOficinaTipoSuelo;
import com.alfainmo.beans.PgOficinaTipoVentana;
import com.alfainmo.beans.PgPisoTipoPuerta;
import com.alfainmo.beans.PgPisoTipoSuelo;
import com.alfainmo.beans.PgPisoTipoVentana;
import com.alfainmo.extra.AlfaException;
import com.alfainmo.util.BdUtils;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.alfainmo.util.FmtUtils;
import org.apache.commons.beanutils.BeanUtils;

/**
 * @author dmonje
 */
public class Export2Web extends AbstractExport2Web {

    public Export2Web(BdUtils bdFnt, BdUtils bdDst) {
        super(bdFnt, bdDst);
    }

    /**
     * @throws AlfaException
     */
    public void purgarInfoObsoleta() throws AlfaException {
        System.out.println("Purgando información obsoleta. Marca: " +  marcaAct);

        // Elimina los obsoletos de la nueva Web
        bdDst.executeUpdate("DELETE FROM inmuebles"
                + " WHERE (marca_act IS NULL OR marca_act <> '" + marcaAct + "')");

        bdDst.executeUpdate("DELETE FROM agencias"
                + " WHERE (marca_act IS NULL OR marca_act <> '" + marcaAct + "')");

        bdDst.executeUpdate("DELETE FROM agencias_portal"
                + " WHERE (marca_act IS NULL OR marca_act <> '" + marcaAct + "')");

        bdDst.executeUpdate("DELETE FROM imagenes"
                + " WHERE (marca_act IS NULL OR marca_act <> '" + marcaAct + "')");

        bdDst.executeUpdate("DELETE FROM inmuebles_portal"
                + " WHERE (marca_act IS NULL OR marca_act <> '" + marcaAct + "')");

        bdDst.executeUpdate("DELETE FROM inmuebles_portal_no"
                + " WHERE (marca_act IS NULL OR marca_act <> '" + marcaAct + "')");

        bdDst.executeUpdate("DELETE FROM inmuebles_portal_central"
                + " WHERE (marca_act IS NULL OR marca_act <> '" + marcaAct + "')");

        bdDst.executeUpdate("DELETE FROM inmuebles_tipo_puerta"
                + " WHERE (marca_act IS NULL OR marca_act <> '" + marcaAct + "')");

        bdDst.executeUpdate("DELETE FROM inmuebles_tipo_suelo"
                + " WHERE (marca_act IS NULL OR marca_act <> '" + marcaAct + "')");

        bdDst.executeUpdate("DELETE FROM inmuebles_tipo_ventana"
                + " WHERE (marca_act IS NULL OR marca_act <> '" + marcaAct + "')");

        System.out.println("OK");

        bdDst.commit();
    }

    /**
     *
     * @param inmuebleInfo
     * @return
     */
    private static String getVisibleEnWeb(PgInmuebleInfo inmuebleInfo) {

        PgInmuebleDb inmuebleDb = inmuebleInfo.getInmuebleDb();

        // Si el país es España comprueba que el inmueble
        if (inmuebleDb.getPais_id() == 34
                && (inmuebleDb.getEs_promocion() == null || inmuebleDb.getEs_promocion().equals("0"))
                && inmuebleDb.getEs_alquiler().equals("0")) {

            if (inmuebleDb.getRegistro_de() == null || inmuebleDb.getRegistro_de().isEmpty()
                    || inmuebleDb.getRegistro_finca() == null || inmuebleDb.getRegistro_finca() == 0
                    || inmuebleDb.getRegistro_folio() == null || inmuebleDb.getRegistro_folio() == 0
                    || inmuebleDb.getRegistro_libro() == null || inmuebleDb.getRegistro_libro() == 0
                    || inmuebleDb.getRegistro_numero() == null || inmuebleDb.getRegistro_numero() == 0
                    || inmuebleDb.getRegistro_tomo() == null || inmuebleDb.getRegistro_tomo() == 0) {

                System.out.println("La referencia " + inmuebleDb.getNumero_agencia() + "/" + inmuebleDb.getCodigo() + " no tiene datos registrales.");

                if (inmuebleDb.getTipo_contrato_id() != null && (inmuebleDb.getTipo_contrato_id().equals("AI") || inmuebleDb.getTipo_contrato_id().equals("PV"))) {
                    return "p";
                } else {
                    return "f";
                }

            }
        }

        boolean vpoEnWeb = false;
        for (PgInmueblePortalDb inmueblePortal : inmuebleInfo.getCentralPortales()) {
            if (inmueblePortal.getPortal_id().equals("06")) {
                System.out.println("La referencia " + inmuebleDb.getNumero_agencia() + "/" + inmuebleDb.getCodigo() + " no está autorizada por Central.");
                return "f";
            } else if (inmueblePortal.getPortal_id().equals("10")) {
                vpoEnWeb = true;
            }
        }

        if (vpoEnWeb == false && inmuebleInfo.getFieldValue("es_vpo").equals("t")) {
            System.out.println("La referencia " + inmuebleDb.getNumero_agencia() + "/" + inmuebleDb.getCodigo() + " es VPO.");
            return "f";
        }

        if (inmuebleDb.getTipo_contrato_id() != null) {
            switch (inmuebleDb.getTipo_contrato_id()) {
                case "PV":
                    return "p";
                case "EV":
                    return "v";
                case "AI":
                    return "i";
                default:
                    break;
            }
        }

        return "t";
    }

    private void annadirTipoPuerta(int inmuebleId, String tipoPuertaId, String marcaAct) throws AlfaException {
        MyInmuebleTipoPuerta tipoPuertaDst = new MyInmuebleTipoPuerta();
        tipoPuertaDst.setInmueble_id(inmuebleId);
        tipoPuertaDst.setTipo_puerta_id(tipoPuertaId);
        tipoPuertaDst.setMarca_act(marcaAct);
        bdDst.replaceData("inmuebles_tipo_puerta", tipoPuertaDst);
    }

    private void annadirTipoVentana(int inmuebleId, String tipoVentanaId, String marcaAct) throws AlfaException {
        MyInmuebleTipoVentana tipoVentanaDst = new MyInmuebleTipoVentana();
        tipoVentanaDst.setInmueble_id(inmuebleId);
        tipoVentanaDst.setTipo_ventana_id(tipoVentanaId);
        tipoVentanaDst.setMarca_act(marcaAct);
        bdDst.replaceData("inmuebles_tipo_ventana", tipoVentanaDst);
    }

    private void annadirTipoSuelo(int inmuebleId, String tipoSueloId, String marcaAct) throws AlfaException {
        MyInmuebleTipoSuelo tipoSueloDst = new MyInmuebleTipoSuelo();
        tipoSueloDst.setInmueble_id(inmuebleId);
        tipoSueloDst.setTipo_suelo_id(tipoSueloId);
        tipoSueloDst.setMarca_act(marcaAct);
        bdDst.replaceData("inmuebles_tipo_suelo", tipoSueloDst);
    }

    /**
     * @param agenciaDb
     * @throws AlfaException
     */
    public void export2InfoAgencia(PgAgenciaDb agenciaDb) throws AlfaException {

        PgAgenciaInfo agenciaInfo = cargarAgenciaInfo(agenciaDb);

        boolean visibleEnWeb = true;

        // Exporta la agencia, portales a los que no debe exportar la agencia y sus inmuebles
        MyAgenciaDb agenciaDst = new MyAgenciaDb();
        try {
            BeanUtils.copyProperties(agenciaDst, agenciaDb);
            agenciaDst.setMarca_act(marcaAct);

            bdDst.replaceData("agencias", agenciaDst);

            for (PgAgenciaPortalDb agenciaPortalDb : agenciaInfo.getPortales()) {
                MyAgenciaPortalDb agenciaPortalDst = new MyAgenciaPortalDb();
                BeanUtils.copyProperties(agenciaPortalDst, agenciaPortalDb);
                agenciaPortalDst.setMarca_act(marcaAct);
                bdDst.replaceData("agencias_portal", agenciaPortalDst);

                if (agenciaPortalDb.getPortal_id().equals("06")) {
                    visibleEnWeb = false;
                }
            }

            bdDst.commit();

        } catch (InvocationTargetException | IllegalAccessException e) {

            bdDst.rollback();

            System.out.println("Error en agencia " + agenciaDb.getNumero_agencia());
            e.printStackTrace();
        }

        if (agenciaDb.getSolo_central() != null && agenciaDb.getSolo_central().equalsIgnoreCase("t")
                && (agenciaDb.getCentral_web() == null || !agenciaDb.getCentral_web().equalsIgnoreCase("t"))) {

            return;
        }

        final SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd");

        // Carga los inmuebles que estén dados de alta tan sÛlo, y que est·n captados
        String sql = "SELECT * FROM inmuebles WHERE agencia_id=%d"
                + " AND fecha_captacion IS NOT NULL AND fecha_baja IS NULL AND motivo_baja_id IS NULL AND estado_inmueble_id='02' ABD ";

        List<PgInmuebleDb> inmueblesDb = bdFnt.getDataList(String.format(sql, agenciaDb.getId()), PgInmuebleDb.class);
        for (PgInmuebleDb inmuebleDb : inmueblesDb) {

            PgInmuebleInfo inmuebleInfo = cargarInmuebleInfo(inmuebleDb);

            MyInmuebleDb inmuebleDst = new MyInmuebleDb();
            try {
                BeanUtils.copyProperties(inmuebleDst, inmuebleDb);

                if (inmuebleDb.getDescripcion() != null) {
                    inmuebleDst.setDescripcion(FmtUtils.filterNonUtf8Chars(inmuebleDb.getDescripcion()));
                }

                inmuebleDst.setMarca_act(marcaAct);
                inmuebleDst.setModified(dt.format(inmuebleDb.getModified()));
                if (visibleEnWeb) {
                    inmuebleDst.setWeb(getVisibleEnWeb(inmuebleInfo));
                } else {
                    inmuebleDst.setWeb("f");
                }

                bdDst.replaceData("inmuebles", inmuebleDst);

                //
                // Pisos
                //
                if (inmuebleInfo.getPisoDb() != null) {
                    MyPisoDb pisoDst = new MyPisoDb();
                    BeanUtils.copyProperties(pisoDst, inmuebleInfo.getPisoDb());
                    bdDst.replaceData("pisos", pisoDst);

                    if (inmuebleInfo.getPisoTiposPuerta() != null) {
                        for (PgPisoTipoPuerta tipoPuerta : inmuebleInfo.getPisoTiposPuerta()) {
                            annadirTipoPuerta(inmuebleDb.getId(), tipoPuerta.getTipo_puerta_id(), marcaAct);
                        }
                    }
                    if (inmuebleInfo.getPisoTiposSuelo() != null) {
                        for (PgPisoTipoSuelo tipoSuelo : inmuebleInfo.getPisoTiposSuelo()) {
                            annadirTipoSuelo(inmuebleDb.getId(), tipoSuelo.getTipo_suelo_id(), marcaAct);
                        }
                    }
                    if (inmuebleInfo.getPisoTiposVentana() != null) {
                        for (PgPisoTipoVentana tipoVentana : inmuebleInfo.getPisoTiposVentana()) {
                            annadirTipoVentana(inmuebleDb.getId(), tipoVentana.getTipo_ventana_id(), marcaAct);
                        }
                    }
                }
                //
                // Chalet
                //
                if (inmuebleInfo.getChaletDb() != null) {
                    MyChaletDb chaletDst = new MyChaletDb();
                    BeanUtils.copyProperties(chaletDst, inmuebleInfo.getChaletDb());
                    bdDst.replaceData("chalets", chaletDst);

                    if (inmuebleInfo.getChaletTiposPuerta() != null) {
                        for (PgChaletTipoPuerta tipoPuerta : inmuebleInfo.getChaletTiposPuerta()) {
                            annadirTipoPuerta(inmuebleDb.getId(), tipoPuerta.getTipo_puerta_id(), marcaAct);
                        }
                    }
                    if (inmuebleInfo.getChaletTiposSuelo() != null) {
                        for (PgChaletTipoSuelo tipoSuelo : inmuebleInfo.getChaletTiposSuelo()) {
                            annadirTipoSuelo(inmuebleDb.getId(), tipoSuelo.getTipo_suelo_id(), marcaAct);
                        }
                    }
                    if (inmuebleInfo.getChaletTiposVentana() != null) {
                        for (PgChaletTipoVentana tipoVentana : inmuebleInfo.getChaletTiposVentana()) {
                            annadirTipoVentana(inmuebleDb.getId(), tipoVentana.getTipo_ventana_id(), marcaAct);
                        }
                    }
                }
                //
                // Local
                //
                if (inmuebleInfo.getLocalDb() != null) {
                    MyLocalDb localDst = new MyLocalDb();
                    BeanUtils.copyProperties(localDst, inmuebleInfo.getLocalDb());
                    bdDst.replaceData("locales", localDst);

                    if (inmuebleInfo.getLocalTiposPuerta() != null) {
                        for (PgLocalTipoPuerta tipoPuerta : inmuebleInfo.getLocalTiposPuerta()) {
                            annadirTipoPuerta(inmuebleDb.getId(), tipoPuerta.getTipo_puerta_id(), marcaAct);
                        }
                    }
                    if (inmuebleInfo.getLocalTiposSuelo() != null) {
                        for (PgLocalTipoSuelo tipoSuelo : inmuebleInfo.getLocalTiposSuelo()) {
                            annadirTipoSuelo(inmuebleDb.getId(), tipoSuelo.getTipo_suelo_id(), marcaAct);
                        }
                    }
                    if (inmuebleInfo.getLocalTiposVentana() != null) {
                        for (PgLocalTipoVentana tipoVentana : inmuebleInfo.getLocalTiposVentana()) {
                            annadirTipoVentana(inmuebleDb.getId(), tipoVentana.getTipo_ventana_id(), marcaAct);
                        }
                    }
                }
                //
                // Oficina
                //
                if (inmuebleInfo.getOficinaDb() != null) {
                    MyOficinaDb oficinaDst = new MyOficinaDb();
                    BeanUtils.copyProperties(oficinaDst, inmuebleInfo.getOficinaDb());
                    bdDst.replaceData("oficinas", oficinaDst);

                    if (inmuebleInfo.getOficinaTiposPuerta() != null) {
                        for (PgOficinaTipoPuerta tipoPuerta : inmuebleInfo.getOficinaTiposPuerta()) {
                            annadirTipoPuerta(inmuebleDb.getId(), tipoPuerta.getTipo_puerta_id(), marcaAct);
                        }
                    }
                    if (inmuebleInfo.getOficinaTiposSuelo() != null) {
                        for (PgOficinaTipoSuelo tipoSuelo : inmuebleInfo.getOficinaTiposSuelo()) {
                            annadirTipoSuelo(inmuebleDb.getId(), tipoSuelo.getTipo_suelo_id(), marcaAct);
                        }
                    }
                    if (inmuebleInfo.getOficinaTiposVentana() != null) {
                        for (PgOficinaTipoVentana tipoVentana : inmuebleInfo.getOficinaTiposVentana()) {
                            annadirTipoVentana(inmuebleDb.getId(), tipoVentana.getTipo_ventana_id(), marcaAct);
                        }
                    }
                }
                //
                // Garaje
                //
                if (inmuebleInfo.getGarajeDb() != null) {
                    MyGarajeDb garajeDst = new MyGarajeDb();
                    BeanUtils.copyProperties(garajeDst, inmuebleInfo.getGarajeDb());
                    bdDst.replaceData("garajes", garajeDst);
                }
                //
                // Terreno
                //
                if (inmuebleInfo.getTerrenoDb() != null) {
                    MyTerrenoDb terrenoDst = new MyTerrenoDb();
                    BeanUtils.copyProperties(terrenoDst, inmuebleInfo.getTerrenoDb());
                    bdDst.replaceData("terrenos", terrenoDst);
                }
                //
                // Nave
                //
                if (inmuebleInfo.getNaveDb() != null) {
                    MyNaveDb naveDst = new MyNaveDb();
                    BeanUtils.copyProperties(naveDst, inmuebleInfo.getNaveDb());
                    bdDst.replaceData("naves", naveDst);

                    if (inmuebleInfo.getNaveTiposPuerta() != null) {
                        for (PgNaveTipoPuerta tipoPuerta : inmuebleInfo.getNaveTiposPuerta()) {
                            annadirTipoPuerta(inmuebleDb.getId(), tipoPuerta.getTipo_puerta_id(), marcaAct);
                        }
                    }
                    if (inmuebleInfo.getNaveTiposSuelo() != null) {
                        for (PgNaveTipoSuelo tipoSuelo : inmuebleInfo.getNaveTiposSuelo()) {
                            annadirTipoSuelo(inmuebleDb.getId(), tipoSuelo.getTipo_suelo_id(), marcaAct);
                        }
                    }
                    if (inmuebleInfo.getNaveTiposVentana() != null) {
                        for (PgNaveTipoVentana tipoVentana : inmuebleInfo.getNaveTiposVentana()) {
                            annadirTipoVentana(inmuebleDb.getId(), tipoVentana.getTipo_ventana_id(), marcaAct);
                        }
                    }
                }
                //
                // Otro
                //
                if (inmuebleInfo.getOtroDb() != null) {
                    MyOtroDb otroDst = new MyOtroDb();
                    BeanUtils.copyProperties(otroDst, inmuebleInfo.getOtroDb());
                    bdDst.replaceData("otros", otroDst);
                }
                if (inmuebleInfo.getPropietarioDb() != null) {
                    MyPropietarioDb propietarioDst = new MyPropietarioDb();
                    BeanUtils.copyProperties(propietarioDst, inmuebleInfo.getPropietarioDb());
                    bdDst.replaceData("propietarios", propietarioDst);
                }
                if (inmuebleInfo.getImagenes() != null) {

                    boolean primeraImagen = true;
                    for (PgImagenDb imagenDb : inmuebleInfo.getImagenes()) {

                        MyImagenDb imagenDst = new MyImagenDb();
                        BeanUtils.copyProperties(imagenDst, imagenDb);
                        if (primeraImagen && !imagenDb.getTipo_imagen_id().equals("07")) {
                            imagenDst.setOrden(-1);
                            primeraImagen = false;
                        }
                        imagenDst.setMarca_act(marcaAct);
                        bdDst.replaceData("imagenes", imagenDst);
                    }
                } else {
                    System.out.println("Sin imágenes en referencia " + inmuebleDb.getNumero_agencia() + "/" + inmuebleDb.getCodigo());
                }
                if (inmuebleInfo.getPortales() != null) {
                    for (PgInmueblePortalDb inmueblePortalDb : inmuebleInfo.getPortales()) {
                        MyInmueblePortalDb inmueblePortalDst = new MyInmueblePortalDb();
                        BeanUtils.copyProperties(inmueblePortalDst, inmueblePortalDb);
                        inmueblePortalDst.setMarca_act(marcaAct);
                        bdDst.replaceData("inmuebles_portal", inmueblePortalDst);
                    }
                }
                if (inmuebleInfo.getNoPortales() != null) {
                    for (PgInmueblePortalDb inmuebleNoPortalDb : inmuebleInfo.getNoPortales()) {
                        MyInmueblePortalDb inmuebleNoPortalDst = new MyInmueblePortalDb();
                        BeanUtils.copyProperties(inmuebleNoPortalDst, inmuebleNoPortalDb);
                        inmuebleNoPortalDst.setMarca_act(marcaAct);
                        bdDst.replaceData("inmuebles_portal_no", inmuebleNoPortalDst);
                    }
                }
                if (inmuebleInfo.getCentralPortales() != null) {
                    for (PgInmueblePortalDb inmuebleCentralPortalDb : inmuebleInfo.getCentralPortales()) {
                        MyInmueblePortalDb inmuebleCentralPortalDst = new MyInmueblePortalDb();
                        BeanUtils.copyProperties(inmuebleCentralPortalDst, inmuebleCentralPortalDb);
                        inmuebleCentralPortalDst.setMarca_act(marcaAct);
                        bdDst.replaceData("inmuebles_portal_central", inmuebleCentralPortalDst);
                    }
                }

                bdDst.commit();

            } catch (AlfaException | InvocationTargetException | IllegalAccessException e) {
                bdDst.rollback();
                System.out.println("Error en referencia " + inmuebleDb.getNumero_agencia() + "/" + inmuebleDb.getCodigo());
                e.printStackTrace();
            }
        }

        System.out.println("Eliminamos obsoletos...");

        bdDst.executeUpdate("DELETE FROM inmuebles"
                + " WHERE agencia_id=" + agenciaDb.getId() + " AND (marca_act IS NULL OR marca_act <> '" + marcaAct + "')");

        System.out.println("OK");
    }
}

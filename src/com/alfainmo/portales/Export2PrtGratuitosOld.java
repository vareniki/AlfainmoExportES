package com.alfainmo.portales;

import com.alfainmo.beans.MyAgenciaDb;
import com.alfainmo.beans.MyImagenDb;
import com.alfainmo.beans.MyInmuebleDb;
import com.alfainmo.beans.MyInmuebleDbPortal;
import com.alfainmo.beans.MyInmuebleInfo;
import com.alfainmo.extra.AlfaException;
import com.alfainmo.extra.MyInmueblesAux;
import com.alfainmo.util.BdUtils;
import com.alfainmo.util.FmtUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Export2PrtGratuitosOld extends AbstractExport2PrtPago {

    private final MyInmueblesAux tbAux;

    public Export2PrtGratuitosOld(BdUtils bdUtils, String pathDestino) throws AlfaException {
        super(bdUtils, pathDestino, "temp/alfainmo-inmuebles-old");
        this.tbAux = new MyInmueblesAux(bdUtils);
    }

    public AbstractExport2Prt exportar() throws AlfaException {

        crearDocumento(true);
        try {
            addCabeceraGeneral();
            addCabecera("inmuebles");
            for (MyAgenciaDb myAgenciaDb : getAgencias()) {
                for (MyInmuebleInfo inmueble : getInmuebles(myAgenciaDb)) {
                    generarInmueble(inmueble);
                }
            }
            addPie("inmuebles");
        } finally {
            terminarDocumento();
        }
        return this;
    }

    public String getDocumentoAct() {
        return documentoAct;
    }

    /**
     *
     * @param inmueble
     * @throws AlfaException
     */
    protected void generarInmueble(MyInmuebleInfo inmueble) throws AlfaException {

        MyInmuebleDb inmuebleDb = inmueble.getInmuebleDb();

        String supConstruida = inmueble.getFieldValue("area_total_construida");
        if (supConstruida.isEmpty()) {
            supConstruida = inmueble.getFieldValue("area_total");
        }

        String estadoConservacion = "";
        switch (inmueble.getFieldValue("estado_conservacion_id")) {
            case "01":
                estadoConservacion = "A reformar";
                break;
            case "02":
                estadoConservacion = "En buen estado";
                break;
            case "03":
                estadoConservacion = "A estrenar";
                break;
        }

        try {
            writer.write("\r\n<inmueble>");

            String fotos="";
            String planos="";
            for (MyImagenDb imagenDb : inmueble.getImagenes()) {

                String img = imagenDb.getPath() + "/" + imagenDb.getFichero();
                if (imagenDb.getTipo_imagen_id().equals("07")) {
                    planos += "," + img;
                } else {
                    fotos += "," + img;
                }

            }
            if (!fotos.isEmpty())
                fotos = fotos.substring(1);
            if (!planos.isEmpty())
                planos = planos.substring(1);

            writer.write("<guids_planos>" + planos + "</guids_planos>");
            writer.write("<guids_fotos>" + fotos + "</guids_fotos>");

            writer.write("<oficina>" + inmuebleDb.getNumero_agencia() + "</oficina>");
            writer.write("<codigo>" + inmuebleDb.getCodigo() + "</codigo>");
            writer.write(MessageFormat.format("<fecha_alta>{0}</fecha_alta>", inmuebleDb.getFecha_captacion()));

            writer.write(MessageFormat.format("<id_tipo>{0}</id_tipo>", inmuebleDb.getTipo_inmueble_id()));
            writer.write(MessageFormat.format("<tipo>{0}</tipo>", convertTipoInmueble(inmueble)));
            writer.write("<subtipo></subtipo>");
            writer.write(MessageFormat.format("<id_pais>{0}</id_pais>", "1"));
            writer.write("<pais></pais>");
            writer.write("<id_provincia></id_provincia>");
            writer.write("<provincia></provincia>");

            writer.write("<id_poblacion></id_poblacion>");
            writer.write(MessageFormat.format("<poblacion>{0}</poblacion>", inmuebleDb.getPoblacion()));
            writer.write(MessageFormat.format("<cp>{0}</cp>", inmuebleDb.getCodigo_postal()));
            writer.write(MessageFormat.format("<zona>{0}</zona>", inmuebleDb.getZona()));

            writer.write("<tipo_via/>");
            writer.write("<nombre_via/>");
            writer.write("<numero_via/>");
            writer.write("<escalera/>");
            writer.write("<nivel/>");
            writer.write("<puerta/>");
            if (inmuebleDb.getEs_venta().equals("t")) {
                writer.write("<venta>1</venta>");
            } else {
                writer.write("<venta>0</venta>");
            }
            if (inmuebleDb.getEs_alquiler().equals("t")) {
                writer.write("<alquiler>1</alquiler>");
            } else {
                writer.write("<alquiler>0</alquiler>");
            }
            if (inmuebleDb.getEs_traspaso().equals("t")) {
                writer.write("<traspaso>1</traspaso>");
            } else {
                writer.write("<traspaso>0</traspaso>");
            }
            writer.write(MessageFormat.format("<nuevo>{0}</nuevo>", inmuebleDb.getEs_promocion() != null && inmuebleDb.getEs_promocion().equals("t") ));
            if (inmueble.getFieldValue("tipo_equipamiento_id").equals("03")) {
                writer.write("<amueblado>1</amueblado>");
            } else {
                writer.write("<amueblado>0</amueblado>");
            }
            if (inmueble.getFieldValue("es_vpo").equals("t")) {
                writer.write("<vpo>1</vpo>");
            } else {
                writer.write("<vpo>0</vpo>");
            }
            String anioConstruccion = inmueble.getFieldValue("anio_construccion");
            if (!anioConstruccion.isEmpty()) {
                try {
                    Integer iAnios = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(anioConstruccion);
                    writer.write(MessageFormat.format("<annos>{0}</annos>", iAnios));
                } catch (NumberFormatException e) {
                }
            } else {
                writer.write("<annos/>");
            }
            writer.write("<subasta/>");
            writer.write("<p_venta_act>" + Double.valueOf(inmuebleDb.getPrecio_venta()).longValue() + "</p_venta_act>");
            writer.write("<p_alq_act>" + Double.valueOf(inmuebleDb.getPrecio_alquiler()).longValue() + "</p_alq_act>");
            writer.write("<p_traspaso_act>" + Double.valueOf(inmuebleDb.getPrecio_traspaso()).longValue() + "</p_traspaso_act>");
            writer.write("<tipo_moneda>1</tipo_moneda>");

            writer.write("<m_const>" + supConstruida + "</m_const>");
            writer.write("<m_util>" + inmueble.getFieldValue("area_total_util") + "</m_util>");
            writer.write("<m_altura/>");
            writer.write(MessageFormat.format("<m_fachada>{0}</m_fachada>", inmueble.getFieldValue("m_lineales_fachada")));
            writer.write("<m_oficinas/>");
            writer.write(MessageFormat.format("<m_parcela>{0}</m_parcela>", inmueble.getFieldValue("area_parcela")));
            writer.write("<m_sotano/>");
            if (inmueble.getFieldValue("con_trastero").equals("t")) {
                writer.write("<trastero>1</trastero>");
            } else {
                writer.write("<trastero>0</trastero>");
            }
            writer.write("<tipo_portal/>");

            writer.write("<tipo_salon_comedor/>");
            writer.write("<tipo_suelo/>");
            writer.write("<tipo_carp_int/>");
            writer.write("<tipo_carp_ext/>");
            writer.write("<tipo_puerta/>");
            writer.write("<tipo_calefaccion/>");
            writer.write("<tipo_agua_caliente/>");
            writer.write("<tipo_seguridad/>");
            writer.write("<tipo_cocina/>");

            writer.write(MessageFormat.format("<tipo_condiciones>{0}</tipo_condiciones>", estadoConservacion));
            if (!inmueble.getFieldValue("plazas_parking").isEmpty() && !inmueble.getFieldValue("plazas_parking").equals("0")) {
                writer.write(MessageFormat.format("<parking_plazas>{0}</parking_plazas>", inmueble.getFieldValue("plazas_parking")));
            } else {
                writer.write("<parking_plazas/>");
            }
            String numeroArmarios = inmueble.getFieldValue("numero_armarios");
            if (!numeroArmarios.isEmpty() && !numeroArmarios.equals("0")) {
                writer.write(MessageFormat.format("<n_armarios_emp>{0}</n_armarios_emp>", numeroArmarios));
            } else {
                writer.write("<n_armarios_emp/>");
            }
            writer.write(MessageFormat.format("<n_aseos>{0}</n_aseos>", inmueble.getFieldValue("numero_aseos")));
            writer.write(MessageFormat.format("<n_bannos>{0}</n_bannos>", inmueble.getFieldValue("numero_banos")));

            writer.write("<n_despachos/>");
            writer.write(MessageFormat.format("<n_dormitorios>{0}</n_dormitorios>", inmueble.getFieldValue("numero_habitaciones")));
            writer.write("<puerta_servicio/>");

            if (!inmueble.getFieldValue("numero_ascensores").isEmpty() && !inmueble.getFieldValue("numero_ascensores").equals("0")) {
                writer.write("<ascensor>1</ascensor>");
            } else {
                writer.write("<ascensor>0</ascensor>");
            }
            if (inmueble.getFieldValue("con_piscina").equals("t")) {
                writer.write("<piscina>1</piscina>");
            } else {
                writer.write("<piscina>0</piscina>");
            }
            if (inmueble.getFieldValue("con_tenis").equals("t")) {
                writer.write("<tenis>1</tenis>");
            } else {
                writer.write("<tenis>0</tenis>");
            }
            if (!inmueble.getFieldValue("tipo_aa_id").isEmpty()) {
                writer.write("<aire_acondic>1</aire_acondic>");
            } else {
                writer.write("<aire_acondic>0</aire_acondic>");
            }

            writer.write("<jardin_comun/>");
            writer.write("<jardin_individual/>");
            if (!inmueble.getFieldValue("subtipo_calefaccion").isEmpty()) {
                writer.write("<calefaccion>1</calefaccion>");
            } else {
                writer.write("<calefaccion>0</calefaccion>");
            }
            writer.write("<mostradores/>");
            writer.write("<montacargas/>");
            writer.write("<salida_humos/>");

            writer.write(MessageFormat.format("<observaciones><![CDATA[{0}]]></observaciones>", inmuebleDb.getDescripcion()));
            int opcionCompra = 0;
            if (inmuebleDb.getEs_opcion_compra() != null && inmuebleDb.getEs_opcion_compra().equals("t")) {
                opcionCompra = 1;
            }
            writer.write(MessageFormat.format("<opcioncompra>{0}</opcioncompra>", "" + opcionCompra));

            if (inmueble.getFieldValue("calificacion_energetica_id") != null) {
                String result;
                switch (inmueble.getFieldValue("calificacion_energetica_id")) {
                    case "00":
                        result = "EN TRAMITE";
                        break;
                    case "02":
                        result = "A";
                        break;
                    case "03":
                        result = "B";
                        break;
                    case "05":
                        result = "C";
                        break;
                    case "06":
                        result = "D";
                        break;
                    case "07":
                        result = "E";
                        break;
                    case "08":
                        result = "F";
                        break;
                    case "09":
                        result = "G";
                        break;
                    default:
                        result = "NO DISPONIBLE";
                }
                writer.write(MessageFormat.format("<certificado>{0}</certificado>", result));
            } else {
                writer.write("<certificado/>");
            }

            writer.write("\n</inmueble>");

        } catch (IOException ex) {
            throw new AlfaException(ex);
        }

    }

    protected void addCabeceraGeneral() throws AlfaException {
        try {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
        } catch (IOException ex) {
            throw new AlfaException(ex);
        }
    }

    protected void addCabecera(String tabla) throws AlfaException {
        try {
            writer.write("<" + tabla + ">\r\n");
        } catch (IOException ex) {
            throw new AlfaException(ex);
        }
    }

    protected void addPie(String tabla) throws AlfaException {
        try {
            writer.write("</" + tabla + ">\r\n");
        } catch (IOException e) {
            throw new AlfaException(e);
        }
    }

    private String convertTipoInmueble(MyInmuebleInfo inmuebleInfo) {
        String result = "";

        switch (inmuebleInfo.getInmuebleDb().getTipo_inmueble_id()) {
            case "01": // Piso
                result = tbAux.getTiposPisoMap().get(inmuebleInfo.getPisoDb().getTipo_piso_id());
                if (result == null || result.isEmpty()) {
                    result = "Piso";
                } else {
                    result = result.substring(0, 1).toUpperCase() + result.substring(1);
                }
                break;
            case "02": // Chalet
                switch (inmuebleInfo.getChaletDb().getTipo_chalet_id()) {
                    case "02":
                        result = "Casa adosada";
                        break;
                    case "03":
                        result = "Casa unifamiliar";
                        break;
                    case "04":
                        result = "Casa pareada";
                        break;
                }
                if (result.isEmpty()) {
                    result = "Chalet";
                }
                break;
            case "03": // Local
                result = "Local comercial";
                break;
            case "04": // Oficina
                result = "Oficina";
                break;
            case "05": // Garaje
                result = "Parking";
                break;
            case "06": // Terreno
                result = "Terreno";
                break;
            case "07": // Nave
                result = "Nave industrial";
                break;
            case "08": // Otros
                result = "Otros";
                break;
        }

        return result;
    }

    private List<MyAgenciaDb> getAgencias() throws AlfaException {
        return bdUtils.getDataList("SELECT * FROM agencias WHERE pais_id='34' ORDER BY numero_agencia", MyAgenciaDb.class);
    }

    private List<MyInmuebleInfo> getInmuebles(MyAgenciaDb agenciaDb) throws AlfaException {

        List<MyInmuebleInfo> result = new ArrayList<>();

        // Carga los inmuebles que están dados de alta tan sólo, y que están captados
        String sql = "SELECT i.* FROM inmuebles i"
                + " LEFT JOIN inmuebles_portal_no ip ON ip.inmueble_id = i.id AND ip.portal_id='09'"
                + " WHERE i.pais_id = 34 AND i.web IN('t', 'i') AND ip.portal_id IS NULL "
                + " AND numero_agencia=" + agenciaDb.getNumero_agencia()
                + " ORDER BY i.codigo";

        for (MyInmuebleDb inmuebleDb : bdUtils.getDataList(sql, MyInmuebleDbPortal.class)) {
            result.add(cargarInmuebleInfo(inmuebleDb));
        }

        return result;
    }

}

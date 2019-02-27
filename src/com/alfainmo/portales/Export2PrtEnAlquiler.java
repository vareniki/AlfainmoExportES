package com.alfainmo.portales;

import com.alfainmo.beans.MyAgenciaDb;
import com.alfainmo.beans.MyImagenDb;
import com.alfainmo.beans.MyInmuebleDb;
import com.alfainmo.beans.MyInmuebleDbPortal;
import com.alfainmo.beans.MyInmuebleInfo;
import com.alfainmo.extra.AlfaException;
import com.alfainmo.extra.MyInmueblesAux;
import com.alfainmo.util.BdUtils;
import com.alfainmo.util.ConfigUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Export2PrtEnAlquiler extends AbstractExport2PrtPago {

    private final MyInmueblesAux tbAux;
    private final String pathImg;

    public Export2PrtEnAlquiler(BdUtils bdUtils, String pathDestino) throws AlfaException {
        super(bdUtils, pathDestino, "temp/alfainmo-enalquiler");
        this.tbAux = new MyInmueblesAux(bdUtils);
        this.pathImg = ConfigUtils.getInstance().getString("pathImagenes");
    }

    public AbstractExport2Prt exportar() throws AlfaException {

        crearDocumento("xml");
        try {
            addCabeceraGeneral();
            addCabecera("Inmuebles");
            for (MyAgenciaDb myAgenciaDb : getAgencias()) {
                for (MyInmuebleInfo inmueble : getInmuebles(myAgenciaDb)) {
                    generarInmueble(inmueble);
                }
            }
            addPie("Inmuebles");
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
            writer.write("\r\n<Inmueble>");

            writer.write("<oficina>" + inmuebleDb.getNumero_agencia() + "</oficina>");
            writer.write("<codigo>" + inmuebleDb.getCodigo() + "</codigo>");
            writer.write(MessageFormat.format("<fecha_alta>{0}</fecha_alta>", inmuebleDb.getFecha_captacion()));

            writer.write(MessageFormat.format("<id_tipo>{0}</id_tipo>", inmuebleDb.getTipo_inmueble_id()));
            writer.write(MessageFormat.format("<tipo>{0}</tipo>", convertTipoInmueble(inmueble)));
            writer.write(MessageFormat.format("<id_pais>{0}</id_pais>", "1"));

            writer.write(MessageFormat.format("<poblacion>{0}</poblacion>", inmuebleDb.getPoblacion()));
            writer.write(MessageFormat.format("<cp>{0}</cp>", inmuebleDb.getCodigo_postal()));
            writer.write(MessageFormat.format("<zona>{0}</zona>", inmuebleDb.getZona()));

            writer.write("<latitud>" + inmuebleDb.getCoord_x() + "</latitud>");
            writer.write("<longitud>" + inmuebleDb.getCoord_y() + "</longitud>");

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

            writer.write("<p_venta_act>" + Double.valueOf(inmuebleDb.getPrecio_venta()).longValue() + "</p_venta_act>");
            writer.write("<p_alq_act>" + Double.valueOf(inmuebleDb.getPrecio_alquiler()).longValue() + "</p_alq_act>");
            writer.write("<p_traspaso_act>" + Double.valueOf(inmuebleDb.getPrecio_traspaso()).longValue() + "</p_traspaso_act>");
            writer.write("<tipo_moneda>1</tipo_moneda>");

            String gastosComunidad = inmueble.getFieldValue("gastos_comunidad");
            if (!gastosComunidad.isEmpty()) {
                try {
                    Integer iGastos = Integer.parseInt(gastosComunidad);
                    writer.write(MessageFormat.format("<gastos_comunidad>{0}</gastos_comunidad>", iGastos));

                } catch (NumberFormatException e) {
                }
            }

            String anioConstruccion = inmueble.getFieldValue("anio_construccion").trim();
            if (!anioConstruccion.isEmpty()) {

                try {
                    Integer iAnioConstruccion = Integer.parseInt(anioConstruccion);
                    Integer iAnios = Calendar.getInstance().get(Calendar.YEAR) - iAnioConstruccion;
                    if (iAnios < 200) {
                        writer.write("<annos>" + iAnios + "</annos>");
                    }

                } catch(NumberFormatException e) {

                }
            }
            writer.write(MessageFormat.format("<estado_conservacion>{0}</estado_conservacion>", estadoConservacion));
            writer.write(MessageFormat.format("<piso>{0}</piso>", inmueble.getFieldValue("piso")));
            writer.write(MessageFormat.format("<plantas_edificio>{0}</plantas_edificio>", inmueble.getFieldValue("plantas_edificio")));

            writer.write(MessageFormat.format("<m_const>{0}</m_const>", supConstruida));
            writer.write(MessageFormat.format("<m_util>{0}</m_util>", inmueble.getFieldValue("area_total_util")));
            writer.write(MessageFormat.format("<m_fachada>{0}</m_fachada>", inmueble.getFieldValue("m_lineales_fachada")));
            writer.write(MessageFormat.format("<m_escaparate>{0}</m_escaparate>", inmueble.getFieldValue("m_lineales_escaparate")));
            writer.write(MessageFormat.format("<m_terraza>{0}</m_terraza>", inmueble.getFieldValue("area_terraza")));
            writer.write(MessageFormat.format("<m_parcela>{0}</m_parcela>", inmueble.getFieldValue("area_parcela")));

            writer.write(MessageFormat.format("<n_dormitorios>{0}</n_dormitorios>", inmueble.getFieldValue("numero_habitaciones")));
            writer.write(MessageFormat.format("<n_bannos>{0}</n_bannos>", inmueble.getFieldValue("numero_banos")));
            writer.write(MessageFormat.format("<n_aseos>{0}</n_aseos>", inmueble.getFieldValue("numero_aseos")));

            String numeroArmarios = inmueble.getFieldValue("numero_armarios");
            if (!numeroArmarios.isEmpty() && !numeroArmarios.equals("0")) {
                writer.write(MessageFormat.format("<n_armarios_emp>{0}</n_armarios_emp>", numeroArmarios));
            }

            if (inmueble.getFieldValue("con_trastero").equals("t")) {
                writer.write("<trastero>1</trastero>");
            }
            if (!inmueble.getFieldValue("plazas_parking").isEmpty() && !inmueble.getFieldValue("plazas_parking").equals("0")) {
                writer.write(MessageFormat.format("<parking_plazas>{0}</parking_plazas>", inmueble.getFieldValue("plazas_parking")));
            }
            if (!inmueble.getFieldValue("numero_ascensores").isEmpty() && !inmueble.getFieldValue("numero_ascensores").equals("0")) {
                writer.write("<ascensor>1</ascensor>");
            }
            if (inmueble.getFieldValue("con_piscina").equals("t")) {
                writer.write("<piscina>1</piscina>");
            }
            if (inmueble.getFieldValue("con_tenis").equals("t")) {
                writer.write("<tenis>1</tenis>");
            }
            if (!inmueble.getFieldValue("tipo_aa_id").isEmpty()) {
                writer.write("<aire_acondic>1</aire_acondic>");
            }
            if (!inmueble.getFieldValue("subtipo_calefaccion").isEmpty()) {
                writer.write("<calefaccion>1</calefaccion>");
            }

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
            }

            // Seguridad
            if (inmueble.getFieldValue("con_videovigilancia").equals("t")
                    || inmueble.getFieldValue("con_vigilancia_24h").equals("t")
                    || inmueble.getFieldValue("con_camaras_seguridad").equals("t")
                    || inmueble.getFieldValue("con_puerta_seguridad").equals("t")) {

                writer.write("<SistemaSeguridad_tiene>1</SistemaSeguridad_tiene>");

                StringBuilder sb = new StringBuilder();
                if (inmueble.getFieldValue("con_videovigilancia").equals("t")) {
                    sb.append(", con videovigilancia");
                }
                if (inmueble.getFieldValue("con_vigilancia_24h").equals("t")) {
                    sb.append(", vigilancia 24h");
                }
                if (inmueble.getFieldValue("con_camaras_seguridad").equals("t")) {
                    sb.append(", con cámaras de seguridad");
                }
                if (inmueble.getFieldValue("con_puerta_seguridad").equals("t")) {
                    sb.append(", con puerta blindada");
                }

                if (sb.length() > 2) {
                    sb.delete(0, 1);
                }
                writer.write(MessageFormat.format("<SistemaSeguridad_comentario>{0}</SistemaSeguridad_comentario>", sb.toString()));
            }

            writer.write(MessageFormat.format("<observaciones><![CDATA[{0}]]></observaciones>", inmuebleDb.getDescripcion()));

            // Fotos
            writer.write("<Fotos>");
            for (MyImagenDb imagenDb : inmueble.getImagenes()) {
                String img = pathImg + imagenDb.getPath() + "/g_" + imagenDb.getFichero();
                writer.write(MessageFormat.format("<Foto>{0}</Foto>", img));
            }
            writer.write("</Fotos>");

            // Vídeos
            if (inmuebleDb.getVideo() != null && !inmuebleDb.getVideo().isEmpty()) {
                writer.write("<Videos>");
                String videos[] = inmuebleDb.getVideo().split("\\n");

                for (String video : videos) {
                    if (video.toLowerCase().startsWith("http") && !video.toLowerCase().contains(",pvi")) {
                        writer.write("<Video><![CDATA[" + video + "]]></Video>");
                    }
                }
                writer.write("</Videos>");
            }

            writer.write("\n</Inmueble>");

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
                + " LEFT JOIN inmuebles_portal_no ip2 ON ip2.inmueble_id = i.id AND ip2.portal_id='12'"
                + " WHERE i.pais_id = 34 AND i.web IN('t', 'i') AND ip.portal_id IS NULL AND ip2.portal_id IS NULL"
                + " AND numero_agencia=" + agenciaDb.getNumero_agencia()
                + " ORDER BY i.codigo";

        for (MyInmuebleDb inmuebleDb : bdUtils.getDataList(sql, MyInmuebleDbPortal.class)) {
            result.add(cargarInmuebleInfo(inmuebleDb));
        }

        return result;
    }

}

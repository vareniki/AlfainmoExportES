package com.alfainmo.portales;

import com.alfainmo.beans.MyImagenDb;
import com.alfainmo.beans.MyInmuebleDb;
import com.alfainmo.beans.MyInmuebleDbPortal;
import com.alfainmo.beans.MyInmuebleInfo;
import com.alfainmo.beans.MyInmuebleTipoSuelo;
import com.alfainmo.extra.AlfaException;
import com.alfainmo.util.BdUtils;
import com.alfainmo.util.ConfigUtils;
import com.alfainmo.util.FmtUtils;
import com.alfainmo.util.FtpUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Export2PrtIdealista extends AbstractExport2PrtPago {

    private final int maxPisos;
    private final String pathImg;

    /**
     * @param inmueble
     * @return
     */
    private static String getTipoInmuebleStr(MyInmuebleInfo inmueble) {
        String result;
        switch (inmueble.getInmuebleDb().getTipo_inmueble_id()) {
            case "01": // Es un piso
                if (inmueble.getPisoDb().getTipo_piso_id() != null) {

                    switch (inmueble.getPisoDb().getTipo_piso_id()) {
                        case "01":
                            result = "Piso";
                            break;
                        case "02":
                            result = "Apartamento";
                            break;
                        case "03":
                            result = "Estudio";
                            break;
                        case "04":
                            result = "Loft";
                            break;
                        case "05":
                            result = "Dúplex";
                            break;
                        case "06":
                            result = "Ático";
                            break;
                        default:
                            result = "Piso";
                    }

                } else {
                    result = "Piso";
                }

                break;

            case "02":

                if (inmueble.getChaletDb().getTipo_chalet_id() != null) {

                    switch (inmueble.getChaletDb().getTipo_chalet_id()) {
                        case "01":
                            result = "Chalet";
                            break;
                        case "02":
                            result = "Chalet adosado";
                            break;
                        case "03":
                            result = "Chalet independiente";
                            break;
                        case "04":
                            result = "Chalet pareado";
                            break;

                        default:
                            result = "Chalet";
                    }

                } else {
                    result = "Chalet";
                }
                break;

            case "03":
                result = "Local";
                break;

            case "04":
                result = "Oficina";
                break;

            case "05":

                result = "Garaje";
                break;

            default:
                result = "";
                break;
        }

        return result;
    }

    /**
     *
     * @param inmuebleInfo
     * @return
     */
    private static String getSumario(MyInmuebleInfo inmuebleInfo) {

        MyInmuebleDb inmueble = inmuebleInfo.getInmuebleDb();

        StringBuilder result = new StringBuilder();
        result.append(getTipoInmuebleStr(inmuebleInfo));
        result.append(" en ");
        if (inmueble.getPoblacion() != null) {
            result.append(inmueble.getPoblacion()).append(" (").append(inmueble.getProvincia()).append(')');
        } else {
            result.append(inmueble.getProvincia());
        }
        return result.toString();
    }

    public Export2PrtIdealista(BdUtils bdUtils, String pathDestino) throws AlfaException {
        super(bdUtils, pathDestino, "IDE");
        this. maxPisos = ConfigUtils.getInstance().getInteger("maxInmueblesIdealista", 1000);
        this.pathImg = ConfigUtils.getInstance().getString("pathImagenesIdealista");
    }

    public AbstractExport2Prt exportar() throws AlfaException {

        crearDocumento("txt", "ISO-8859-1");
        try {
            for (MyInmuebleInfo inmueble : getInmuebles(maxPisos)) {
                generarInmueble(inmueble);
            }
        } finally {
            terminarDocumento();
        }
        return this;
    }

    /**
     *
     * @param inmuebleInfo
     * @throws AlfaException
     */
    protected void generarInmueble(MyInmuebleInfo inmuebleInfo) throws AlfaException {

        MyInmuebleDb inmueble = inmuebleInfo.getInmuebleDb();
        try {
            // Bloque para inmuebles
            writer.write("ilc6d8081617939d52cc5678ee06295e1e2727f43fd");  // 1
            writer.write("|");
            writer.write("2.02"); // 2
            writer.write("|");
            writer.write("ES");// 3
            writer.write("|");
            writer.write("P00001784"); // 4
            writer.write("|");

            switch (inmueble.getTipo_inmueble_id()) {
                case "01": // Piso
                case "02": // Chalet
                    writer.write("V");
                    break;
                case "03": // Local
                    writer.write("L");
                    break;
                case "04": // Oficina
                    writer.write("O");
                    break;
            }
            writer.write("|");

            // Precio de venta
            Integer precio;
            if (inmueble.getEs_venta().equals("t")) {
                precio = (int) inmueble.getPrecio_venta();
                writer.write("V"); // 6
            } else {
                precio = (int) inmueble.getPrecio_alquiler();
                writer.write("A"); // 6
            }

            writer.write("|");
            writer.write(String.format("%05d", inmueble.getCodigo()) + String.format("%04d", inmueble.getNumero_agencia()));    // 7
            writer.write("|");
            writer.write("");  // 8
            writer.write("|");
            writer.write(inmueble.getModified().substring(0, 4) + inmueble.getModified().substring(4, 6) + inmueble.getModified().substring(6, 8));  // 9
            writer.write("|");
            writer.write(FmtUtils.convertCharset(inmuebleInfo.getAgencia().getNombre_agencia()));    // 10
            writer.write("|");

            // Email provisional
            String email = (inmuebleInfo.getAgencia().getEmail_contacto() != null) ? inmuebleInfo.getAgencia().getEmail_contacto().replace("ñ", "n") : "";

            writer.write(email);   // 11
            writer.write("|");

            String telefono = (inmuebleInfo.getAgencia().getTelefono1_contacto() != null) ? inmuebleInfo.getAgencia().getTelefono1_contacto().replaceAll("[^0-9.]", "") : "";
            if (telefono.length() > 16) {
                telefono = telefono.substring(0, 16);
            }
            writer.write(telefono);   // 12
            writer.write("|");

            telefono = (inmuebleInfo.getAgencia().getTelefono2_contacto() != null) ? inmuebleInfo.getAgencia().getTelefono2_contacto().replaceAll("[^0-9.]", "") : "";
            if (telefono.length() > 16) {
                telefono = telefono.substring(0, 16);
            }
            writer.write(telefono);   // 13
            writer.write("|");
            writer.write("07"); // 14
            writer.write("|");
            writer.write(FmtUtils.notNull(inmueble.getCodigo_postal())); // 15
            writer.write("|");
            writer.write(""); // 16
            writer.write("|");
            writer.write(""); // 17
            writer.write("|");

            if (inmueble.getPoblacion() != null) {
                try {
                    writer.write(FmtUtils.convertCharset(inmueble.getPoblacion()));   // 18
                } catch (Exception e) {
                }
            }

            writer.write("|");
            writer.write("xxxx"); // Tipo de vía ... 19
            writer.write("|");
            writer.write(FmtUtils.notNull(inmueble.getNombre_calle()));    // 20
            writer.write("|");
            writer.write("" + inmueble.getNumero_calle());  // 21
            writer.write("|");
            writer.write("");     // 22
            writer.write("|");
            if (inmueble.getCoord_x() != 0 && inmueble.getCoord_y() != 0) {
                writer.write("" + inmueble.getCoord_x() + "," + inmueble.getCoord_y()); // 23
            }
            writer.write("|");
            writer.write("01");     // 24
            writer.write("|");
            writer.write((inmueble.getRef_catastral() != null) ? inmueble.getRef_catastral() : ""); // 25
            writer.write("|");
            writer.write("f");  // 26
            writer.write("|");
            writer.write(precio.toString()); // 27
            writer.write("|");
            writer.write(getSumario(inmuebleInfo)); // 28
            writer.write("|");

            String descripcion;
            if (inmueble.getDescripcion() != null && !inmueble.getDescripcion().trim().isEmpty()) {
                descripcion = inmueble.getDescripcion().trim();
            } else {
                descripcion = inmueble.getDescripcion_abreviada();
            }
            if (descripcion == null) {
                descripcion = "";
            }

            String observaciones = FmtUtils.limitarTexto(
                    "REF. " + inmueble.getNumero_agencia() + "/" + inmueble.getCodigo() + ". "
                            + ((inmuebleInfo.getFieldValue("es_vpo").equals("t")) ? ". El inmueble es VPO." : "")
                            + FmtUtils.htmlToPlainText(descripcion), 2400);

            writer.write(observaciones.replaceAll("|", "")); // 29
            writer.write("|");

            writer.write("https://www.alfainmo.com/referencia/" + inmueble.getId() + "/?ref=" + inmueble.getNumero_agencia() + "-" + inmueble.getCodigo());

            /*
            if (inmueble.getVideo() != null) {

                String videos[] = inmueble.getVideo().split("\\n");
                for (String video : videos) {
                    if (video.toLowerCase().startsWith("http") && !video.toLowerCase().contains(",pvi")) {
                        video = video.replaceAll("\r|\n", "");
                        writer.write(video);
                        break;
                    }
                }

            } else if (inmueble.getTour_virtual() != null) {

                String tours[] = inmueble.getTour_virtual().split("\\n");
                for (String tour : tours) {
                    if (tour.toLowerCase().startsWith("http") && !tour.toLowerCase().contains(",pvi")) {
                        tour = tour.replaceAll("\r|\n", "");
                        writer.write(tour);
                        break;
                    }
                }

            }*/ // 30
            writer.write("|");

            writer.write(inmuebleInfo.getFieldValue("calificacion_energetica_id"));
            writer.write("|");
            writer.write(""); // 32
            writer.write("|");
            writer.write(""); // 33
            writer.write("|");
            writer.write(""); // 34
            writer.write("|");

            switch (inmuebleInfo.getInmuebleDb().getTipo_inmueble_id()) {
                case "01":
                case "02":
                    bloqueViviendas(inmuebleInfo);
                    break;
                case "03":
                    bloqueLocales(inmuebleInfo);
                    break;
                case "04":
                    bloqueOficinas(inmuebleInfo);
                    break;
            }

            if (inmuebleInfo.getImagenes() != null) {
                for (MyImagenDb foto : inmuebleInfo.getImagenes()) {

                    String fotoPath = foto.getPath().replaceAll("/", "--") + "--";

                    if (foto.getTipo_imagen_id().equals("07")) {
                        // Es un plano
                        //writer.write(pathImg + foto.getPath() + "/" + foto.getFichero());
                        writer.write(pathImg + "o/" + fotoPath + foto.getFichero());
                        writer.write("|13|"); // Plano

                    } else {
                        // Es una foto
                        writer.write(pathImg + "x/" + fotoPath + foto.getFichero());
                        //writer.write(pathImg + foto.getPath() + "/g_" + foto.getFichero());

                        if (Integer.parseInt(foto.getTipo_imagen_id()) > 10) {
                            int tipo = Integer.parseInt(foto.getTipo_imagen_id()) - 10;
                            writer.write("|" + String.format("%02d", tipo) + "|");
                        } else {
                            writer.write("|XX|");
                        }

                    }
                }
            }
            writer.write("\r\n");

        } catch (IOException ex) {
            throw new AlfaException(ex);
        }
    }

    /**
     *
     * @param inmuebleInfo
     * @throws IOException
     */
    private void bloqueViviendas(MyInmuebleInfo inmuebleInfo) throws IOException {

        MyInmuebleDb inmueble = inmuebleInfo.getInmuebleDb();
        // Bloque para viviendas
        String subtipoVivienda;
        if (inmueble.getTipo_inmueble_id().equals("01")) {
            // Es un piso
            switch (inmuebleInfo.getPisoDb().getTipo_piso_id()) {
                case "03": // Estudio
                    subtipoVivienda = "ve";
                    break;
                case "05": // Dúplex
                    subtipoVivienda = "vd";
                    break;
                case "06": // Ático
                    subtipoVivienda = "va";
                    break;
                default: // El resto
                    subtipoVivienda = "vp";
                    break;
            }

        } else {
            // Es un chalet
            switch (inmuebleInfo.getChaletDb().getTipo_chalet_id()) {
                case "02":
                    subtipoVivienda = "vj";
                    break;
                case "04":
                    subtipoVivienda = "vo";
                    break;
                default:
                    subtipoVivienda = "vi";
                    break;
            }
        }
        writer.write(subtipoVivienda); // 01
        writer.write("|");
        writer.write("");  // 02
        writer.write("|");
        writer.write("");  // 03
        writer.write("|");

        switch (inmuebleInfo.getFieldValue("piso")) {

            case "00": // bajo
                writer.write("bj");
                break;

            case "X0": // entreplanta
                writer.write("en");
                break;

            case "X1": // semisótano
                writer.write("ss");
                break;

            case "X2": // sótano
                writer.write("st");
                break;

            default:
                writer.write(inmuebleInfo.getFieldValue("piso"));
                break;
        } // 04 - Planta de la vivienda

        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("puerta")); // 05
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("area_total_construida")); // 06
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("area_total_util"));  // 07
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("numero_habitaciones")); // 08
        writer.write("|");

        Integer bannosYAseos = 0;
        try {
            bannosYAseos = Integer.valueOf(inmuebleInfo.getFieldValue("numero_banos"));
        } catch (NumberFormatException e) {
        }
        try {
            bannosYAseos += Integer.valueOf(inmuebleInfo.getFieldValue("numero_aseos"));
        } catch (NumberFormatException e) {
        }

        writer.write("" + bannosYAseos); // 09
        writer.write("|");
        writer.write((!inmuebleInfo.getFieldValue("numero_ascensores").isEmpty() && !inmuebleInfo.getFieldValue("numero_ascensores").equals("0")) ? "t" : "f"); // 10
        writer.write("|");

        if (!inmuebleInfo.getFieldValue("plazas_parking").isEmpty()) {
            try {
                Integer plazasParking = Integer.valueOf(inmuebleInfo.getFieldValue("plazas_parking"));
                writer.write("" + plazasParking);
            } catch (NumberFormatException e) {
            }

        } else if (inmuebleInfo.getFieldValue("con_parking").equals("t")) {
            writer.write("1");
        } // 11
        writer.write("|");

        switch (inmuebleInfo.getFieldValue("estado_conservacion_id")) {
            case "01": // Para reformar
                writer.write("03");
                break;
            case "02": // Buen estado
            case "03": // Obra nueva
                writer.write("04");
                break;
        } // 12

        writer.write("|");
        String tipoSuelo = "10";
        if (inmuebleInfo.getTiposSuelo() != null && !inmuebleInfo.getTiposSuelo().isEmpty()) {
            MyInmuebleTipoSuelo suelo = inmuebleInfo.getTiposSuelo().iterator().next();

            switch (suelo.getTipo_suelo_id()) {
                case "01": // Parquet
                    tipoSuelo = "01";
                    break;
                case "02": // Tarima
                    tipoSuelo = "02";
                    break;
                case "03": // Madera
                    tipoSuelo = "04";
                    break;
                case "04": // Moqueta
                    tipoSuelo = "05";
                    break;
                case "05": // Mármol
                    tipoSuelo = "06";
                    break;
                case "06": // Cerámica
                    tipoSuelo = "07";
                    break;
                case "07": // Gres
                    tipoSuelo = "08";
                    break;
                case "08": // Terrazo
                    tipoSuelo = "09";
                    break;
            }
        }
        writer.write(tipoSuelo); // 13
        writer.write("|");

        String tipoCalefaccion = "03";
        switch (inmuebleInfo.getFieldValue("tipo_calefaccion_id")) {
            case "01":
                tipoCalefaccion = "01";
                break;
            case "02":
                tipoCalefaccion = "02";
                break;
        }
        writer.write(tipoCalefaccion); // 14
        writer.write("|");
        writer.write(""); // 15
        writer.write("|");

        String tipoAgua = "03";
        switch (inmuebleInfo.getFieldValue("tipo_agua_caliente_id")) {
            case "01":
                tipoAgua = "01";
                break;
            case "02":
                tipoAgua = "02";
                break;
        }
        writer.write(tipoAgua); // 16
        writer.write("|");

        writer.write(""); // 17
        writer.write("|");

        switch (inmuebleInfo.getFieldValue("tipo_aa_id")) {
            case "01": // Frío
                writer.write("01");
                break;
            case "02": // Frío / calor
                writer.write("03");
                break;
            default: // No disponible
                writer.write("04");
        } // 18

        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("numero_armarios")); // 19
        writer.write("|");
        if (!inmuebleInfo.getFieldValue("area_terraza").isEmpty() && !inmuebleInfo.getFieldValue("area_terraza").equals("0")) {
            writer.write("02"); // 20
        }
        writer.write("|");
        if (!inmuebleInfo.getFieldValue("area_terraza").isEmpty() && !inmuebleInfo.getFieldValue("area_terraza").equals("0")) {
            writer.write(inmuebleInfo.getFieldValue("area_terraza")); // 21
        }
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("tipo_orientacion_id"));  // 22
        writer.write("|");
        writer.write("");  // 23
        writer.write("|");
        writer.write(""); // 24
        writer.write("|");

        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        int anioConstruccion = anioActual;
        try {
            anioConstruccion = Integer.valueOf(inmuebleInfo.getFieldValue("anio_construccion"));
        } catch (NumberFormatException e) {
        }
        int anios = anioActual - anioConstruccion;
        if (anios < 5) {
            writer.write("01");
        } else if (anios <= 10) {
            writer.write("02");
        } else if (anios <= 20) {
            writer.write("03");
        } else if (anios <= 30) {
            writer.write("04");
        } else {
            writer.write("05");
        }  // 25

        writer.write("|");
        if (inmuebleInfo.getFieldValue("con_tenis").equals("t")
                || inmuebleInfo.getFieldValue("con_squash").equals("t")
                || inmuebleInfo.getFieldValue("con_futbol").equals("t")
                || inmuebleInfo.getFieldValue("con_baloncesto").equals("t")
                || inmuebleInfo.getFieldValue("con_gimnasio").equals("t")
                || inmuebleInfo.getFieldValue("con_padel").equals("t")
                || inmuebleInfo.getFieldValue("con_golf").equals("t")) {
            writer.write("t"); // 26
        }
        writer.write("|");

        switch (inmuebleInfo.getFieldValue("interior_exterior_id")) {
            case "01": // Exterior
            case "03":
                writer.write("t");
                break;
            default:
                writer.write("f");
        } // 27
        writer.write("|");
        if (inmuebleInfo.getFieldValue("tipo_tendedero_id").isEmpty()) {
            writer.write("03");
        } else {
            writer.write(inmuebleInfo.getFieldValue("tipo_tendedero_id"));
        } // 28
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_piscina")); // 29
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_gimnasio")); // 30
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_areas_verdes")); // 31
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_conserje")); // 32
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_trastero")); // 33
        writer.write("|");

        switch (inmuebleInfo.getFieldValue("tipo_equipamiento_id")) {
            case "02":
                writer.write("C"); // 34
                break;
            case "03":
                writer.write("A"); // 34
                break;
        }

        writer.write("|");
        if (inmuebleInfo.getFieldValue("tipo_equipamiento_id").equals("02")) {
            writer.write("t"); // 35
        }
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_puerta_seguridad")); // 36
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_alarma")); // 37
        writer.write("|");

        if (!inmuebleInfo.getFieldValue("gastos_comunidad").isEmpty()) {
            try {
                Integer gastosComunidad = Float.valueOf(inmuebleInfo.getFieldValue("gastos_comunidad")).intValue();
                writer.write("" + gastosComunidad); // 38
            } catch (NumberFormatException e) {
            }
        }
        writer.write("|");

    }

    /**
     *
     * @param inmuebleInfo
     * @throws IOException
     */
    private void bloqueLocales(MyInmuebleInfo inmuebleInfo) throws IOException {
        MyInmuebleDb inmueble = inmuebleInfo.getInmuebleDb();

        writer.write(""); // 01
        writer.write("|");
        writer.write("");  // 02
        writer.write("|");
        writer.write("");  // 03
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("puerta")); // 04
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("area_total_construida"));  // 05
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("area_total_util"));  // 06
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("numero_aseos")); // 07
        writer.write("|");
        writer.write("");  // 08
        writer.write("|");
        writer.write("");  // 09
        writer.write("|");
        switch (inmuebleInfo.getFieldValue("estado_conservacion_id")) {
            case "01": // Para reformar
                writer.write("03");
                break;
            case "02": // Buen estado
            case "03": // Obra nueva
                writer.write("04");
                break;
        } // 10
        writer.write("|");
        writer.write("");  // 11
        writer.write("|");

        String tipoCalefaccion = "03";
        switch (inmuebleInfo.getFieldValue("tipo_calefaccion_id")) {
            case "01":
                tipoCalefaccion = "01";
                break;
            case "02":
                tipoCalefaccion = "02";
                break;
        }
        writer.write(tipoCalefaccion); // 12
        writer.write("|");
        String tipoAgua = "03";
        switch (inmuebleInfo.getFieldValue("tipo_agua_caliente_id")) {
            case "01":
                tipoAgua = "01";
                break;
            case "02":
                tipoAgua = "02";
                break;
        }
        writer.write(tipoAgua); // 13
        writer.write("|");
        writer.write("");  // 14
        writer.write("|");
        writer.write("");  // 15
        writer.write("|");
        writer.write(""); // 16
        writer.write("|");
        writer.write("");  // 17
        writer.write("|");
        writer.write("");  // 18
        writer.write("|");

        String metrosFachada = inmuebleInfo.getFieldValue("m_lineales_fachada");
        if (metrosFachada != null && !metrosFachada.isEmpty()) {
            Integer iMetrosFachada = Integer.parseInt(metrosFachada);
            if (iMetrosFachada >= 1 && iMetrosFachada <= 4) {
                metrosFachada="01";
            } else if (iMetrosFachada >= 5 && iMetrosFachada <= 8) {
                metrosFachada="02";
            } else if (iMetrosFachada >= 9 && iMetrosFachada <= 12) {
                metrosFachada="03";
            } else if (iMetrosFachada >= 13) {
                metrosFachada="04";
            } else if (iMetrosFachada == 0) {
                metrosFachada="05";
            }
        } else {
            metrosFachada = "";
        }

        writer.write(metrosFachada); // 19
        writer.write("|");
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        int anioConstruccion = anioActual;
        try {
            anioConstruccion = Integer.valueOf(inmuebleInfo.getFieldValue("anio_construccion"));
        } catch (NumberFormatException e) {
        }
        int anios = anioActual - anioConstruccion;
        if (anios < 5) {
            writer.write("01");
        } else if (anios <= 10) {
            writer.write("02");
        } else if (anios <= 20) {
            writer.write("03");
        } else if (anios <= 30) {
            writer.write("04");
        } else {
            writer.write("05");
        } // 20
        writer.write("|");
        writer.write("");  // 21
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_salida_humos")); // 22
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_almacen")); // 23
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_cocina_equipada")); // 24
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_puerta_seguridad")); // 25
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_camaras_seguridad"));  // 26
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_alarma"));  // 27
        writer.write("|");
        if (!inmuebleInfo.getFieldValue("gastos_comunidad").isEmpty()) {
            try {
                Integer gastosComunidad = Float.valueOf(inmuebleInfo.getFieldValue("gastos_comunidad")).intValue();
                writer.write("" + gastosComunidad); // 38
            } catch (NumberFormatException e) {
            }
        } // 28
        writer.write("|");
        inmuebleInfo.getFieldValue("ultima_actividad"); // 29
        writer.write("|");
    }

    /**
     *
     * @param inmuebleInfo
     * @throws IOException
     */
    private void bloqueOficinas(MyInmuebleInfo inmuebleInfo) throws IOException {
        writer.write(""); // 01
        writer.write("|");
        writer.write(""); // 02
        writer.write("|");
        writer.write(""); // 03
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("puerta")); // 04
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("area_total_construida"));  // 05
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("area_total_util"));  // 06
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("numero_aseos")); // 07
        writer.write("|");
        writer.write("");  // 08
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("numero_ascensores"));  // 09
        writer.write("|");
        if (!inmuebleInfo.getFieldValue("plazas_parking").isEmpty()) {
            try {
                Integer plazasParking = Integer.valueOf(inmuebleInfo.getFieldValue("plazas_parking"));
                writer.write("" + plazasParking);
            } catch (NumberFormatException e) {
            }

        } else if (inmuebleInfo.getFieldValue("con_parking").equals("t")) {
            writer.write("1");
        } // 10
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("numero_habitaciones")); // 11

        writer.write("|");
        switch (inmuebleInfo.getFieldValue("estado_conservacion_id")) {
            case "01": // Para reformar
                writer.write("03");
                break;
            case "02": // Buen estado
            case "03": // Obra nueva
                writer.write("04");
                break;
        } // 12
        writer.write("|");
        String tipoSuelo = "10";
        if (inmuebleInfo.getTiposSuelo() != null && !inmuebleInfo.getTiposSuelo().isEmpty()) {
            MyInmuebleTipoSuelo suelo = inmuebleInfo.getTiposSuelo().iterator().next();

            switch (suelo.getTipo_suelo_id()) {
                case "01": // Parquet
                    tipoSuelo = "01";
                    break;
                case "02": // Tarima
                    tipoSuelo = "02";
                    break;
                case "03": // Madera
                    tipoSuelo = "04";
                    break;
                case "04": // Moqueta
                    tipoSuelo = "05";
                    break;
                case "05": // Mármol
                    tipoSuelo = "06";
                    break;
                case "06": // Cerámica
                    tipoSuelo = "07";
                    break;
                case "07": // Gres
                    tipoSuelo = "08";
                    break;
                case "08": // Terrazo
                    tipoSuelo = "09";
                    break;
            }
        }
        writer.write(tipoSuelo);  // 13
        writer.write("|");

        String tipoCalefaccion = "03";
        switch (inmuebleInfo.getFieldValue("tipo_calefaccion_id")) {
            case "01":
                tipoCalefaccion = "01";
                break;
            case "02":
                tipoCalefaccion = "02";
                break;
        }
        writer.write(tipoCalefaccion); // 14
        writer.write("|");
        String tipoAgua = "03";
        switch (inmuebleInfo.getFieldValue("tipo_agua_caliente_id")) {
            case "01":
                tipoAgua = "01";
                break;
            case "02":
                tipoAgua = "02";
                break;
        }
        writer.write(tipoAgua); // 15
        writer.write("|");
        switch (inmuebleInfo.getFieldValue("tipo_aa_id")) {
            case "01": // Frío
                writer.write("01");
                break;
            case "02": // Frío / calor
                writer.write("03");
                break;
            default: // No disponible
                writer.write("04");
        } // 16
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValue("tipo_orientacion_id"));  // 17
        writer.write("|");
        writer.write(""); // 18
        writer.write("|");
        writer.write("");  // 19
        writer.write("|");
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        int anioConstruccion = anioActual;
        try {
            anioConstruccion = Integer.valueOf(inmuebleInfo.getFieldValue("anio_construccion"));
        } catch (NumberFormatException e) {
        }
        int anios = anioActual - anioConstruccion;
        if (anios < 5) {
            writer.write("01");
        } else if (anios <= 10) {
            writer.write("02");
        } else if (anios <= 20) {
            writer.write("03");
        } else if (anios <= 30) {
            writer.write("04");
        } else {
            writer.write("05");
        } // 20
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("altura_edificio")); // 21
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_instalaciones_deportivas")); // 22
        writer.write("|");
        writer.write(""); // 23
        writer.write("|");
        switch (inmuebleInfo.getFieldValue("interior_exterior_id")) {
            case "01": // Exterior
            case "03":
                writer.write("t");
                break;
            default:
                writer.write("f");
        } // 24
        writer.write("|");

        writer.write(inmuebleInfo.getFieldValueTF("con_almacen")); // 25
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_zona_carga_descarga")); // 26

        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_puerta_seguridad")); // 27
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_camaras_seguridad"));  // 28
        writer.write("|");
        writer.write(inmuebleInfo.getFieldValueTF("con_alarma"));  // 29
        writer.write("|");
        if (!inmuebleInfo.getFieldValue("gastos_comunidad").isEmpty()) {
            try {
                Integer gastosComunidad = Float.valueOf(inmuebleInfo.getFieldValue("gastos_comunidad")).intValue();
                writer.write("" + gastosComunidad);
            } catch (NumberFormatException e) {
            }
        } // 30
        writer.write("|");
    }

    /**
     * @return @throws AlfaException
     */
    private List<MyInmuebleInfo> getInmuebles(int contador) throws AlfaException {

        contador -= 10; // Reserva 10 para los locales de la oficina 1338

        List<MyInmuebleInfo> result = new ArrayList<>();

        //
        // Viviendas dadas de alta, captadas y en venta
        //
        String sql = "SELECT i.* FROM inmuebles i"
                + " JOIN inmuebles_portal ip ON ip.inmueble_id = i.id AND ip.portal_id='01'"
                + " WHERE i.web IN ('t', 'i') AND i.tipo_inmueble_id IN ('01', '02', '03', '04') AND i.es_venta='t' AND i.pais_id=34"
                + " AND i.es_opcion_compra <> 't' ORDER BY i.numero_agencia, i.codigo"; // + (contador << 1);

        int oficinaAnt = -1, orderBy = 0;

        List<MyInmuebleDbPortal> inmueblesDb = bdUtils.getDataList(sql, MyInmuebleDbPortal.class);
        for (MyInmuebleDbPortal inmuebleDb : inmueblesDb) {

            if (inmuebleDb.getNumero_agencia() != oficinaAnt) {
                oficinaAnt = inmuebleDb.getNumero_agencia();
                orderBy = 0;
            }
            inmuebleDb.setOrderBy(orderBy);

            orderBy++;
        }

        Collections.sort(inmueblesDb);

        for (MyInmuebleDb inmuebleDb : inmueblesDb) {
            result.add(cargarInmuebleInfo(inmuebleDb));

            contador--;
            if (contador == 0) {
                break;
            }
        }

        //
        // Inmuebles en alquiler
        //
        sql = "SELECT i.* FROM inmuebles i"
                + " LEFT JOIN inmuebles_portal_no ip ON ip.inmueble_id = i.id AND ip.portal_id='05'"
                + " WHERE i.web IN ('t', 'i') AND i.tipo_inmueble_id IN ('01', '02', '03', '04') AND ip.portal_id IS NULL"
                + " AND es_alquiler='t' AND es_opcion_compra <> 't' AND i.pais_id=34 ORDER BY i.numero_agencia, i.codigo";

        inmueblesDb = bdUtils.getDataList(sql, MyInmuebleDbPortal.class);
        for (MyInmuebleDbPortal inmuebleDb : inmueblesDb) {
            inmuebleDb.setEs_venta("f");
            inmuebleDb.setPrecio_venta(0);
            result.add(cargarInmuebleInfo(inmuebleDb));
        }

        return result;
    }
}

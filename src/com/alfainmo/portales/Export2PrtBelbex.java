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
import com.alfainmo.util.FmtUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Export2PrtBelbex extends AbstractExport2PrtPago {

    private final MyInmueblesAux tbAux;
    private final MyAgenciaDb agencia;
    private final String pathImg;

    public Export2PrtBelbex(BdUtils bdUtils, String pathDestino, MyAgenciaDb agencia) throws AlfaException {
        super(bdUtils, pathDestino, "temp/alfainmo-belbex-" + agencia.getNumero_agencia());
        this.tbAux = new MyInmueblesAux(bdUtils);
        this.agencia = agencia;
        pathImg = ConfigUtils.getInstance().getString("pathImagenes");
    }

    public AbstractExport2Prt exportar() throws AlfaException {

        crearDocumento("xml");
        try {
            addCabeceraGeneral();

            addCabecera("Agencias");
            generarAgencia(agencia);

            addPie();
            addCabecera("Inmuebles");
            for (MyInmuebleInfo inmueble : getInmuebles()) {
                generarInmueble(inmueble);
            }
            addPie();
            addPieGeneral();
        } finally {
            terminarDocumento();
        }
        return this;
    }

    /**
     * @param agencia
     * @return
     */
    private String getDireccionAgencia(MyAgenciaDb agencia) {
        StringBuilder result = new StringBuilder();

        result.append(agencia.getNombre_calle()).append(" n. ").append(agencia.getNumero_calle())
                .append(" (").append(agencia.getCodigo_postal()).append(" - ")
                .append(agencia.getPoblacion()).append(", ").append(agencia.getProvincia()).append(")");

        return result.toString();
    }

    public String getDocumentoAct() {
        return documentoAct;
    }

    /**
     * @param agencia
     * @throws AlfaException
     */
    protected void generarAgencia(MyAgenciaDb agencia) throws AlfaException {

        try {
            writer.write("\r\n<Agencia>");
            writer.write("<IdInmobiliariaExterna><![CDATA[" + agencia.getId() + "]]></IdInmobiliariaExterna>");
            writer.write("<DireccionAgencia><![CDATA[" + getDireccionAgencia(agencia) + "]]></DireccionAgencia>");
            writer.write("<TelefonoAgencia><![CDATA[" + agencia.getTelefono1_contacto() + "]]></TelefonoAgencia>");
            writer.write("<FaxAgencia><![CDATA[" + ((agencia.getFax() != null) ? agencia.getFax() : "") + "]]></FaxAgencia>");
            writer.write("<EmailAgencia><![CDATA[" + agencia.getEmail_contacto() + "]]></EmailAgencia>");
            writer.write("<WebAgencia><![CDATA[" + agencia.getWeb() + "]]></WebAgencia>");
            writer.write("\r\n</Agencia>");
        } catch (IOException e) {
            throw new AlfaException(e);
        }

    }

    /**
     * @param inmueble
     * @throws AlfaException
     */
    protected void generarInmueble(MyInmuebleInfo inmueble) throws AlfaException {

        MyInmuebleDb inmuebleDb = inmueble.getInmuebleDb();

        int operacion = 0, precio = 0, precioVenta = 0;

        if (inmuebleDb.getEs_alquiler() != null && inmuebleDb.getEs_alquiler().equals("t")) {
            operacion = 3; // Alquiler
            precio = (int) inmuebleDb.getPrecio_alquiler();
        }

        if (inmuebleDb.getEs_venta() != null && inmuebleDb.getEs_venta().equals("t")) {
            operacion = 4; // Venta
            precioVenta = (int) inmuebleDb.getPrecio_venta();
            precio = precioVenta;
        }

        if (operacion == 0) {
            return;
        }

        int opcionCompra = 0;
        if (inmuebleDb.getEs_opcion_compra() != null && inmuebleDb.getEs_opcion_compra().equals("t")) {
            opcionCompra = 1;
        }

        String supConstruida = inmueble.getFieldValue("area_total_construida");
        if (supConstruida.isEmpty()) {
            supConstruida = inmueble.getFieldValue("area_total");
        }

        String referencia = inmuebleDb.getNumero_agencia() + "/" + inmuebleDb.getCodigo();

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

            writer.write("<IdInmobiliariaExterna>alfainmo</IdInmobiliariaExterna>");
            writer.write(MessageFormat.format("<IdPisoExterno>{0}</IdPisoExterno>", referencia));

            writer.write(MessageFormat.format("<TipoInmueble>{0}</TipoInmueble>", convertTipoInmueble(inmueble)));

            writer.write(MessageFormat.format("<TipoOperacion>{0}</TipoOperacion>", "" + operacion));
            writer.write(MessageFormat.format("<PrecioEur>{0}</PrecioEur>", "" + precio));

            writer.write(MessageFormat.format("<OpcionACompra>{0}</OpcionACompra>", "" + opcionCompra));
            if (opcionCompra == 1) {
                writer.write(MessageFormat.format("<PrecioVentaOpcionCompra>{0}</PrecioVentaOpcionCompra>", "" + precioVenta));
            }

            writer.write(MessageFormat.format("<NombrePoblacion>{0}</NombrePoblacion>", inmuebleDb.getPoblacion()));
            writer.write(MessageFormat.format("<CodigoPostal>{0}</CodigoPostal>", inmuebleDb.getCodigo_postal()));
            writer.write(MessageFormat.format("<Situacion1>{0}</Situacion1>", inmuebleDb.getZona()));
            writer.write(MessageFormat.format("<SuperficieConstruida>{0}</SuperficieConstruida>", supConstruida));
            writer.write(MessageFormat.format("<SuperficieUtil>{0}</SuperficieUtil>", inmueble.getFieldValue("area_total_util")));
            writer.write(MessageFormat.format("<HabitacionesSimples>{0}</HabitacionesSimples>", inmueble.getFieldValue("numero_habitaciones")));
            writer.write(MessageFormat.format("<BanosCompletos>{0}</BanosCompletos>", inmueble.getFieldValue("numero_banos")));
            writer.write(MessageFormat.format("<BanosAuxiliares>{0}</BanosAuxiliares>", inmueble.getFieldValue("numero_aseos")));

            writer.write(MessageFormat.format("<Descripcion><![CDATA[{0}]]></Descripcion>", inmuebleDb.getDescripcion()));

            writer.write("<Fotos>");
            int nimg = 1;
            for (MyImagenDb imagenDb : inmueble.getImagenes()) {
                String img = pathImg + imagenDb.getPath() + "/g_" + imagenDb.getFichero();

                writer.write(MessageFormat.format("<Foto{0}>{1}</Foto{2}>", nimg, img, nimg));

                nimg++;
            }
            writer.write("</Fotos>");

            writer.write(MessageFormat.format("<EstadoConservacion>{0}</EstadoConservacion>", estadoConservacion));
            writer.write(MessageFormat.format("<NombreCalle>{0}</NombreCalle>", inmuebleDb.getNombre_calle()));
            writer.write(MessageFormat.format("<NumeroCalle>{0}</NumeroCalle>", "" + inmuebleDb.getNumero_calle()));
            writer.write("<MostrarCalle>0</MostrarCalle>");
            writer.write(MessageFormat.format("<AlturaPiso>{0}</AlturaPiso>", inmueble.getFieldValue("piso")));

            writer.write("<Latitud>" + inmuebleDb.getCoord_x() + "</Latitud>");
            writer.write("<Longitud>" + inmuebleDb.getCoord_y() + "</Longitud>");

            String gastosComunidad = inmueble.getFieldValue("gastos_comunidad");
            if (!gastosComunidad.isEmpty()) {
                writer.write("<GastosComunidad_tiene>1</GastosComunidad_tiene>");

                try {
                    Integer iGastos = Integer.parseInt(gastosComunidad);
                    String gastos;
                    if (iGastos <= 20) {
                        gastos = "Entre 10 y 20 €";
                    } else if (iGastos <= 40) {
                        gastos = "Entre 20 y 40 €";
                    } else if (iGastos <= 60) {
                        gastos = "Entre 40 y 60 €";
                    } else if (iGastos <= 80) {
                        gastos = "Entre 60 y 80 €";
                    } else if (iGastos <= 100) {
                        gastos = "Entre 80 y 100 €";
                    } else {
                        gastos = "Más de 100 €";
                    }

                    writer.write(MessageFormat.format("<GastosComunidad_comentario>{0}</GastosComunidad_comentario>", gastos));

                } catch (NumberFormatException e) {
                }

            }

            String anioConstruccion = inmueble.getFieldValue("anio_construccion");
            if (!anioConstruccion.isEmpty()) {

                try {
                    Integer iAnios = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(anioConstruccion);
                    String anios;
                    if (iAnios < 5) {
                        anios = "Menos de 5 años";
                    } else if (iAnios <= 10) {
                        anios = "Entre 5 y 10 años";
                    } else if (iAnios <= 20) {
                        anios = "Entre 10 y 20 años";
                    } else if (iAnios <= 30) {
                        anios = "Entre 20 y 30 años";
                    } else if (iAnios <= 50) {
                        anios = "Entre 30 y 50 años";
                    } else {
                        anios = "Más de 50 años";
                    }
                    writer.write("<AnoConstruccion_tiene>1</AnoConstruccion_tiene>");
                    writer.write(MessageFormat.format("<AnoConstruccion_comentario>{0}</AnoConstruccion_comentario>", anios));

                } catch (NumberFormatException e) {
                }

            }

            if (inmueble.getFieldValue("con_trastero").equals("t")) {
                writer.write("<Trastero_tiene>1</Trastero_tiene>");
            }

            if (!inmueble.getFieldValue("plazas_parking").isEmpty() && !inmueble.getFieldValue("plazas_parking").equals("0")) {
                writer.write("<Garaje_tiene>1</Garaje_tiene>");
            }

            if (!inmueble.getFieldValue("numero_ascensores").isEmpty() && !inmueble.getFieldValue("numero_ascensores").equals("0")) {
                writer.write("<Ascensor_tiene>1</Ascensor_tiene>");
                writer.write(MessageFormat.format("<Ascensor_comentario>{0}</Ascensor_comentario>", inmueble.getFieldValue("numero_ascensores")));
            }

            if (!inmueble.getFieldValue("area_terraza").isEmpty() && !inmueble.getFieldValue("area_terraza").equals("0")) {
                writer.write("<Terraza_tiene>1</Terraza_tiene>");
                writer.write(MessageFormat.format("<Terraza_comentario>{0}</Terraza_comentario>", inmueble.getFieldValue("area_terraza")));
            }

            if (inmueble.getFieldValue("con_piscina").equals("t")) {
                writer.write("<Piscina_tiene>1</Piscina_tiene>");
            }

            if (inmueble.getFieldValue("tipo_equipamiento_id").equals("03")) {
                writer.write("<Amueblado_tiene>1</Amueblado_tiene>");
            }

            String numeroArmarios = inmueble.getFieldValue("numero_armarios");
            if (!numeroArmarios.isEmpty() && !numeroArmarios.equals("0")) {
                writer.write("<ArmariosEmpotrados_tiene>1</ArmariosEmpotrados_tiene>");

                if (!numeroArmarios.equals("1") && !numeroArmarios.equals("2")) {
                    numeroArmarios = "Más de 2";
                }
                writer.write(MessageFormat.format("<ArmariosEmpotrados_comentario>{0}</ArmariosEmpotrados_comentario>", numeroArmarios));
            }

            String tipoAA = inmueble.getFieldValue("tipo_aa_id");
            if (!tipoAA.isEmpty()) {
                writer.write("<AireAcondicionado_tiene>1</AireAcondicionado_tiene>");
                if (tipoAA.equals("02")) {
                    tipoAA = "Frío y calor";
                } else {
                    tipoAA = "Frío";
                }
                writer.write(MessageFormat.format("<AireAcondicionado_comentario>{0}</AireAcondicionado_comentario>", tipoAA));
            }

            if (inmueble.getFieldValue("con_puerta_seguridad").equals("t")) {
                writer.write("<PuertaBlindada_tiene>1</PuertaBlindada_tiene>");
            }

            if (inmueble.getFieldValue("con_videovigilancia").equals("t")
                    || inmueble.getFieldValue("con_vigilancia_24h").equals("t")
                    || inmueble.getFieldValue("con_camaras_seguridad").equals("t")) {

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
                if (sb.length() > 2) {
                    sb.delete(0, 1);
                }

                writer.write(MessageFormat.format("<SistemaSeguridad_comentario>{0}</SistemaSeguridad_comentario>", sb.toString()));
            }

            switch (inmueble.getFieldValue("interior_exterior_id")) {
                case "01":
                    writer.write("<Exterior_tiene>1</Exterior_tiene>");
                    break;
                case "02":
                    writer.write("<Interior_tiene>1</Interior_tiene>");
                    break;
                case "03":
                    writer.write("<Exterior_tiene>1</Exterior_tiene>");
                    writer.write("<Interior_tiene>1</Interior_tiene>");
                    break;
            }

            if (!inmueble.getFieldValue("tipo_orientacion_id").isEmpty()) {
                writer.write("<Orientacion_tiene>1</Orientacion_tiene>");
                String orient = tbAux.getTiposOrientacionMap().get(inmueble.getFieldValue("tipo_orientacion_id"));
                if (orient != null && orient.length() > 2) {
                    orient = orient.substring(0, 1).toUpperCase() + orient.substring(1);
                    writer.write(MessageFormat.format("<Orientacion_comentario>{0}</Orientacion_comentario>", orient));
                }
            }

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

                writer.write(MessageFormat.format("<EnergiaConsumoCategoria>{0}</EnergiaConsumoCategoria>", result));

            } else {

                writer.write("<EnergiaConsumoCategoria>NO INDICADO</EnergiaConsumoCategoria>");
            }

            writer.write("<EnergiaEmisionCategoria>NO INDICADO</EnergiaEmisionCategoria>");

            if (inmuebleDb.getVideo() != null && !inmuebleDb.getVideo().isEmpty()) {

                String videos[] = inmuebleDb.getVideo().split("\\n");

                int i = 1;
                for (String video : videos) {
                    if (video.toLowerCase().startsWith("http") && !video.toLowerCase().contains(",pvi")) {
                        writer.write("<VideosExternos><Video" + i + "><![CDATA[" + video + "]]></Video" + i + "></VideosExternos>");
                        i++;
                    }
                }
            }

            writer.write("\n</Inmueble>");

        } catch (IOException ex) {
            throw new AlfaException(ex);
        }

    }

    protected void addCabeceraGeneral() throws AlfaException {
        try {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
            writer.write("<Publicacion>\r\n");
        } catch (IOException ex) {
            throw new AlfaException(ex);
        }
    }

    protected void addPieGeneral() throws AlfaException {
        try {
            writer.write("</Publicacion>\r\n");
        } catch (IOException e) {
            throw new AlfaException(e);
        }
    }

    protected void addCabecera(String tabla) throws AlfaException {
        try {
            writer.write("<Table Name=\"" + tabla + "\">\r\n");
        } catch (IOException ex) {
            throw new AlfaException(ex);
        }
    }

    protected void addPie() throws AlfaException {
        try {
            writer.write("</Table>\r\n");
        } catch (IOException e) {
            throw new AlfaException(e);
        }
    }

    private String convertTipoInmueble(MyInmuebleInfo inmuebleInfo) {
        String result = "";

        switch (inmuebleInfo.getInmuebleDb().getTipo_inmueble_id()) {

            case "03": // Local
                result = "Local comercial";
                break;
            case "04": // Oficina
                result = "Oficina";
                break;

            case "06": // Terreno
                result = "Terreno";
                break;
            case "07": // Nave
                result = "Nave industrial";
                break;
        }

        return result;
    }

    /**
     * @return @throws AlfaException
     */
    private List<MyInmuebleInfo> getInmuebles() throws AlfaException {

        List<MyInmuebleInfo> result = new ArrayList<>();

        // Carga los inmuebles que están dados de alta tan sólo, y que están captados
        String sql = "SELECT i.* FROM inmuebles i"
                + " JOIN inmuebles_portal ip ON ip.inmueble_id = i.id AND ip.portal_id='11'"
                + " WHERE i.pais_id = 34 AND i.web IN('t', 'i') AND i.tipo_inmueble_id IN ('03','04','06','07')"
                + " AND numero_agencia=" + agencia.getNumero_agencia()
                + " ORDER BY i.codigo";

        for (MyInmuebleDb inmuebleDb : bdUtils.getDataList(sql, MyInmuebleDbPortal.class)) {
            result.add(cargarInmuebleInfo(inmuebleDb));
        }

        return result;
    }
}

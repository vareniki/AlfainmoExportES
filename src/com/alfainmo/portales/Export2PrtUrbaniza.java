package com.alfainmo.portales;

import java.io.IOException;

import com.alfainmo.extra.AlfaException;
import com.alfainmo.util.BdUtils;

public class Export2PrtUrbaniza extends AbstractExport2Prt {

    private final int oficina;

    /**
     * @param bdUtils
     * @param pathDestino
     * @param oficina
     */
    public Export2PrtUrbaniza(BdUtils bdUtils, String pathDestino, int oficina) {
        super(bdUtils, pathDestino, "URB_" + oficina);
        this.oficina = oficina;
    }

    /**
     * @return @throws AlfaException
     */
    public AbstractExport2Prt exportar() throws AlfaException {

        crearDocumento(true);
        try {
            addCabecera();
            addPie();

        } catch (IOException e) {
            throw new AlfaException(e);
        } finally {
            terminarDocumento();
        }
        return this;
    }

    public String getArchivoZip() {
        return archivoZip;
    }

    public String getDocumentoAct() {
        return documentoAct;
    }

    private void addCabecera() throws IOException {
        writer.write("<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>\r\n");
        writer.write("<inmuebles>\r\n");
    }

    private void addPie() throws IOException {
        writer.write("</inmuebles>\r\n");
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    private void addInmueble() throws IOException {

    /*
    writer.write("<inmueble>\r\n");

    writer.write(MessageFormat.format("<referencia_unica>{0}</referencia_unica>", FmtUtils.notNull(inmueble.getReferencia())));
    writer.write(MessageFormat.format("<referencia_anunciante>{0}</referencia_anunciante>", FmtUtils.notNull(inmueble.getReferencia())));
    writer.write(MessageFormat.format("<pais><![CDATA[{0}]]></pais>", FmtUtils.notNull(inmueble.getPais())));
    writer.write(MessageFormat.format("<provincia><![CDATA[{0}]]></provincia>", FmtUtils.notNull(inmueble.getProvincia())));
    writer.write(MessageFormat.format("<poblacion><![CDATA[{0}]]></poblacion>", FmtUtils.notNull(inmueble.getPoblacion())));
    writer.write(MessageFormat.format("<zona><![CDATA[{0}]]></zona>", FmtUtils.notNull(inmueble.getZona())));
    writer.write("<direccion_visible_01>0</direccion_visible_01>");
    writer.write(MessageFormat.format("<direccion_tipo_via><![CDATA[{0}]]></direccion_tipo_via>", FmtUtils.notNull(inmueble.getTipo_via())));
    writer.write(MessageFormat.format("<direccion_calle><![CDATA[{0}]]></direccion_calle>", FmtUtils.notNull(inmueble.getNombre_via())));
    writer.write(MessageFormat.format("<direccion_numero>{0}</direccion_numero>", inmueble.getNumero_via()));
    writer.write(MessageFormat.format("<direccion_piso>{0}</direccion_piso>", inmueble.getNivel()));
    writer.write(MessageFormat.format("<direccion_letra>{0}</direccion_letra>", FmtUtils.notNull(inmueble.getPuerta())));
    writer.write(MessageFormat.format("<direccion_escalera>{0}</direccion_escalera>", FmtUtils.notNull(inmueble.getEscalera())));
    writer.write(MessageFormat.format("<cp>{0}</cp>", FmtUtils.notNull(inmueble.getCp())));
    writer.write(MessageFormat.format("<tipo><![CDATA[{0}]]></tipo>", FmtUtils.notNull(inmueble.getSubtipo())));

    SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");

    if (inmueble.getCaptacionfh() != null) {
      writer.write(MessageFormat.format("<fecha_captacion>{0}</fecha_captacion>", sd.format(inmueble.getCaptacionfh())));
    }
    writer.write("<fecha_fin_mandato></fecha_fin_mandato>");
    writer.write(MessageFormat.format("<rc_tomo>{0}</rc_tomo>", FmtUtils.notNull(inmueble.getRegistro_tomo())));
    writer.write(MessageFormat.format("<rc_libro>{0}</rc_libro>", FmtUtils.notNull(inmueble.getRegistro_libro())));
    writer.write(MessageFormat.format("<rc_folio>{0}</rc_folio>", FmtUtils.notNull(inmueble.getRegistro_folio())));
    writer.write(MessageFormat.format("<rc_finca>{0}</rc_finca>", FmtUtils.notNull(inmueble.getRegistro_finca())));
    writer.write(MessageFormat.format("<rc_registro>{0}</rc_registro>", FmtUtils.notNull(inmueble.getRegistro_de())));
    writer.write(MessageFormat.format("<referencia_catastral>{0}</referencia_catastral>", FmtUtils.notNull(inmueble.getRef_catastral())));

    writer.write("<gestiones>");
    if (inmueble.getP_venta_act() > 0) {
      writer.write("<gestion>");
      writer.write("<tipo><![CDATA[Compra]]></tipo>");
      writer.write(MessageFormat.format("<precio>{0}</precio>", inmueble.getP_venta_act()));
      writer.write("</gestion>");
    }

    if (inmueble.getP_alq_act() > 0) {
      writer.write("<gestion>");
      writer.write("<tipo><![CDATA[Alquiler]]></tipo>");
      writer.write(MessageFormat.format("<precio>{0}</precio>", inmueble.getP_alq_act()));
      writer.write("</gestion>");
    }
    writer.write("</gestiones>");
    writer.write(MessageFormat.format("<n_habitaciones>{0}</n_habitaciones>", inmueble.getN_dormitorios()));
    writer.write(MessageFormat.format("<n_banos>{0}</n_banos>", inmueble.getN_bannos()));
    writer.write(MessageFormat.format("<n_aseos>{0}</n_aseos>", inmueble.getN_aseos()));
    writer.write(MessageFormat.format("<m2_utiles>{0}</m2_utiles>", (inmueble.getM_util() > 0) ? inmueble.getM_util() : inmueble.getM_const()));
    writer.write(MessageFormat.format("<m2_construidos>{0}</m2_construidos>", inmueble.getM_const()));
    writer.write(MessageFormat.format("<nuevo_01>{0}</nuevo_01>", inmueble.getNuevo()));
    writer.write(MessageFormat.format("<garaje_012>{0}</garaje_012>", (inmueble.getParking_plazas() > 0) ? "1" : "0"));
    writer.write(MessageFormat.format("<calefaccion_01>{0}</calefaccion_01>", inmueble.getCalefaccion()));
    writer.write(MessageFormat.format("<a_acondicionado_01>{0}</a_acondicionado_01>", inmueble.getAire_acondic()));
    writer.write(MessageFormat.format("<piscina_01>{0}</piscina_01>", inmueble.getPiscina()));
    writer.write(MessageFormat.format("<jardin_01>{0}</jardin_01>", inmueble.getJardin_individual()));
    writer.write(MessageFormat.format("<trastero_01>{0}</trastero_01>", (inmueble.getM_trastero() > 0) ? "1" : "0"));
    writer.write(MessageFormat.format("<ascensor_01>{0}</ascensor_01>", inmueble.getAscensor()));
    writer.write(MessageFormat.format("<terraza_01>{0}</terraza_01>", (inmueble.getM_parcela() > 0) ? "1" : "0"));
    writer.write(MessageFormat.format("<descripcion><![CDATA[{0}]]></descripcion>", FmtUtils.htmlToPlainText(inmueble.getObservaciones())));

    writer.write("<fotos>");

    if (inmueble.getGuids_fotos() != null) {
      for (String fotos : inmueble.getGuids_fotos().split(",")) {
        String[] tamanos = fotos.split("_");
        if (tamanos.length < 2) {
          continue;
        }
        writer.write("<foto>");
        writer.write(MessageFormat.format("<url_foto>http://imagenes.alfainmo.com/imagen.htm?guid={0}.jpg</url_foto>", tamanos[1]));
        writer.write("<descripcion_foto></descripcion_foto>");
        writer.write("</foto>");
      }
    }

    if (inmueble.getGuids_planos() != null) {
      for (String planos : inmueble.getGuids_planos().split(",")) {
        String[] tamanos = planos.split("_");
        if (tamanos.length < 2) {
          continue;
        }
        writer.write("<foto>");
        writer.write("<url_foto>" + "http://imagenes.alfainmo.com/imagen.htm?guid=" + tamanos[0] + ".jpg" + "</url_foto>");
        writer.write("<descripcion_foto>Plano</descripcion_foto>");
        writer.write("</foto>");
      }
    }

    writer.write("</fotos>");

    writer.write(MessageFormat.format("<url_externa><![CDATA[http://www.alfainmo.com/ficha.do?referencia={0}/{1}]]></url_externa>", inmueble.getOficina(), inmueble.getCodigo()));
    writer.write(MessageFormat.format("<anyo_construccion>{0}</anyo_construccion>", Calendar.getInstance().get(Calendar.YEAR) - inmueble.getAnnos()));
    writer.write(MessageFormat.format("<n_planta>{0}</n_planta>", inmueble.getNivel()));
    writer.write(MessageFormat.format("<altura_edificio>{0}</altura_edificio>", inmueble.getN_plantas_edificio()));
    writer.write(MessageFormat.format("<terrazas_sup>{0}</terrazas_sup>", inmueble.getM_parcela()));
    writer.write(MessageFormat.format("<gastos_comunidad>{0}</gastos_comunidad>", inmueble.getP_comunidad()));

    writer.write(MessageFormat.format("<n_armarios_empotrados>{0}</n_armarios_empotrados>", inmueble.getN_armarios_emp()));
    writer.write(MessageFormat.format("<cocina_desc><![CDATA[{0}]]></cocina_desc>", inmueble.getTipo_cocina()));

    writer.write("<comercializador>");
    writer.write(MessageFormat.format("<email_comercializador>{0}</email_comercializador>", inmueble.getOficina_email()));
    writer.write(MessageFormat.format("<nombre_comercializador>{0}</nombre_comercializador>", inmueble.getOficina_nombre()));

    String tfno = inmueble.getOficina_telefonos();
    if (tfno != null && tfno.length() > 9) {
      tfno = inmueble.getOficina_telefonos().replaceAll(" |\\.|-", "").substring(0, 9);
    }

    writer.write(MessageFormat.format("<telefono_comercializador>{0}</telefono_comercializador>", tfno));
    writer.write("</comercializador>");

    writer.write("\r\n");

    writer.write("</inmueble>\r\n");
    */
    }

}

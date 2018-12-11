package com.alfainmo.portales;

import com.alfainmo.beans.MyAgenciaDb;
import com.alfainmo.beans.MyImagenDb;
import com.alfainmo.beans.MyInmuebleDb;
import com.alfainmo.beans.MyInmuebleDbPortal;
import com.alfainmo.beans.MyInmuebleInfo;
import com.alfainmo.extra.AlfaException;
import com.alfainmo.extra.MyInmueblesAux;
import com.alfainmo.util.BdUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Export2PrtAgencias extends AbstractExport2PrtPago {

    public Export2PrtAgencias(BdUtils bdUtils, String pathDestino) throws AlfaException {
        super(bdUtils, pathDestino, "temp/alfainmo-agencias");
    }

    public AbstractExport2Prt exportar() throws AlfaException {

        crearDocumento(true);
        try {
            addCabeceraGeneral();

            addCabecera("Agencias");
            for (MyAgenciaDb agencia: getAgencias()) {
                generarAgencia(agencia);
            }
            addPie("Agencias");
        } finally {
            terminarDocumento();
        }
        return this;
    }

    private String getDireccionAgencia(MyAgenciaDb agencia) {
        StringBuilder result = new StringBuilder();

        result.append(agencia.getNombre_calle()).append(" n. ").append(agencia.getNumero_calle())
                .append(" (").append(agencia.getCodigo_postal()).append(" - ")
                .append(agencia.getPoblacion()).append(", ").append(agencia.getProvincia()).append(")");

        return result.toString();
    }

    /**
     *
     * @param agencia
     * @throws AlfaException
     */
    protected void generarAgencia(MyAgenciaDb agencia) throws AlfaException {

        try {
            writer.write("\r\n<Agencia>");
            writer.write("<oficina>" + agencia.getNumero_agencia() + "</oficina>");
            writer.write("<nombre>" + agencia.getNombre_agencia() + "</nombre>");
            writer.write("<direccion><![CDATA[" + getDireccionAgencia(agencia) + "]]></direccion>");

            writer.write("<cp>" + agencia.getCodigo_postal() + "</cp>");
            writer.write("<coord_x>" + agencia.getCoord_x() + "</coord_x>");
            writer.write("<coord_y>" + agencia.getCoord_y() + "</coord_y>");
            writer.write("<poblacion>" + agencia.getPoblacion() + "</poblacion>");
            writer.write("<provincia>" + agencia.getProvincia() + "</provincia>");
            writer.write("<telefono>" + agencia.getTelefono1_contacto() + "</telefono>");
            writer.write("<fax>" + ((agencia.getFax() != null) ? agencia.getFax() : "") + "</fax>");
            writer.write("<email>" + agencia.getEmail_contacto() + "</email>");
            writer.write("<web>" + agencia.getWeb() + "</web>");
            writer.write("\r\n</Agencia>");
        } catch (IOException e) {
            throw new AlfaException(e);
        }

    }

    public String getDocumentoAct() {
        return documentoAct;
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

    /**
     * @return @throws AlfaException
     */
    private List<MyAgenciaDb> getAgencias() throws AlfaException {
        return bdUtils.getDataList("SELECT * FROM agencias WHERE pais_id='34' ORDER BY numero_agencia", MyAgenciaDb.class);
    }

}

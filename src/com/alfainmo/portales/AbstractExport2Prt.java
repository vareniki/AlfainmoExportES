package com.alfainmo.portales;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.*;

import com.alfainmo.extra.AlfaException;
import com.alfainmo.util.BdUtils;
import com.alfainmo.util.CSVPrinter;
import com.alfainmo.util.ConvertUtils;
import com.alfainmo.util.ICSVPrint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class AbstractExport2Prt {

    protected final DateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
    protected final List<String> contenidoZip = new ArrayList<>();
    protected final String pathDestino, prefijo;
    protected final BdUtils bdUtils;

    protected ICSVPrint csvPrint = null;
    protected OutputStreamWriter writer = null;

    protected String archivoZip = null;
    protected String documentoAct = null;

    /**
     * @return @throws AlfaException
     */
    public String generarZip() throws AlfaException {
        contenidoZip.add(documentoAct);
        return ConvertUtils.generarZip(Collections.unmodifiableList(contenidoZip), archivoZip);
    }

    /**
     * @param bdUtils
     * @param pathDestino
     * @param prefijo
     */
    protected AbstractExport2Prt(BdUtils bdUtils, String pathDestino, String prefijo) {
        this.bdUtils = bdUtils;
        this.pathDestino = pathDestino;
        this.prefijo = prefijo;
    }

    /**
     * @param xml
     * @param charset
     * @throws AlfaException
     */
    protected void crearDocumento(boolean xml, String charset) throws AlfaException {

        contenidoZip.clear();

        String extension = (xml) ? ".xml" : ".txt";
        if (prefijo.length() > 8) {
            archivoZip = MessageFormat.format("{0}{1}.zip", pathDestino + "/" + prefijo, extension);
            documentoAct = pathDestino + "/" + prefijo;
        } else {
            archivoZip = MessageFormat.format("{0}{1}.zip", pathDestino + "/" + prefijo + "-" + dateFmt.format(Calendar.getInstance().getTime()), extension);
            documentoAct = pathDestino + "/temp/" + prefijo + "-" + dateFmt.format(Calendar.getInstance().getTime());
        }

        documentoAct += extension;

        try {
            writer = new OutputStreamWriter(new FileOutputStream(documentoAct), charset);
        } catch (IOException e) {
            throw new AlfaException(e);
        }

        if (!xml) {
            csvPrint = new CSVPrinter(writer, true, true);
            csvPrint.changeDelimiter(';');
        }

    }

    /**
     * @param xml
     * @throws AlfaException
     */
    protected void crearDocumento(boolean xml) throws AlfaException {
        crearDocumento(xml, "UTF-8");
    }

    protected void terminarDocumento() throws AlfaException {
        if (csvPrint != null) {
            try {
                csvPrint.close();
                csvPrint = null;
            } catch (IOException ex) {
                throw new AlfaException(ex);
            }
        }

        if (writer != null) {
            try {
                writer.close();
                writer = null;
            } catch (IOException ex) {
                throw new AlfaException(ex);
            }
        }
    }

}
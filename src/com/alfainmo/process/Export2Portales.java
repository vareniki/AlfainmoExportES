package com.alfainmo.process;

import com.alfainmo.beans.MyAgenciaDb;
import com.alfainmo.extra.AlfaException;
import com.alfainmo.portales.*;
import com.alfainmo.util.*;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

public class Export2Portales {

    private final BdUtils bdUtils;
    private final String portalesPath;

    public Export2Portales(BdUtils bdUtils) {
        this.bdUtils = bdUtils;
        this.portalesPath = ConfigUtils.getInstance().getString("portales_path");

        (new File(portalesPath + "/temp")).mkdirs();
    }

    /**
     * @throws AlfaException
     */
    public void exportarInmuebles() throws AlfaException {

        // Exporta inmuebles a Pisos.com
        //System.out.println("Exportando inmuebles a Pisos.com...");

        //Export2PrtPisosCom exportarPisos = new Export2PrtPisosCom(bdUtils, portalesPath);
        //exportarPisos.exportar();

        // Exporta inmuebles a Pisos.com multiagencia
        System.out.println("Exportando inmuebles a Pisos.com multiagencia...");

        Export2PrtPisosComMA exportarPisosMA = new Export2PrtPisosComMA(bdUtils, portalesPath);
        exportarPisosMA.exportar();

        //
        // Exporta a IDEALISTA
        //
        System.out.println("Exportando inmuebles a Idealista...");
        Export2PrtIdealistaV6 exportarIdealista = new Export2PrtIdealistaV6(bdUtils, portalesPath);
        exportarIdealista.exportar();

        //
        // Exporta las AGENCIAS
        //
        System.out.println("Exportando agencias...");

        List<String> genericoZip = new ArrayList<>();
        Export2PrtAgencias exportAgencias = new Export2PrtAgencias(bdUtils, portalesPath);
        exportAgencias.exportar();
        genericoZip.add(exportAgencias.getDocumentoAct());

        ConvertUtils.generarZip(genericoZip, portalesPath + "/alfainmo-agencias.zip");

        //
        // Exporta INMUEBLES - antiguo formato genérico (legacy)
        //
        System.out.println("Exportando inmuebles a los portales gratuitos (legacy)...");

        genericoZip = new ArrayList<>();
        Export2PrtGratuitosLegacy exportGratuitosLegacy = new Export2PrtGratuitosLegacy(bdUtils, portalesPath);
        exportGratuitosLegacy.exportar();
        genericoZip.add(exportGratuitosLegacy.getDocumentoAct());

        ConvertUtils.generarZip(genericoZip, portalesPath + "/alfainmo-inmuebles-legacy.zip");

        //
        // Exporta INMUEBLES - al antiguo formato genérico (legacy)
        //
        System.out.println("Exportando inmuebles (temporal pisos.com)...");

        genericoZip = new ArrayList<>();
        Export2PrtGratuitosOld exportGratuitosLegacyOld = new Export2PrtGratuitosOld(bdUtils, portalesPath);
        exportGratuitosLegacyOld.exportar();
        genericoZip.add(exportGratuitosLegacyOld.getDocumentoAct());

        ConvertUtils.generarZip(genericoZip, portalesPath + "/alfainmo-inmuebles-old.zip");

        //
        // Exporta INMUEBLES - nuevo formato¡
        //
        System.out.println("Exportando inmuebles a los portales gratuitos...");

        genericoZip = new ArrayList<>();
        for (MyAgenciaDb myAgenciaDb : getAgencias()) {
            Export2PrtGratuitos exportGratuitos = new Export2PrtGratuitos(bdUtils, portalesPath, myAgenciaDb);
            exportGratuitos.exportar();
            genericoZip.add(exportGratuitos.getDocumentoAct());
        }
        ConvertUtils.generarZip(genericoZip, portalesPath + "/alfainmo-inmuebles.zip");

        //
        // Exporta INMUEBLES - Inmotools
        //
        System.out.println("Exportando inmuebles a Inmotools...");

        genericoZip = new ArrayList<>();
        for (MyAgenciaDb myAgenciaDb : getAgenciasInmotools()) {
            Export2PrtGratuitos exportInmotools = new Export2PrtGratuitos(bdUtils, portalesPath, myAgenciaDb);
            exportInmotools.exportar();
            genericoZip.add(exportInmotools.getDocumentoAct());
        }
        ConvertUtils.generarZip(genericoZip, portalesPath + "/alfainmo-inmotools.zip");

        //
        // Exporta INMUEBLES - Belbex
        //
        System.out.println("Exportando inmuebles a Belbex...");

        genericoZip = new ArrayList<>();
        for (MyAgenciaDb myAgenciaDb : getAgencias()) {
            Export2PrtBelbex exportBelbex = new Export2PrtBelbex(bdUtils, portalesPath, myAgenciaDb);
            exportBelbex.exportar();
            genericoZip.add(exportBelbex.getDocumentoAct());
        }
        ConvertUtils.generarZip(genericoZip, portalesPath + "/alfainmo-belbex.zip");

        //
        // Exporta INMUEBLES - EnAlquiler / VentaDePisos / MasProfesional
        //
        System.out.println("Exportando inmuebles a EnAlquiler...");

        genericoZip = new ArrayList<>();
        Export2PrtEnAlquiler exportEnAlquiler = new Export2PrtEnAlquiler(bdUtils, portalesPath);
        exportEnAlquiler.exportar();
        genericoZip.add(exportEnAlquiler.getDocumentoAct());

        ConvertUtils.generarZip(genericoZip, portalesPath + "/alfainmo-enalquiler.zip");

        //
        // Eliminar ficheros temporales
        //
        eliminarTemporales();

        //
        // Envía fichero a Idealista
        //
        System.out.println("Enviando fichero a Idealista...");
        exportarIdealista.enviarFtp();
    }

    /**
     *
     */
    private void eliminarTemporales() {

        File dir = (new File(portalesPath + "/temp"));
        for (File file : dir.listFiles()) {
            file.delete();
        }
    }

    /**
     * @return @throws AlfaException
     */
    private List<MyAgenciaDb> getAgencias() throws AlfaException {
        return bdUtils.getDataList("SELECT * FROM agencias WHERE pais_id='34' ORDER BY numero_agencia", MyAgenciaDb.class);
    }

    /**
     *
     * @return
     * @throws AlfaException
     */
    private List<MyAgenciaDb> getAgenciasInmotools() throws AlfaException {
        return bdUtils.getDataList("SELECT * FROM agencias WHERE numero_agencia IN (1544,1901)", MyAgenciaDb.class);
    }

}

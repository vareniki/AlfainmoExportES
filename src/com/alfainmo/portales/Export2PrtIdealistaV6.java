package com.alfainmo.portales;

import com.alfainmo.beans.*;
import com.alfainmo.extra.AlfaException;
import com.alfainmo.util.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Export2PrtIdealistaV6 extends AbstractExport2PrtPago {

    private final static String customerCode = "ilc6d8081617939d52cc5678ee06295e1e2727f43fd";

    private final int maxPisos;
    private final String pathImg;
    protected Map tiposFotos = new HashMap();
    protected Map califEnerg = new HashMap();

    protected void addField(String field, String value) throws IOException {
        addField(field, value, false);
    }

    protected void addField(String field, String value, boolean close) throws IOException {
        writer.write("\"" + field + "\": \"" + value + "\"" + ((close)?"}":","));
    }

    /**
     *
     * @throws AlfaException
     */
    public void enviarFtp() throws AlfaException {

        ConfigUtils cfg = ConfigUtils.getInstance();

        FtpUtils ftpUtils = new FtpUtils(
                cfg.getString("idealista_host"),
                cfg.getString("idealista_login"),
                cfg.getString("idealista_password"));

        ftpUtils.login();
        try {
            ftpUtils.sendFile(documentoAct, customerCode + ".json");
        } finally {
            ftpUtils.logout();
        }

    }

    private void setBooleanFeatures(String[][] clavesValor, MyInmuebleInfo inmuebleInfo, JSONObject features) {
        for (String[] claveValor: clavesValor) {
            switch (inmuebleInfo.getFieldValue(claveValor[0])) {
                case "t":
                    features.put(claveValor[1], true);
                    break;
                case "f":
                    features.put(claveValor[1], false);
                    break;
            }
        }
    }

    private void setFloatFeature(String field, String value, JSONObject features) {
        if (value == null || value.isEmpty()) {
            return;
        }
        try {
            features.put(field, Float.valueOf(value));
        } catch (NumberFormatException e) {

        }
    }

    private void setIntegerFeature(String field, String value, JSONObject features) {
        setIntegerFeature(field, value, features, -1);
    }

    private void setIntegerFeature(String field, String value, JSONObject features, int byDefault) {
        if (value == null || value.isEmpty() && byDefault == -1) {
            return;
        }
        if (value == null || value.isEmpty()) {
            value = "" + byDefault;
        }
        try {
            features.put(field, Integer.valueOf(value));
        } catch (NumberFormatException e) {

        }
    }

    public Export2PrtIdealistaV6(BdUtils bdUtils, String pathDestino) throws AlfaException {
        super(bdUtils, pathDestino, "IDE");
        this. maxPisos = ConfigUtils.getInstance().getInteger("maxInmueblesIdealista", 1000);
        this.pathImg = ConfigUtils.getInstance().getString("pathImagenesIdealista");

        String[][] tipos = {
                { "00", "unknown" },
                { "01", "unknown" },
                { "07", "archive" },
                { "11", "kitchen" },
                { "14", "living" },
                { "15", "bedroom" },
                { "16", "room" },
                { "17", "corridor" },
                { "18", "terrace" },
                { "19", "patio" },
                { "20", "facade" },
                { "21", "garden" },
                { "22", "views" },
                { "24", "details" },
                { "29", "storage" },
                { "30", "room" },
                { "31", "reception" },
                { "32", "waitingroom" },
                { "33", "office" },
                { "35", "garage" },
                { "36", "penthouse" },
                { "37", "pool" },
                { "38", "dining_room" },
                { "41", "communalareas" },
                { "42", "storage_space" },
                { "44", "surroundings" }
        };

        for (String[] tipo: tipos) {
            tiposFotos.put(tipo[0], tipo[1]);
        }

        String[][] califs = {
                {"00", "inProcess"},
                {"02", "A"},
                {"03", "B"},
                {"05", "C"},
                {"06", "D"},
                {"07", "E"},
                {"08", "F"},
                {"09", "G"}
        };

        for (String[] calif: califs) {
            califEnerg.put(calif[0], calif[1]);
        }
    }

    protected void crearDocumento(String charset) throws AlfaException {

        documentoAct = pathDestino + "/" + customerCode + ".json";
        try {
            writer = new OutputStreamWriter(new FileOutputStream(documentoAct), charset);
        } catch (IOException e) {
            throw new AlfaException(e);
        }

    }

    public AbstractExport2Prt exportar() throws AlfaException {

        crearDocumento("UTF8");
        try {

            DateFormat fmtDate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

            writer.write("{");
            addField("customerCountry", "Spain");
            addField("customerCode", customerCode);
            addField("customerReference", "Alfa Inmobiliaria");
            addField("customerSendDate", fmtDate.format(new GregorianCalendar().getTime()));

            writer.write("\"customerContact\": {");

            addField("contactName", "Alfa Inmobiliaria");
            addField("contactEmail", "central@alfainmo.com");
            addField("contactPrimaryPhonePrefix", "34");
            addField("contactPrimaryPhoneNumber", "915191319");
            addField("contactSecondaryPhonePrefix", "34");
            addField("contactSecondaryPhoneNumber", "915191319", true);

            writer.write(", \"customerProperties\": [");

            int i = 0;
            for (MyInmuebleInfo inmueble : getInmuebles(maxPisos)) {

                if (inmueble.getInmuebleDb().getPrecio_venta() + inmueble.getInmuebleDb().getPrecio_alquiler() == 0) {
                    continue;
                }
                Integer areaTotal;
                try {
                    switch (inmueble.getInmuebleDb().getTipo_inmueble_id()) {
                        case "05":
                        case "06":
                            areaTotal = Integer.parseInt(inmueble.getFieldValue("area_total"));
                            break;
                        default:
                            areaTotal = Integer.parseInt(inmueble.getFieldValue("area_total_construida"));
                            break;
                    }
                } catch (NumberFormatException e) {
                    areaTotal = 0;
                }

                if (areaTotal == 0) {
                    continue;
                }

                if (i >= 1) {
                    writer.write(",");
                }

                generarInmueble(inmueble);
                i++;

            }

            writer.write("] }");
        } catch(IOException ex) {
            throw new AlfaException(ex);
        } finally {
            terminarDocumento();
        }
        return this;
    }

    protected void generarInmueble(MyInmuebleInfo inmuebleInfo) throws AlfaException {

        MyInmuebleDb inmueble = inmuebleInfo.getInmuebleDb();
        try {
            // INICIO
            DateFormat fmtDate = new SimpleDateFormat("yyyy/MM/dd h:mm:ss");

            JSONObject property = new JSONObject();
            property.put("propertyCode", String.format("%05d", inmueble.getCodigo()) + String.format("%04d", inmueble.getNumero_agencia()));
            property.put("propertyReference", inmueble.getNumero_agencia() + "/" + inmueble.getCodigo());
            property.put("propertyVisibility", "idealista");
            property.put("propertyUrl", "https://www.alfainmo.com/referencia/" + inmueble.getId() + "/?ref=" + inmueble.getNumero_agencia() + "-" + inmueble.getCodigo());

            property.put("propertyOperation", getPropertyOperation(inmuebleInfo));
            property.put("propertyContact", getPropertyContact(inmuebleInfo));
            property.put("propertyAddress", getPropertyAddress(inmuebleInfo));
            property.put("propertyFeatures", getPropertyFeatures(inmuebleInfo));
            property.put("propertyDescriptions", getPropertyDescriptions(inmuebleInfo));
            property.put("propertyImages", getPropertyImages(inmuebleInfo));

            writer.write(property.toJSONString());

        } catch (IOException ex) {
            throw new AlfaException(ex);
        }
    }

    private JSONArray getPropertyImages(MyInmuebleInfo inmuebleInfo) {
        JSONArray images = new JSONArray();

        if (inmuebleInfo.getImagenes() != null) {
            int orden = 0;
            for (MyImagenDb foto : inmuebleInfo.getImagenes()) {
                orden++;

                JSONObject imagen = new JSONObject();

                String fotoPath = foto.getPath().replaceAll("/", "--") + "--";
                String fotoUrl;
                if (foto.getTipo_imagen_id().equals("07")) {
                    fotoUrl = pathImg + "o/" + fotoPath + foto.getFichero();
                } else {
                    fotoUrl = pathImg + "x/" + fotoPath + foto.getFichero();
                }

                imagen.put("imageOrder", orden);
                if (tiposFotos.containsKey(foto.getTipo_imagen_id())) {
                    imagen.put("imageLabel", tiposFotos.get(foto.getTipo_imagen_id()));
                } else {
                    imagen.put("imageLabel", "unknown");
                }

                imagen.put("imageUrl", fotoUrl);

                images.add(imagen);
            }
        }

        return images;
    }

    private JSONArray getPropertyDescriptions(MyInmuebleInfo inmuebleInfo) {
        MyInmuebleDb inmueble = inmuebleInfo.getInmuebleDb();

        String observaciones;
        if (inmueble.getDescripcion() != null && !inmueble.getDescripcion().trim().isEmpty()) {
            observaciones = inmueble.getDescripcion().trim();
        } else {
            observaciones = inmueble.getDescripcion_abreviada();
        }
        if (observaciones == null) {
            observaciones = "";
        }

        observaciones = "REF. " + inmueble.getNumero_agencia() + "/" + inmueble.getCodigo() + ". "
                        + ((inmuebleInfo.getFieldValue("es_vpo").equals("t")) ? ". El inmueble es VPO." : "")
                        + FmtUtils.htmlToPlainText(observaciones);

        JSONArray descriptions = new JSONArray();
        JSONObject description = new JSONObject();
        description.put("descriptionLanguage", "spanish");
        description.put("descriptionText", observaciones);

        descriptions.add(description);

        return descriptions;
    }

    private JSONObject getPropertyFeatures(MyInmuebleInfo inmuebleInfo) {
        MyInmuebleDb inmueble = inmuebleInfo.getInmuebleDb();
        JSONObject features = new JSONObject();

        switch (inmueble.getTipo_inmueble_id()) {
            case "01": // Es un piso o casa
            case "02":
                setHomeProperties(inmuebleInfo, features);
                break;
            case "03": // Locales y naves
            case "07":
                setPremiseProperties(inmuebleInfo, features);
                break;
            case "04": // Oficinas
                setOfficeProperties(inmuebleInfo, features);
                break;
            case "05": // Parking
                setGarageProperties(inmuebleInfo, features);
                break;
            case "06": // Terreno
                setLandProperties(inmuebleInfo, features);
                break;
        }

        return features;
    }

    private void setLandProperties(MyInmuebleInfo inmuebleInfo, JSONObject features) {

        MyTerrenoDb terrenoDb = inmuebleInfo.getTerrenoDb();

        features.put("featuresType", "land");
        features.put("featuresAreaPlot", terrenoDb.getArea_total());
        features.put("featuresNearestLocationKm", terrenoDb.getKilometro());
    }

    private void setGarageProperties(MyInmuebleInfo inmuebleInfo, JSONObject features) {

        MyGarajeDb garageDb = inmuebleInfo.getGarajeDb();

        features.put("featuresType", "garage");
        features.put("featuresAreaConstructed", garageDb.getArea_total());

        setBooleanFeatures(new String[][]{
                        {"con_ascensor", "featuresLiftAvailable"},
                        {"con_puerta_automatica", "featuresParkingAutomaticDoor"},
                        {"plaza_cubierta", "featuresParkingPlaceCovered"},
                        {"con_trastero", "featuresStorage"},
                        {"con_alarma", "featuresSecurityAlarm"}
                }, inmuebleInfo, features);

        if ((garageDb.getCon_vigilancia_24h() != null && garageDb.getCon_vigilancia_24h().equals("t"))
            || (garageDb.getCon_camaras_seguridad() != null && garageDb.getCon_camaras_seguridad().equals("t"))) {
            features.put("featuresSecuritySystem", true);
        }
    }

    private void setOfficeProperties(MyInmuebleInfo inmuebleInfo, JSONObject features) {

        MyOficinaDb oficinaDb = inmuebleInfo.getOficinaDb();

        features.put("featuresType", "office");
        features.put("featuresAreaConstructed", oficinaDb.getArea_total_construida());

        setIntegerFeature("featuresAreaUsable", inmuebleInfo.getFieldValue("area_total_util"), features);

        // Baños y aseos
        Integer bannosYAseos = 0;
        if (!inmuebleInfo.getFieldValue("numero_banos").isEmpty()) {
            bannosYAseos = Integer.valueOf(inmuebleInfo.getFieldValue("numero_banos"));
        }
        if (!inmuebleInfo.getFieldValue("numero_aseos").isEmpty()) {
            bannosYAseos += Integer.valueOf(inmuebleInfo.getFieldValue("numero_aseos"));
        }
        if (bannosYAseos > 0) {
            features.put("featuresBathroomNumber", bannosYAseos);
        } else {
            features.put("featuresBathroomNumber", 1);
        }

        try {
            Integer anioConstruccion = Integer.parseInt(inmuebleInfo.getFieldValue("anio_construccion"));
            if (anioConstruccion >= 1700 && anioConstruccion < 2100) {
                features.put("featuresBuiltYear", anioConstruccion);
            }

        } catch (NumberFormatException ex) {
        }

        // Aire acondicionado
        switch (inmuebleInfo.getFieldValue("tipo_aa_id")) {
            case "01": // Frío
                features.put("featuresConditionedAir", true);
                features.put("featuresConditionedAirType", "cold");
                break;
            case "02": // Frío / calor
                features.put("featuresConditionedAir", true);
                features.put("featuresConditionedAirType", "cold/heat");
                break;
            default: // No disponible
                features.put("featuresConditionedAir", false);
        }

        // Estado de conservación
        switch (inmuebleInfo.getFieldValue("estado_conservacion_id")) {
            case "01": // Para reformar
                features.put("featuresConservation", "toRestore");
                break;
            case "02": // Buen estado
            case "03":
                features.put("featuresConservation", "good");
                break;
            //case "03": // Obra nueva
                //features.put("featuresConservation", "new");
                //break;
        }


        // Certificado energético
        if (!inmuebleInfo.getFieldValue("calificacion_energetica_id").isEmpty()) {
            String calif=inmuebleInfo.getFieldValue("calificacion_energetica_id");
            if (califEnerg.containsKey(calif)) {
                features.put("featuresEnergyCertificateRating", califEnerg.get(calif));
            } else {
                features.put("featuresEnergyCertificateRating", "unknown");
            }
        }

        setFloatFeature("featuresEnergyCertificatePerformance", inmuebleInfo.getFieldValue("indice_energetico"), features);

        setIntegerFeature("featuresFloorsBuilding", inmuebleInfo.getFieldValue("plantas_edificio"), features);

        // Calefacción
        if (!inmuebleInfo.getFieldValue("tipo_calefaccion_id").isEmpty()) {
            features.put("featuresHeating", true);
        }

        // Agua caliente
        if (!inmuebleInfo.getFieldValue("tipo_agua_caliente_id").isEmpty()) {
            features.put("featuresHotWater", true);
        }

        setIntegerFeature("featuresLiftNumber", inmuebleInfo.getFieldValue("numero_ascensores"), features);

        // Tipo de orientación
        switch (inmuebleInfo.getFieldValue("tipo_orientacion_id")) {
            case "01": // Norte

                break;
            case "02": // Nordeste
                features.put("featuresOrientationNorth", true);
                features.put("featuresOrientationEast", true);
                break;
            case "03": // Este
                features.put("featuresOrientationEast", true);
                break;
            case "04": // Sudeste
                features.put("featuresOrientationSouth", true);
                features.put("featuresOrientationEast", true);
                break;
            case "05": // Sur
                features.put("featuresOrientationSouth", true);
                break;
            case "06": // Suroeste
                features.put("featuresOrientationSouth", true);
                features.put("featuresOrientationWest", true);
                break;
            case "07": // Oeste
                features.put("featuresOrientationWest", true);
                break;
            case "08": // Noroeste
                features.put("featuresOrientationNorth", true);
                features.put("featuresOrientationWest", true);
                break;
        }

        // Parking
        setIntegerFeature("featuresParkingSpacesNumber", inmuebleInfo.getFieldValue("plazas_parking"), features);


        // Sistema de seguridad
        String seguridad = inmuebleInfo.getFieldValue("con_videovigilancia")
                + inmuebleInfo.getFieldValue("con_vigilancia_24h")
                + inmuebleInfo.getFieldValue("con_camaras_seguridad");

        if (!seguridad.isEmpty()) {
            features.put("featuresSecuritySystem", true);
        }

        setBooleanFeatures(new String[][] {
                { "con_cocina_equipada", "featuresEquippedKitchen" },
                { "con_alarma", "featuresSecurityAlarm"},
                { "con_puerta_seguridad", "featuresSecurityDoor"}

        }, inmuebleInfo, features);

    }

    private void setPremiseProperties(MyInmuebleInfo inmuebleInfo, JSONObject features) {

        switch (inmuebleInfo.getInmuebleDb().getTipo_inmueble_id()) {
            case "03": // Local
                features.put("featuresType", "premises_commercial");
                features.put("featuresAreaConstructed", inmuebleInfo.getLocalDb().getArea_total_construida());
                break;
            case "07": // Nave
                features.put("featuresType", "premises_industrial");
                features.put("featuresAreaConstructed", inmuebleInfo.getNaveDb().getArea_total_construida());
                break;
        }

        setIntegerFeature("featuresAreaUsable", inmuebleInfo.getFieldValue("area_total_util"), features);
        setIntegerFeature("featuresBathroomNumber", inmuebleInfo.getFieldValue("numero_aseos"), features, 1);

        // Aire acondicionado
        switch (inmuebleInfo.getFieldValue("tipo_aa_id")) {
            case "01": // Frío
            case "02": // Frío y calor
                features.put("featuresConditionedAir", true);
                break;
            default: // No disponible
                features.put("featuresConditionedAir", false);
        }

        // Estado de conservación
        switch (inmuebleInfo.getFieldValue("estado_conservacion_id")) {
            case "01": // Para reformar
                features.put("featuresConservation", "toRestore");
                break;
            case "02": // Buen estado
            case "03":
                features.put("featuresConservation", "good");
                break;
            //case "03": // Obra nueva
            //features.put("featuresConservation", "new");
            //break;
        }

        // Certificado energético
        if (!inmuebleInfo.getFieldValue("calificacion_energetica_id").isEmpty()) {
            String calif=inmuebleInfo.getFieldValue("calificacion_energetica_id");
            if (califEnerg.containsKey(calif)) {
                features.put("featuresEnergyCertificateRating", califEnerg.get(calif));
            } else {
                features.put("featuresEnergyCertificateRating", "unknown");
            }
        }

        setFloatFeature("featuresEnergyCertificatePerformance", inmuebleInfo.getFieldValue("indice_energetico"), features);

        setBooleanFeatures(new String[][] {
                { "con_cocina_equipada", "featuresEquippedKitchen" },
                { "con_salida_humos", "featuresSmokeExtraction" },
                { "con_alarma", "featuresSecurityAlarm"},
                { "con_puerta_seguridad", "featuresSecurityDoor"}

        }, inmuebleInfo, features);

        setIntegerFeature("featuresFacadeArea", inmuebleInfo.getFieldValue("m_lineales_fachada"), features);
        setIntegerFeature("featuresFloorsBuilding", inmuebleInfo.getFieldValue("plantas_edificio"), features);

        // Calefacción
        if (!inmuebleInfo.getFieldValue("tipo_calefaccion_id").isEmpty()) {
            features.put("featuresHeating", true);
        }

        // Sistema de seguridad
        String seguridad = inmuebleInfo.getFieldValue("con_videovigilancia")
                + inmuebleInfo.getFieldValue("con_vigilancia_24h")
                + inmuebleInfo.getFieldValue("con_camaras_seguridad");

        if (!seguridad.isEmpty()) {
            features.put("featuresSecuritySystem", true);
        }
    }

    private void setHomeProperties(MyInmuebleInfo inmuebleInfo, JSONObject features) {
        MyInmuebleDb inmueble = inmuebleInfo.getInmuebleDb();

        if (inmueble.getTipo_inmueble_id().equals("01")) {

            // Piso
            features.put("featuresType", "flat");
            features.put("featuresAreaConstructed", inmuebleInfo.getPisoDb().getArea_total_construida());

            switch (inmuebleInfo.getPisoDb().getTipo_piso_id()) {
                case "06":
                    features.put("featuresPenthouse", true);
                    break;
                case "05":
                    features.put("featuresDuplex", true);
                    break;
                case "03":
                    features.put("featuresStudio", true);
            }

        } else {

            // Casa o chalet
            features.put("featuresAreaConstructed", inmuebleInfo.getChaletDb().getArea_total_construida());

            switch (inmuebleInfo.getChaletDb().getTipo_chalet_id()) {
                case "02":
                case "04":
                    features.put("featuresType", "house_semidetached");
                    break;
                case "03":
                    features.put("featuresType", "house_independent");
                    break;
                default:
                    features.put("featuresType", "house");
            }
        }

        setIntegerFeature("featuresAreaPlot", inmuebleInfo.getFieldValue("area_parcela"), features);
        setIntegerFeature("featuresAreaUsable", inmuebleInfo.getFieldValue("area_total_util"), features);

        if (!inmuebleInfo.getFieldValue("area_terraza").isEmpty()) {
            features.put("featuresTerrace", true);
        }

        // Baños y aseos
        Integer bannosYAseos = 0;
        if (!inmuebleInfo.getFieldValue("numero_banos").isEmpty()) {
            bannosYAseos = Integer.valueOf(inmuebleInfo.getFieldValue("numero_banos"));
        }
        if (!inmuebleInfo.getFieldValue("numero_aseos").isEmpty()) {
            bannosYAseos += Integer.valueOf(inmuebleInfo.getFieldValue("numero_aseos"));
        }
        if (bannosYAseos > 0) {
            features.put("featuresBathroomNumber", bannosYAseos);
        } else {
            features.put("featuresBathroomNumber", 1);
        }

        try {
            Integer anioConstruccion = Integer.parseInt(inmuebleInfo.getFieldValue("anio_construccion"));
            if (anioConstruccion >= 1700 && anioConstruccion < 2100) {
                features.put("featuresBuiltYear", anioConstruccion);
            }

        } catch (NumberFormatException ex) {
        }

        // Aire acondicionado
        switch (inmuebleInfo.getFieldValue("tipo_aa_id")) {
            case "01": // Frío
                features.put("featuresConditionedAir", true);
                features.put("featuresConditionedAirType", "cold");
                break;
            case "02": // Frío / calor
                features.put("featuresConditionedAir", true);
                features.put("featuresConditionedAirType", "cold/heat");
                break;
            default: // No disponible
                features.put("featuresConditionedAir", false);
        }

        // Estado de conservación
        switch (inmuebleInfo.getFieldValue("estado_conservacion_id")) {
            case "01": // Para reformar
                features.put("featuresConservation", "toRestore");
                break;
            case "02": // Buen estado
            case "03":
                features.put("featuresConservation", "good");
                break;
            //case "03": // Obra nueva
            //features.put("featuresConservation", "new");
            //break;
        }

        // Amueblado
        switch (inmuebleInfo.getFieldValue("tipo_equipamiento_id")) {
            case "01":
                features.put("featuresEquippedKitchen", false);
                features.put("featuresEquippedWithFurniture", false);
                break;
            case "02":
                features.put("featuresEquippedKitchen", true);
                features.put("featuresEquippedWithFurniture", false);
                break;
            case "03":
                features.put("featuresEquippedKitchen", true);
                features.put("featuresEquippedWithFurniture", true);
                break;
        }

        // Certificado energético
        if (!inmuebleInfo.getFieldValue("calificacion_energetica_id").isEmpty()) {
            String calif=inmuebleInfo.getFieldValue("calificacion_energetica_id");
            if (califEnerg.containsKey(calif)) {
                features.put("featuresEnergyCertificateRating", califEnerg.get(calif));
            } else {
                features.put("featuresEnergyCertificateRating", "unknown");
            }
        }

        setFloatFeature("featuresEnergyCertificatePerformance", inmuebleInfo.getFieldValue("indice_energetico"), features);
        setIntegerFeature("featuresFloorsBuilding", inmuebleInfo.getFieldValue("plantas_edificio"), features);

        // Ascensor
        features.put("featuresLiftAvailable", !inmuebleInfo.getFieldValue("numero_ascensores").isEmpty());

        // Tipo de orientación
        switch (inmuebleInfo.getFieldValue("tipo_orientacion_id")) {
            case "01": // Norte
                features.put("featuresOrientationNorth", true);
                break;
            case "02": // Nordeste
                features.put("featuresOrientationNorth", true);
                features.put("featuresOrientationEast", true);
                break;
            case "03": // Este
                features.put("featuresOrientationEast", true);
                break;
            case "04": // Sudeste
                features.put("featuresOrientationSouth", true);
                features.put("featuresOrientationEast", true);
                break;
            case "05": // Sur
                features.put("featuresOrientationSouth", true);
                break;
            case "06": // Suroeste
                features.put("featuresOrientationSouth", true);
                features.put("featuresOrientationWest", true);
                break;
            case "07": // Oeste
                features.put("featuresOrientationWest", true);
                break;
            case "08": // Noroeste
                features.put("featuresOrientationNorth", true);
                features.put("featuresOrientationWest", true);
                break;
        }

        // Parking
        if (!inmuebleInfo.getFieldValue("plazas_parking").isEmpty()
                || inmuebleInfo.getFieldValue("con_parking").equals("t")) {
            features.put("featuresParkingAvailable", true);
        }

        // Tipo de calefacción
        switch (inmuebleInfo.getFieldValue("tipo_calefaccion_id")) {
            case "01":
                features.put("featuresHeatingType", "centralOther");
                break;
            case "02":
                features.put("featuresHeatingType", "individualOther");
                break;
        }

        Integer habitac;
        try {
            habitac = Integer.parseInt(inmuebleInfo.getFieldValue("numero_habitaciones"));
            if (habitac == null || habitac == 0) {

                if (inmueble.getTipo_inmueble_id().equals("01") && inmuebleInfo.getPisoDb().getTipo_piso_id().equals("03")) {
                    habitac = 0;
                } else {
                    habitac = 1;
                }
            }
        } catch (NumberFormatException e) {

            if (inmueble.getTipo_inmueble_id().equals("01") && inmuebleInfo.getPisoDb().getTipo_piso_id().equals("03")) {
                habitac = 0;
            } else {
                habitac = 1;
            }

        }
        features.put("featuresBedroomNumber", habitac);

        // Interior / exterior
        switch (inmuebleInfo.getFieldValue("interior_exterior_id")) {
            case "01":
            case "03":
                features.put("featuresWindowsLocation", "exterior");
                break;
            case "02":
                features.put("featuresWindowsLocation", "interior");
                break;
        }

        setBooleanFeatures(new String[][] {
                { "con_conserje", "featuresDoorman" },
                { "con_areas_verdes", "featuresGarden" },
                { "con_piscina", "featuresPool" },
                { "con_trastero", "featuresStorage" }

        }, inmuebleInfo, features);

    }

    /**
     *
     * @param inmuebleInfo
     * @return
     */
    private JSONObject getPropertyAddress(MyInmuebleInfo inmuebleInfo) {
        MyInmuebleDb inmueble = inmuebleInfo.getInmuebleDb();
        JSONObject address = new JSONObject();
        address.put("addressVisibility", "hidden");
        address.put("addressStreetName", inmueble.getNombre_calle());
        address.put("addressStreetNumber", "" + inmueble.getNumero_calle());
        //address.put("addressBlock", "");
        //address.put("addressFloor", "");
        //address.put("addressStair", "");
        //address.put("addressDoor", inmuebleInfo.getFieldValue("puerta"));
        //address.put("addressUrbanization", "");
        address.put("addressPostalCode", inmueble.getCodigo_postal());
        //address.put("addressNsiCode", "");
        address.put("addressTown", inmueble.getPoblacion());
        address.put("addressCountry", "Spain");
        address.put("addressCoordinatesPrecision", "moved");
        address.put("addressCoordinatesLatitude", inmueble.getCoord_x());
        address.put("addressCoordinatesLongitude", inmueble.getCoord_y());

        return address;
    }

    private JSONObject getPropertyOperation(MyInmuebleInfo inmuebleInfo) {
        MyInmuebleDb inmueble = inmuebleInfo.getInmuebleDb();
        JSONObject operation = new JSONObject();

        Integer precio;
        if (inmueble.getEs_venta().equals("t")) {
            operation.put("operationType", "sale");
            precio = (int) inmueble.getPrecio_venta();
        } else {
            operation.put("operationType", "rent");
            precio = (int) inmueble.getPrecio_alquiler();
        }
        operation.put("operationPrice", precio);

        if (!inmuebleInfo.getFieldValue("gastos_comunidad").isEmpty()) {
            try {
                Integer gastos = Float.valueOf(inmuebleInfo.getFieldValue("gastos_comunidad")).intValue();
                if (gastos >= 1) {
                    operation.put("operationPriceCommunity", gastos);
                }
            } catch (NumberFormatException e) {
            }
        }

        return operation;
    }

    private JSONObject getPropertyContact(MyInmuebleInfo inmuebleInfo) {
        JSONObject contact = new JSONObject();

        contact.put("contactName", inmuebleInfo.getAgencia().getNombre_agencia());
        contact.put("contactEmail", inmuebleInfo.getAgencia().getEmail_contacto());
        contact.put("contactPrimaryPhonePrefix", "34");
        contact.put("contactPrimaryPhoneNumber", inmuebleInfo.getAgencia().getTelefono1_contacto().replaceAll("[^0-9.]", ""));
        if (!inmuebleInfo.getAgencia().getTelefono2_contacto().trim().isEmpty()) {
            contact.put("contactSecondaryPhonePrefix", "34");
            contact.put("contactSecondaryPhoneNumber", inmuebleInfo.getAgencia().getTelefono2_contacto().replaceAll("[^0-9.]", ""));
        }

        return contact;
    }

    private List<MyInmuebleInfo> getInmuebles(int contador) throws AlfaException {

        List<MyInmuebleInfo> result = new ArrayList<>();

        //
        // Inmuebles en alquiler
        //
        String sql = "SELECT i.* FROM inmuebles i"
                + " LEFT JOIN inmuebles_portal_no ip ON ip.inmueble_id = i.id AND ip.portal_id='05'"
                + " WHERE i.web IN ('t', 'i') AND i.tipo_inmueble_id IN ('01', '02', '03', '04', '05', '06', '07') AND ip.portal_id IS NULL"
                + " AND es_alquiler='t' AND es_opcion_compra <> 't' AND i.pais_id=34 ORDER BY i.numero_agencia, i.codigo";

        List<MyInmuebleDbPortal> inmueblesDb = bdUtils.getDataList(sql, MyInmuebleDbPortal.class);
        for (MyInmuebleDbPortal inmuebleDb : inmueblesDb) {
            inmuebleDb.setEs_venta("f");
            inmuebleDb.setPrecio_venta(0);
            result.add(cargarInmuebleInfo(inmuebleDb));

            contador--;
        }

        //
        // Viviendas dadas de alta, captadas y en venta
        //
        sql = "SELECT i.* FROM inmuebles i"
                + " JOIN inmuebles_portal ip ON ip.inmueble_id = i.id AND ip.portal_id='01'"
                + " WHERE i.web IN ('t', 'i') AND i.tipo_inmueble_id IN ('01', '02', '03', '04', '05', '06', '07') AND i.es_venta='t' AND i.pais_id=34"
                + " AND i.es_opcion_compra <> 't' ORDER BY i.numero_agencia, i.codigo";

        int oficinaAnt = -1, orderBy = 0;

        inmueblesDb = bdUtils.getDataList(sql, MyInmuebleDbPortal.class);
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
            if (contador < 0) {
                break;
            }
        }

        return result;
    }
}

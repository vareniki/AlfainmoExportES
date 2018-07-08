package com.alfainmo.extra;

import com.alfainmo.beans.IdDescription;
import com.alfainmo.util.BdUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dmonje
 */
public class MyInmueblesAux {

    private final BdUtils bdUtils;

    private Map<String, String> antiguedadesMap = new HashMap<>();
    private Map<String, String> areasFachadaMap = new HashMap<>();
    private Map<String, String> calificacionesEnergMap = new HashMap<>();
    private Map<String, String> distribucionesMap = new HashMap<>();
    private Map<String, String> estadosConservacionMap = new HashMap<>();
    private Map<String, String> interiorExteriorMap = new HashMap<>();
    private Map<String, String> localizacionLocalMap = new HashMap<>();
    private Map<String, String> plantasPisoMap = new HashMap<>();
    private Map<String, String> puertasPisoMap = new HashMap<>();
    private Map<String, String> subtiposCalefaccionMap = new HashMap<>();
    private Map<String, String> tiposAAMap = new HashMap<>();
    private Map<String, String> tiposAguaMap = new HashMap<>();
    private Map<String, String> tiposCableadoMap = new HashMap<>();
    private Map<String, String> tiposCalefaccionMap = new HashMap<>();
    private Map<String, String> tiposChaletMap = new HashMap<>();
    private Map<String, String> tiposEquipamientoMap = new HashMap<>();
    private Map<String, String> tiposGarajeMap = new HashMap<>();
    private Map<String, String> tiposInmuebleMap = new HashMap<>();
    private Map<String, String> tiposMonedaMap = new HashMap<>();
    private Map<String, String> tiposOficinaMap = new HashMap<>();
    private Map<String, String> tiposOrientacionMap = new HashMap<>();
    private Map<String, String> tiposPisoMap = new HashMap<>();

    private Map<String, String> tiposPuertaMap = new HashMap<>();
    private Map<String, String> tiposSueloMap = new HashMap<>();
    private Map<String, String> tiposTendederoMap = new HashMap<>();
    private Map<String, String> tiposTerrenoMap = new HashMap<>();
    private Map<String, String> tiposVentanaMap = new HashMap<>();

    public Map<String, String> getAntiguedadesMap() {
        return antiguedadesMap;
    }

    public Map<String, String> getAreasFachadaMap() {
        return areasFachadaMap;
    }

    public Map<String, String> getCalificacionesEnergMap() {
        return calificacionesEnergMap;
    }

    public Map<String, String> getDistribucionesMap() {
        return distribucionesMap;
    }

    public Map<String, String> getEstadosConservacionMap() {
        return estadosConservacionMap;
    }

    public Map<String, String> getInteriorExteriorMap() {
        return interiorExteriorMap;
    }

    public Map<String, String> getLocalizacionLocalMap() {
        return localizacionLocalMap;
    }

    public Map<String, String> getPlantasPisoMap() {
        return plantasPisoMap;
    }

    public Map<String, String> getPuertasPisoMap() {
        return puertasPisoMap;
    }

    public Map<String, String> getTiposCalefaccionMap() {
        return tiposCalefaccionMap;
    }

    public Map<String, String> getTiposAAMap() {
        return tiposAAMap;
    }

    public Map<String, String> getTiposAguaMap() {
        return tiposAguaMap;
    }

    public Map<String, String> getTiposCableadoMap() {
        return tiposCableadoMap;
    }

    public Map<String, String> getSubtiposCalefaccionMap() {
        return subtiposCalefaccionMap;
    }

    public Map<String, String> getTiposChaletMap() {
        return tiposChaletMap;
    }

    public Map<String, String> getTiposEquipamiento() {
        return tiposEquipamientoMap;
    }

    public Map<String, String> getTiposGarajeMap() {
        return tiposGarajeMap;
    }

    public Map<String, String> getTiposInmuebleMap() {
        return tiposInmuebleMap;
    }

    public Map<String, String> getTiposMonedaMap() {
        return tiposMonedaMap;
    }

    public Map<String, String> getTiposOficinaMap() {
        return tiposOficinaMap;
    }

    public Map<String, String> getTiposOrientacionMap() {
        return tiposOrientacionMap;
    }

    public Map<String, String> getTiposPisoMap() {
        return tiposPisoMap;
    }

    public Map<String, String> getTiposPuertaMap() {
        return tiposPuertaMap;
    }

    public Map<String, String> getTiposSueloMap() {
        return tiposSueloMap;
    }

    public Map<String, String> getTiposTendederoMap() {
        return tiposTendederoMap;
    }

    public Map<String, String> getTiposTerrenoMap() {
        return tiposTerrenoMap;
    }

    public Map<String, String> getTiposVentanaMap() {
        return tiposVentanaMap;
    }

    public MyInmueblesAux(BdUtils bdUtils) throws AlfaException {
        this.bdUtils = bdUtils;

        antiguedadesMap = listToMap(getTauxList("antiguedades"));
        areasFachadaMap = listToMap(getTauxList("areas_fachada"));
        calificacionesEnergMap = listToMap(getTauxList("calificaciones_energeticas"));
        distribucionesMap = listToMap(getTauxList("distribuciones"));
        estadosConservacionMap = listToMap(getTauxList("estados_conservacion"));
        interiorExteriorMap = listToMap(getTauxList("interior_exterior"));
        localizacionLocalMap = listToMap(getTauxList("localizaciones_local"));
        plantasPisoMap = listToMap(getTauxList("plantas_piso"));
        puertasPisoMap = listToMap(getTauxList("puertas_piso"));
        tiposCalefaccionMap = listToMap(getTauxList("tipos_calefaccion"));
        subtiposCalefaccionMap = listToMap(getTauxList("subtipos_calefaccion"));
        tiposAAMap = listToMap(getTauxList("tipos_aa"));
        tiposAguaMap = listToMap(getTauxList("tipos_agua_caliente"));
        tiposCableadoMap = listToMap(getTauxList("tipos_cableado"));

        tiposChaletMap = listToMap(getTauxList("tipos_chalet"));
        tiposEquipamientoMap = listToMap(getTauxList("tipos_equipamiento"));
        tiposGarajeMap = listToMap(getTauxList("tipos_garaje"));
        tiposInmuebleMap = listToMap(getTauxList("tipos_inmueble"));
        tiposMonedaMap = listToMap(getTauxList("tipos_moneda"));
        tiposOficinaMap = listToMap(getTauxList("tipos_oficina"));
        tiposOrientacionMap = listToMap(getTauxList("tipos_orientacion"));
        tiposPisoMap = listToMap(getTauxList("tipos_piso"));

        tiposPuertaMap = listToMap(getTauxList("tipos_puerta"));
        tiposSueloMap = listToMap(getTauxList("tipos_suelo"));
        tiposTendederoMap = listToMap(getTauxList("tipos_tendedero"));
        tiposTerrenoMap = listToMap(getTauxList("tipos_terreno"));
        tiposVentanaMap = listToMap(getTauxList("tipos_ventana"));

    }

    /**
     * @param tabla
     * @return
     * @throws AlfaException
     */
    private List<IdDescription> getTauxList(String tabla) throws AlfaException {
        return bdUtils.getDataList("SELECT * FROM taux_" + tabla, IdDescription.class);
    }

    /**
     * @param items
     * @return
     */
    private Map<String, String> listToMap(List<IdDescription> items) {

        Map<String, String> result = new HashMap<>();
        for (IdDescription item : items) {
            result.put(item.getId(), item.getDescription());
        }

        return result;
    }

}

package utn.frba.dds.que_me_pongo.Utilities.Helpers.RecomendacionDeAtuendos;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import utn.frba.dds.que_me_pongo.Model.Prenda;

public class FIltroForma {

    public static Boolean isComposicionAuendoCorrecta(Map<String, List<Prenda>> map){
        return map.containsKey("Superior") &&
                map.containsKey("Inferior") &&
                map.containsKey("Calzado");
    }

    public static Boolean noSuperposicion(Map<String, List<Prenda>> map){
        for (List<Prenda> p: map.values()) {
            Map<Integer, List<Prenda>> prendaMaped =  p.stream().collect(Collectors.groupingBy(Prenda::getIndiceSuperposicion));
            Long cantidad = prendaMaped.values().stream().filter(list -> list.size() > 1).count();
            if(cantidad > 0)
                return false;
        }
        return true;
    }
}

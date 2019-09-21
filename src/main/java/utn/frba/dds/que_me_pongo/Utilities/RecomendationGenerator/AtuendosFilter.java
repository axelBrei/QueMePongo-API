package utn.frba.dds.que_me_pongo.Utilities.RecomendationGenerator;

import utn.frba.dds.que_me_pongo.Helpers.ClimeHelper;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Model.Prenda;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AtuendosFilter {

    private static final Set<String> tipos = new HashSet<>(Arrays.asList("Superior", "Inferior", "Calzado", "Accesorio"));

    public static Set<Atuendo> execute(Stream<Atuendo> combinaciones, Evento evento){
        Set<Atuendo> atuendos = new HashSet<>();
        Map<String, Double> climasEvento = ClimeHelper.getClimaParaEvento(evento);

        combinaciones.filter(filtroPorTamano())
                .filter(filtrarPorAbrigo(
                        climasEvento.get(ClimeHelper.MAX_ABRIGO),
                        climasEvento.get(ClimeHelper.MIN_ABRIGO)
                ))
                .forEach( at -> atuendos.add(at));

        return atuendos;
    }

    

    private static Predicate<? super Atuendo> filtroPorTamano(){
        return atuendo -> {
            if(atuendo.getPrendas().size() < 3)
                return false;

            Map<String, List<Prenda>> prendasSeparadas = atuendo.getPrendas().stream().collect(Collectors.groupingBy(Prenda::getTipoDePrenda));
            for (List<Prenda> pr: prendasSeparadas.values()) {
                if(pr.size() > 3)
                    return false;
            }
            if(isComposicionAuendoCorrecta(prendasSeparadas)){
                return noSuperposicion(prendasSeparadas);
            }
            return false;
        };
    }

    private static Boolean isComposicionAuendoCorrecta(Map<String, List<Prenda>> map){
        return map.containsKey("Superior") &&
                map.containsKey("Inferior") &&
                map.containsKey("Calzado");
    }

    private static Boolean noSuperposicion(Map<String, List<Prenda>> map){
        for (List<Prenda> p: map.values()) {
            Map<Integer, List<Prenda>> prendaMaped =  p.stream().collect(Collectors.groupingBy(Prenda::getIndiceSuperposicion));
            Long cantidad = prendaMaped.values().stream().filter(list -> list.size() > 1).count();
            if(cantidad > 0)
                return false;
        }
        return true;
    }

    private static Predicate<? super Atuendo> filtrarPorAbrigo(Double max, Double min){
        return atuendo -> {
            Double sumatoria =  atuendo.getPrendas().stream().mapToDouble(Prenda::getAbrigo).sum();
            return sumatoria >= min && sumatoria <= max;
        };
    }
}

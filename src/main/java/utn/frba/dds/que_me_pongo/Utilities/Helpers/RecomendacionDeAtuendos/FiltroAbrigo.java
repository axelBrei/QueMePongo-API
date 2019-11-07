package utn.frba.dds.que_me_pongo.Utilities.Helpers.RecomendacionDeAtuendos;

import java.util.function.Predicate;

import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Prenda;

public class FiltroAbrigo {

    public static Predicate<? super Atuendo> filtrarPorAbrigo(Double max, Double min){
        return atuendo -> {
            Double sumatoria =  atuendo.getPrendas().stream().mapToDouble(Prenda::getAbrigo).sum();
            return sumatoria >= min && sumatoria <= max;
        };
    }
}

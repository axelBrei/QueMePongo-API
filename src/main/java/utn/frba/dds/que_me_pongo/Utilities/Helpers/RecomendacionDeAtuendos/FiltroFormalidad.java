package utn.frba.dds.que_me_pongo.Utilities.Helpers.RecomendacionDeAtuendos;

import java.util.List;
import java.util.function.Predicate;

import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Model.Prenda;

public class FiltroFormalidad {

    public static List<Prenda> filtrarPorFormalidad(List<Prenda> prendas, Evento evento){
        prendas.removeIf(p -> filtrar(p,evento.getFormalidad()));
        return prendas;
    }

    private static Boolean filtrar(Prenda prenda,String formalidad){
        return ! (Boolean) prenda.getFormalidad().equals(formalidad);
    }
}

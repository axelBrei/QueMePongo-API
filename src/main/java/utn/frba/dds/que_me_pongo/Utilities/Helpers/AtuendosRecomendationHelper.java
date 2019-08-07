package utn.frba.dds.que_me_pongo.Utilities.Helpers;

import org.paukov.combinatorics3.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utn.frba.dds.que_me_pongo.Controller.ClimaAPIs.ClimaApiUNO;
import utn.frba.dds.que_me_pongo.Helpers.ClimeHelper;
import utn.frba.dds.que_me_pongo.Helpers.PrendasReservadas;
import utn.frba.dds.que_me_pongo.Helpers.ReservaHelper;
import utn.frba.dds.que_me_pongo.Model.*;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.PrendaReservadaRespository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AtuendosRecomendationHelper {

    private static final Set<String> tipos = new HashSet<>(Arrays.asList("Superior", "Inferior", "Calzado", "Accesorio"));
    private List<Prenda> getPrendasDisponibles(Guardarropa guardarropa, Evento evento, PrendaReservadaRespository pr){
        List<PrendasReservadas> prList = pr.prendasReservadasList();
        ReservaHelper rh = new ReservaHelper();
        List<Prenda> prendasLibres = rh.prendasDisponibles(guardarropa.getPrendas().stream().collect(Collectors.toList()), evento,prList);
        prendasLibres.removeIf(p-> filtrarPorFormalidad(p,evento.getFormalidad()));
        return prendasLibres;
    }

//    Filtrar prendas
    public Set<Atuendo> generarAtuendos(String uid, int idGuardarropa,Evento evento,  ClientesRepository clientesRepository, PrendaReservadaRespository pr){
        Set<Atuendo> atuendos = new HashSet<>();
        Guardarropa guardarropa = clientesRepository.findClienteByUid(uid).getGuardarropa(idGuardarropa);
        List<PrendasReservadas> prList = pr.prendasReservadasList();
        ReservaHelper rh = new ReservaHelper();
        List<Prenda> prendasLibres = rh.prendasDisponibles(guardarropa.getPrendas().stream().collect(Collectors.toList()), evento,prList);

        if(evento.getFormalidad() != null)
            prendasLibres.removeIf(p-> filtrarPorFormalidad(p,evento.getFormalidad()));

        Map<String, Double> climasEvento = ClimeHelper.getClimaParaEvento(evento);

        Generator.subset(prendasLibres)
                .simple()
                .stream()
                .map( prendas -> new Atuendo(prendas))
                .filter(filtroPorTamano())
                .filter(filtrarPorAbrigo(
                        climasEvento.get(ClimeHelper.MAX_ABRIGO),
                        climasEvento.get(ClimeHelper.MIN_ABRIGO)
                ))
                .forEach( at -> atuendos.add(at));
        return atuendos;
    }

    private Predicate<? super Atuendo> filtroPorTamano(){
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

    private Boolean isComposicionAuendoCorrecta(Map<String, List<Prenda>> map){
        return map.containsKey("Superior") &&
                map.containsKey("Inferior") &&
                map.containsKey("Calzado");
    }

    private Boolean noSuperposicion(Map<String, List<Prenda>> map){
        for (List<Prenda> p: map.values()) {
            Map<Integer, List<Prenda>> prendaMaped =  p.stream().collect(Collectors.groupingBy(Prenda::getIndiceSuperposicion));
            Long cantidad = prendaMaped.values().stream().filter(list -> list.size() > 1).count();
            if(cantidad > 0)
                return false;
        }
        return true;
    }

    private Predicate<? super Atuendo> filtrarPorAbrigo(Double max, Double min){
        return atuendo -> {
            Double sumatoria =  atuendo.getPrendas().stream().mapToDouble(Prenda::getAbrigo).sum();
            return sumatoria >= min && sumatoria <= max;
        };
    }

    private Boolean filtrarPorFormalidad(Prenda prenda,String formalidad){
        return ! (Boolean) prenda.getFormalidad().equals(formalidad);
    }
}

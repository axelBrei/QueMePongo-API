package utn.frba.dds.que_me_pongo.Helpers;

import org.paukov.combinatorics3.Generator;
import org.springframework.stereotype.Service;
import utn.frba.dds.que_me_pongo.Model.*;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.PrendaReservadaRespository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AtuendosRecomendationHelper {



    private static final Set<String> tipos = new HashSet<>(Arrays.asList("Superior", "Inferior", "Calzado", "Accesorio"));



//    Filtrar prendas
    public Set<Atuendo>     generarAtuendos(String uid, int idGuardarropa, ClientesRepository clientesRepository){
        Set<Atuendo> atuendos = new HashSet<>();
        Guardarropa guardarropa = clientesRepository.findClienteByUid(uid).getGuardarropa(idGuardarropa);

        Generator.subset(guardarropa.getPrendas())
                .simple()
                .stream()
                // TODO: agregar un id unico a cada atuendo para guardarselo al usuario cuando deba usarlo
                .map( prendas -> new Atuendo(prendas))
                .filter(filtroPorTamano())
                .filter(filtrarPorAbrigo(100.0))
                .forEach( at -> atuendos.add(at));
        return atuendos;
    }


    //    Filtrar prendas
    public Set<Atuendo>  generarAtuendosParaEvento(String uid, int idGuardarropa, Evento evento,
                                                      ClientesRepository clientesRepository, PrendaReservadaRespository pr){
        Set<Atuendo> atuendos = new HashSet<>();
        Guardarropa guardarropa = clientesRepository.findClienteByUid(uid).getGuardarropa(idGuardarropa);
        List<PrendasReservadas> prList = pr.prendasReservadasList();
        ReservaHelper rh = new ReservaHelper();
        List<Prenda> prendasLibres = rh.prendasDisponibles(guardarropa.getPrendas().stream().collect(Collectors.toList()), evento,prList);

        Generator.subset(prendasLibres)
                .simple()
                .stream()
                // TODO: agregar un id unico a cada atuendo para guardarselo al usuario cuando deba usarlo
                .map( prendas -> new Atuendo(prendas))
                .filter(filtroPorTamano())
                .filter(filtrarPorAbrigo(100.0))
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

    private Predicate<? super Atuendo> filtrarPorAbrigo(Double abrigoRequerido){
        return atuendo -> {
            Double sumatoria =  atuendo.getPrendas().stream().mapToDouble(Prenda::getAbrigo).sum();
            return sumatoria > abrigoRequerido;
        };
    }
}

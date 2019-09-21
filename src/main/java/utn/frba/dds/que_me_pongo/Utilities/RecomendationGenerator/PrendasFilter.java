package utn.frba.dds.que_me_pongo.Utilities.RecomendationGenerator;

import utn.frba.dds.que_me_pongo.Helpers.PrendasReservadas;
import utn.frba.dds.que_me_pongo.Helpers.ReservaHelper;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Repository.PrendaReservadaRespository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PrendasFilter {

    static public List<Prenda> execute(Guardarropa guardarropa, Evento evento, PrendaReservadaRespository pr){
        List<PrendasReservadas> prList = pr.prendasReservadasList();
        ReservaHelper rh = new ReservaHelper();
        List<Prenda> prendasLibres = rh.prendasDisponibles(guardarropa.getPrendas().stream().collect(Collectors.toList()), evento,prList);
        prendasLibres.removeIf(p-> filtrarPorFormalidad(p,evento.getFormalidad()));
        return prendasLibres;
    }


    private static Boolean filtrarPorFormalidad(Prenda prenda, String formalidad){
        return ! (Boolean) prenda.getFormalidad().equals(formalidad);
    }
}

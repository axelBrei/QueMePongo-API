package utn.frba.dds.que_me_pongo.Utilities.RecomendationGenerator;


import org.paukov.combinatorics3.Generator;
import org.springframework.stereotype.Service;

import utn.frba.dds.que_me_pongo.Helpers.ClimeHelper;
import utn.frba.dds.que_me_pongo.Helpers.PrendasReservadas;
import utn.frba.dds.que_me_pongo.Helpers.ReservaHelper;
import utn.frba.dds.que_me_pongo.Model.*;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.PrendaReservadaRespository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AtuendosRecomendationHelper {

    public static Set<Atuendo> execute(String uid, int idGuardarropa,Evento evento,  ClientesRepository clientesRepository, PrendaReservadaRespository pr){
        Guardarropa guardarropa = clientesRepository.findClienteByUid(uid).getGuardarropa(idGuardarropa);

        List<Prenda> prendas =  PrendasFilter.execute(guardarropa,evento,pr);

        Stream<Atuendo> atuendoStream = PrendasCombiner.execute(prendas);

        Set<Atuendo> atuendos = AtuendosFilter.execute(atuendoStream,evento);

        return atuendos;
    }






}

package utn.frba.dds.que_me_pongo.Utilities.RecomendationGenerator;

import org.paukov.combinatorics3.Generator;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Prenda;

import java.util.List;
import java.util.stream.Stream;

public class PrendasCombiner {

    static public Stream<Atuendo> execute(List<Prenda> prendasLibres) {

        return Generator.subset(prendasLibres)
                .simple()
                .stream()
                .map(prendas -> new Atuendo(prendas));

    }

}

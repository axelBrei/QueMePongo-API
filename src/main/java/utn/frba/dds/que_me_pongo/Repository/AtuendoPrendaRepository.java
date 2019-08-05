package utn.frba.dds.que_me_pongo.Repository;

import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Prenda;

public interface AtuendoPrendaRepository {
    Prenda addPrendaToAtuendo(Atuendo a, Prenda p);
    boolean removePrendaDelAtuendo(Atuendo c, int id);
}

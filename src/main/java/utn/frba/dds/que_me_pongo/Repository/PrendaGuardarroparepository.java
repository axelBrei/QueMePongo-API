package utn.frba.dds.que_me_pongo.Repository;

import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.Prenda;

public interface PrendaGuardarroparepository {
    Prenda addPrendaToGuardarropa(Guardarropa g, Prenda p);
    void eleminiarPrendaDelGuardarropa(Guardarropa g, int prendaId);
}

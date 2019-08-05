package utn.frba.dds.que_me_pongo.Repository;

import org.springframework.stereotype.Repository;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.Prenda;


public interface AtuendoGuardarropaRepository {
    Atuendo addAtuendoToGuardarropa(Guardarropa g, Atuendo p);
    void eleminiarAtuendoDelGuardarropa(Guardarropa g, int atuendoId);
}

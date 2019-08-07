package utn.frba.dds.que_me_pongo.Repository.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Repository.AtuendoGuardarropaRepository;
import utn.frba.dds.que_me_pongo.Repository.AtuendoRepository;
import utn.frba.dds.que_me_pongo.Repository.GuardarropasRepository;

@Repository
public class AtuendoGuardarropaRepositoryImp implements AtuendoGuardarropaRepository {
    @Autowired
    AtuendoRepository atuendoRepository;

    @Autowired
    GuardarropasRepository guardarropasRepository;

    @Override
    public Atuendo addAtuendoToGuardarropa(Guardarropa g, Atuendo p) {
        p = atuendoRepository.save(p);
        g.addAtuendo(p);
        guardarropasRepository.save(g);
        return p;
    }

    @Override
    public void eleminiarAtuendoDelGuardarropa(Guardarropa g, int atuendoId) {
        if(g.deletePrenda(atuendoId)){
            atuendoRepository.deleteById(atuendoId);
            guardarropasRepository.save(g);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error al intentar eliminar la prenda: " + atuendoId);
        }
    }
}

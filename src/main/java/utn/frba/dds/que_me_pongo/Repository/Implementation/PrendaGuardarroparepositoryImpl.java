package utn.frba.dds.que_me_pongo.Repository.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Repository.GuardarropasRepository;
import utn.frba.dds.que_me_pongo.Repository.PrendaGuardarroparepository;
import utn.frba.dds.que_me_pongo.Repository.PrendasRepository;

@Repository
public class PrendaGuardarroparepositoryImpl implements PrendaGuardarroparepository {

    @Autowired
    PrendasRepository prendasRepository;

    @Autowired
    GuardarropasRepository guardarropasRepository;

    @Override
    public Prenda addPrendaToGuardarropa(Guardarropa g, Prenda p) {
        p = prendasRepository.save(p);
        g.addPrenda(p);
        guardarropasRepository.save(g);
        return p;
    }

    @Override
    public void eleminiarPrendaDelGuardarropa(Guardarropa g, int prendaId) {
        if(g.deletePrenda(prendaId)){
            prendasRepository.deleteById(prendaId);
            guardarropasRepository.save(g);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error al intentar eliminar la prenda: " + prendaId);
        }
    }
}

package utn.frba.dds.que_me_pongo.Repository.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Repository.AtuendoPrendaRepository;
import utn.frba.dds.que_me_pongo.Repository.AtuendoRepository;
import utn.frba.dds.que_me_pongo.Repository.PrendasRepository;

@Repository
public class AtuendoPrendaRepositoryImp implements AtuendoPrendaRepository {
    @Autowired
    AtuendoRepository atuendoRepository;

    @Autowired
    PrendasRepository prendasRepository;

    @Override
    public Prenda addPrendaToAtuendo(Atuendo c, Prenda r) {
        r = prendasRepository.save(r);
        c.anadirPrenda(r);
        atuendoRepository.save(c);
        return r;
    }

    @Override
    public boolean removePrendaDelAtuendo(Atuendo c, int id) {
        return false;
    }
}

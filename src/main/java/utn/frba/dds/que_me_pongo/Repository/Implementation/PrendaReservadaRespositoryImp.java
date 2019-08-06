package utn.frba.dds.que_me_pongo.Repository.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import utn.frba.dds.que_me_pongo.Helpers.PrendasReservadas;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Repository.PrendaReservadaRespository;
import utn.frba.dds.que_me_pongo.Repository.ReservasRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PrendaReservadaRespositoryImp implements PrendaReservadaRespository {
    @Autowired
    EntityManager entityManager;

    @Override
    public List<PrendasReservadas> prendasReservadasList() {
        Query query = entityManager.createNativeQuery(
                        "SELECT atuendos_prendas.prendas_id , e.desde , e.hasta "+
                        "FROM public.reservas as r "+
                        "JOIN public.atuendos_prendas ON atuendos_prendas.atuendo_id = r.atuendo_id "+
                        "JOIN public.eventos as e ON e.id = r.evento_id"
            );

            List<Object[]> prendas = query.getResultList();
            return prendas.stream().map( o ->
                    new PrendasReservadas(new Long((Integer) o[0]), (Date)o[1], (Date)o[2])
            ).collect(Collectors.toList());
    }
}


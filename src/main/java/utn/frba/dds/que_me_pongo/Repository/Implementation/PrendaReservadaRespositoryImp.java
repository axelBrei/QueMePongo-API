package utn.frba.dds.que_me_pongo.Repository.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utn.frba.dds.que_me_pongo.Helpers.PrendasReservadas;
import utn.frba.dds.que_me_pongo.Repository.PrendaReservadaRespository;

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
                "SELECT e.id , eventos.desde , eventos.hasta "+
                "FROM public.eventos "+
                "JOIN public.atuendos_prendas as at ON at.atuendo_id = eventos.atuendo_id "+
                "JOIN public.prendas as e ON e.id = at.prendas_id;"
            );

            List<Object[]> prendas = query.getResultList();
            return prendas.stream().map( o ->
                    new PrendasReservadas(new Long((Integer) o[0]), (Date)o[1], (Date)o[2])
            ).collect(Collectors.toList());
    }
}


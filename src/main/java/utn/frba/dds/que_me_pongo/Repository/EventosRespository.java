package utn.frba.dds.que_me_pongo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import utn.frba.dds.que_me_pongo.Model.Evento;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface EventosRespository extends JpaRepository<Evento, Long> {
    Set<Evento> findAllByDesdeBetween(Date incio, Date fin);
    Set<Evento> findAllByHastaBetween(Date incio, Date fin);

    Evento getOne(Long idEvento);
}

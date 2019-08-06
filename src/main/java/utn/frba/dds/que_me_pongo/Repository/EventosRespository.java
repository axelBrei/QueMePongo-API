package utn.frba.dds.que_me_pongo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frba.dds.que_me_pongo.Model.Evento;

public interface EventosRespository extends JpaRepository<Evento, Integer> {
}

package utn.frba.dds.que_me_pongo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.frba.dds.que_me_pongo.Model.Reserva;

@Repository
public interface ReservasRepository  extends JpaRepository<Reserva, Integer> {

}

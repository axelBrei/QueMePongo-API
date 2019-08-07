package utn.frba.dds.que_me_pongo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import utn.frba.dds.que_me_pongo.Model.*;

import java.util.Set;

@Repository
public interface ReservasRepository  extends JpaRepository<Reserva, Integer> {
    @Transactional
    void deleteById(Long id);
}

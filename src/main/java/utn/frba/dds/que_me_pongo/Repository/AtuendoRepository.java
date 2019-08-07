package utn.frba.dds.que_me_pongo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
@Repository
public interface AtuendoRepository extends JpaRepository<Atuendo, Integer> {
    Atuendo getAtuendoById(Long id);
}

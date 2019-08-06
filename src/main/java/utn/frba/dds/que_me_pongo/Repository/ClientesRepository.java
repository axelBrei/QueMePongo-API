package utn.frba.dds.que_me_pongo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Reserva;

@Repository
public interface ClientesRepository extends JpaRepository<Cliente, Integer> {
    Cliente findClienteByUid(String uid);
    Cliente findClienteById(Long id);
    void deleteByUid(String id);
}

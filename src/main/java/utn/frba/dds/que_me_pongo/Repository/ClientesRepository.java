package utn.frba.dds.que_me_pongo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import utn.frba.dds.que_me_pongo.Model.Cliente;

@Repository
public interface ClientesRepository extends JpaRepository<Cliente, Integer> {
    Cliente findClienteByUid(String uid);

    void deleteByUid(String id);
}

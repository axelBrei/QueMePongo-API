package utn.frba.dds.que_me_pongo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.GuardarropaCompartido;

@Repository
public interface ClienteGuardarropaRepository {
    Guardarropa addGuardarropaToCliente(Cliente c, Guardarropa g);
    boolean removeGuardarropaDelCliente(Cliente c, int id);
    List<GuardarropaCompartido> getGuardarropsCompartidosDelCliente(String uid, Integer idGuardarropa);
}

package utn.frba.dds.que_me_pongo.Repository;

import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;

public interface ClienteGuardarropaRepository {
    Guardarropa addGuardarropaToCliente(Cliente c, Guardarropa g);
    boolean compartirToCliente(Cliente c, Guardarropa g);
    boolean dejarDeCompartirToCliente(Cliente c, Guardarropa g);
    boolean removeGuardarropaDelCliente(Cliente c, int id);
}

package utn.frba.dds.que_me_pongo.Repository;

import utn.frba.dds.que_me_pongo.Model.Cliente;

public interface EventosClienteRepository {
    Cliente clienteDelEvento(Long id);
}

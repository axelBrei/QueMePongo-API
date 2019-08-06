package utn.frba.dds.que_me_pongo.Repository.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.EventosClienteRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Repository
public class EventosClienteRepositoryImp implements EventosClienteRepository {
    @Autowired
    EntityManager entityManager;
    @Autowired
    ClientesRepository clientesRepository;

    @Override
    public Cliente clienteDelEvento(Long idE) {
        Query query = entityManager.createNativeQuery(
                "SELECT cliente_id " +
                        "FROM public.clientes_eventos "+
                            "WHERE eventos_id = ?1 ;"
        ).setParameter(1,idE);

        List<BigInteger> list = query.getResultList();
        return clientesRepository.findClienteById(list.get(0).longValue());
    }
}

package utn.frba.dds.que_me_pongo.Repository.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.GuardarropaCompartido;
import utn.frba.dds.que_me_pongo.Repository.ClienteGuardarropaRepository;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.GuardarropasRepository;

@Repository
public class ClienteGuardarropaRepositoryImpl implements ClienteGuardarropaRepository {

    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    GuardarropasRepository guardarropasRepository;
    @Autowired
    EntityManager entityManager;


    public Guardarropa addGuardarropaToCliente(Cliente c, Guardarropa g){
        g = guardarropasRepository.save(g);
        c.addGuardarropa(g);
        clientesRepository.save(c);
        return g;
    }

    @Override
    public boolean removeGuardarropaDelCliente(Cliente c, int id) {
       try{
           c.deleteGuardarropa(id);
           clientesRepository.save(c);
           guardarropasRepository.deleteById(id);
           return true;
       }catch (Exception e){
           return false;
       }
    }

    @Override
    public List<GuardarropaCompartido> getGuardarropsCompartidosDelCliente(String uid) {
        Query query = entityManager.createNativeQuery(
                "SELECT DISTINCT clientes.uid, g.id, g.descripcion, clientes.name" +
                " FROM public.guardarropas as g" +
                " JOIN public.clientes_guardarropas ON clientes_guardarropas.guardarropas_id = g.id" +
                " JOIN public.clientes ON clientes.id = clientes_guardarropas.cliente_id" +
                " WHERE not g.uid_dueno = clientes.uid AND g.uid_dueno = ?1"
            ).setParameter(1, uid);

        List<Object[]> guardarropas = query.getResultList();
        return guardarropas.stream().map( o ->
                new GuardarropaCompartido((String) o[0], (Integer)o[1], (String)o[2], (String)o[3])
        ).collect(Collectors.toList());
    }
}

package utn.frba.dds.que_me_pongo.Repository.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Repository.ClienteGuardarropaRepository;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.GuardarropasRepository;

@Repository
public class ClienteGuardarropaRepositoryImpl implements ClienteGuardarropaRepository {

    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    GuardarropasRepository guardarropasRepository;



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
           return true;
       }catch (Exception e){
           return false;
       }
    }
}

package utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer;

import org.springframework.http.HttpStatus;
import utn.frba.dds.que_me_pongo.Exceptions.GuardarropaNotFoundException;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;

import java.util.ArrayList;
import java.util.List;

public class ClienteContainer {
    private String userName;
    private String nombre;


    private List<GuardarropasContainer> guardarropas = new ArrayList<GuardarropasContainer>();

    public ClienteContainer(Cliente cliente){
        this.userName=cliente.getUser();
        this.nombre=cliente.getName();
        for (Guardarropa g : cliente.getGuardarropas()) {
            guardarropas.add(new GuardarropasContainer(g));
        }
    }




    public GuardarropasContainer getGuardarropa(int id) throws GuardarropaNotFoundException {
        return guardarropas
                .stream()
                .filter( guardarropasContainer -> guardarropasContainer.getId() == id)
                .findFirst()
                .orElseThrow( () -> new GuardarropaNotFoundException(HttpStatus.NOT_FOUND,id));
    }

    public String getUserName(){return  this.userName;}

    public Cliente getCliente(){
        Cliente cliente = new Cliente(this.userName,this.nombre);
        for(GuardarropasContainer g : guardarropas){
            Guardarropa guardarropa = new Guardarropa(g.getDesc());
            guardarropa.setId(g.getId());
            guardarropa.aniadirPrendas(g.getPrendas());
            cliente.addGuardarropa(guardarropa);
        }

        return cliente;
    }

}

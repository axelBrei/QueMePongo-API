package utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer;

import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Responses.ResponseObjects.PrendaResponseObject;

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




    public GuardarropasContainer getGuardarropa(int id){

        for (GuardarropasContainer g: guardarropas) {
            if (g.getId() == id) {
                return g;
            }
        }

        return null;
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

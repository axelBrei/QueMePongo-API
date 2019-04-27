package utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer;

import org.springframework.http.HttpStatus;
import utn.frba.dds.que_me_pongo.Exceptions.GuardarropaNotFoundException;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;

import java.util.ArrayList;
import java.util.List;

public class ClienteContainer {
    private String uid;
    private String mail;
    private String nombre;


    private List<GuardarropasContainer> guardarropas = new ArrayList<GuardarropasContainer>();

    public ClienteContainer(Cliente cliente){
        this.uid=cliente.getUid();
        this.nombre=cliente.getName();
        this.mail = cliente.getMail();
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

    public Cliente getCliente(){
        Cliente cliente = new Cliente(this.uid,this.mail,this.nombre);
        for(GuardarropasContainer g : guardarropas){
            Guardarropa guardarropa = new Guardarropa(g.getDesc());
            guardarropa.setId(g.getId());
            guardarropa.aniadirPrendas(g.getPrendas());
            cliente.addGuardarropa(guardarropa);
        }

        return cliente;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<GuardarropasContainer> getGuardarropas() {
        return guardarropas;
    }

    public void setGuardarropas(List<GuardarropasContainer> guardarropas) {
        this.guardarropas = guardarropas;
    }
}

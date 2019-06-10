package utn.frba.dds.que_me_pongo.Model;



import org.springframework.http.HttpStatus;
import utn.frba.dds.que_me_pongo.Exceptions.GuardarropaNotFoundException;
import utn.frba.dds.que_me_pongo.Exceptions.PrendaNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cliente {
    private String uid;
    private String mail;
    private TipoCliente tipoCliente = new TipoCliente().setTipoClienteGratuito();
    @JsonProperty("nombre")
    private String name;


    private List<Guardarropa> guardarropas = new ArrayList<>();

    public Cliente(String uid, String mail, String name) {
        this.uid = uid;
        this.mail = mail;
        this.name = name;
    }

    public Cliente(String mail, String name) {
        this.mail = mail;
        this.name = name;
    }

    public List<Guardarropa> getGuardarropas() {
        return this.guardarropas;
    }

    public void setGuardarropas(List<Guardarropa> guardarropas) {
        this.guardarropas = guardarropas;
    }

    public void addGuardarropa(Guardarropa g){
        this.guardarropas.add(g);
    }

    public void anadirPrendaAlGuardarropa(Prenda prenda, int id) throws GuardarropaNotFoundException {
        Optional<Guardarropa> guardarropaOptional = guardarropas.stream().filter( guardarropa -> guardarropa.getId() == id).findFirst();
        Guardarropa g = guardarropaOptional.orElseThrow( () ->
                new GuardarropaNotFoundException(HttpStatus.NOT_FOUND,id)
        );
        g.addPrenda(prenda);
    }

    public Guardarropa getGuardarropa(int id) throws GuardarropaNotFoundException {
        Optional<Guardarropa> guardarropaOptional = guardarropas.stream().filter( guardarropa -> guardarropa.getId() == id).findFirst();
        return guardarropaOptional.orElseThrow( () ->
            new GuardarropaNotFoundException(HttpStatus.NOT_FOUND,id)
        );
    }

    public void deleteGuardarropa(int id) throws GuardarropaNotFoundException {
        Optional<Guardarropa> guardarropaOptional = guardarropas.stream().filter( guardarropa -> guardarropa.getId() == id).findFirst();
        Guardarropa g = guardarropaOptional.orElseThrow( () ->
                new GuardarropaNotFoundException(HttpStatus.NOT_FOUND,id)
        );
        guardarropas.remove(g);
    }

    public void deletePrendaDelGuardarropa(Prenda prenda,int id) throws GuardarropaNotFoundException, PrendaNotFoundException{
        Optional<Guardarropa> guardarropaOptional = guardarropas.stream().filter(guardarropa -> guardarropa.getId() == id).findFirst();
        Guardarropa g = guardarropaOptional.orElseThrow( () ->
                new GuardarropaNotFoundException(HttpStatus.NOT_FOUND,id)
        );
        if(!g.deletePrenda(prenda))
            throw new PrendaNotFoundException(HttpStatus.NOT_FOUND);
    }

    public String getUid() {
        return uid;
    }

    public List<Prenda> getAllPrendas(){
        Guardarropa nuevo = new Guardarropa();
        getGuardarropas().forEach( g -> nuevo.aniadirPrendas(g.getPrendas()));

        return nuevo.getPrendas();
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setTipoCliente(String uid) {
        this.uid = uid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


package utn.frba.dds.que_me_pongo.Model;



import org.springframework.http.HttpStatus;
import utn.frba.dds.que_me_pongo.Exceptions.GuardarropaNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cliente {
    private String userName;
    private String nombre;


    private List<Guardarropa> guardarropas = new ArrayList<Guardarropa>();

    public Cliente(String userName, String nombre)  {
        this.nombre=nombre;
        this.userName=userName;


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

    public void deletePrendaDelGuardarropa(int idPrenda,int id) throws GuardarropaNotFoundException {
        Optional<Guardarropa> guardarropaOptional = guardarropas.stream().filter(guardarropa -> guardarropa.getId() == id).findFirst();
        Guardarropa g = guardarropaOptional.orElseThrow( () ->
                new GuardarropaNotFoundException(HttpStatus.NOT_FOUND,id)
        );
        g.deletePrenda(idPrenda);
    }

    public String getUser(){return this.userName;}
    public String getName(){return this.nombre;}




}


package utn.frba.dds.que_me_pongo.Model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import org.springframework.http.HttpStatus;
import utn.frba.dds.que_me_pongo.Exceptions.GuardarropaNotFoundException;
import utn.frba.dds.que_me_pongo.Exceptions.PrendaNotFoundException;
import utn.frba.dds.que_me_pongo.Helpers.AtuendosRecomendationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class Guardarropa{
    @JsonProperty("descripcion")
    private String descripcion;
    private int id;
    private List<Prenda> prendas = new ArrayList<>();


    public Guardarropa() {
    }

    public Guardarropa(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }
    public void aniadirPrendas(List<Prenda> prendas) {
        this.prendas.addAll(prendas);
    }
    public void addPrenda(Prenda prenda){
        prendas.add(prenda);
    }

    public  boolean  deletePrenda(Prenda prenda){
       return prendas.remove(prenda);
    }

    // getter and setter


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public Atuendo generarAtuendo(){
        AtuendosRecomendationHelper atuendosHelper = new AtuendosRecomendationHelper();
        Atuendo atuendo = atuendosHelper.generarAtuendoRecomendado(
                this.getPrendas(),
                // COndicion para filtrar prendas
                (prenda -> {return true;}),
                //Condicion para filtrar el accesorio
                (prenda -> {return true;})
        );

        return atuendo;
    }

    public Atuendo generarAtuendoParaEvento(Evento evento,ClimaService climaService){
        AtuendosRecomendationHelper atuendosHelper = new AtuendosRecomendationHelper();
        Atuendo atuendo = atuendosHelper.generarAtuendoRecomendado(
                this.getPrendas(),
                // COndicion para filtrar prendas
                (prenda -> {return true;}),
                //Condicion para filtrar el accesorio
                (prenda -> {return true;})
        );

        return atuendo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Prenda> getPrendas() {
        return prendas;
    }
}

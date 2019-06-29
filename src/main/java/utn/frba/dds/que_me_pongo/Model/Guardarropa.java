package utn.frba.dds.que_me_pongo.Model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import org.springframework.http.HttpStatus;
import utn.frba.dds.que_me_pongo.Exceptions.GuardarropaNotFoundException;
import utn.frba.dds.que_me_pongo.Exceptions.NoSePuedoReservarException;
import utn.frba.dds.que_me_pongo.Exceptions.PrendaNotFoundException;
import utn.frba.dds.que_me_pongo.Helpers.AtuendosRecomendationHelper;

import java.util.*;
import java.util.stream.Collectors;

public class Guardarropa{
    @JsonProperty("descripcion")
    private String descripcion;
    private int id;
    private List<Prenda> prendas = new ArrayList<>();
   // private List<AtuendoReservado> atuendosReservados = new ArrayList<>();


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
/*
    public void reservarAtuendo(Atuendo atuendo,Date desde,Date hasta){
        if(existeElAtuendo(atuendo) && estaLibre(atuendo,desde,hasta)) {
            this.atuendosReservados.add(new AtuendoReservado(atuendo, desde, hasta));
        }else {
            throw new NoSePuedoReservarException(HttpStatus.NOT_FOUND);
        }
    }

    public Boolean sePuedeReservar(Atuendo atuendo,Date desde,Date hasta){
        return (existeElAtuendo(atuendo) && estaLibre(atuendo,desde,hasta));
    }


    private Boolean existeElAtuendo(Atuendo atuendo){
        //si existen las prendas en el guardarropa
        return  (atuendo.getPrendas().stream().allMatch(p-> prendas.stream().anyMatch(prenda->prenda.getId().equals(p.getId()))));
    }

    private Boolean estaLibre(Atuendo atuendo, Date desde,Date hasta){
        //solo los atuendos que tengan algua de las prendas
        List<AtuendoReservado> reservas = atuendosReservados.stream().filter(reservado -> reservado.getAtuendo().tieneAlgunaPrenda(atuendo.getPrendas())).collect(Collectors.toList());
        //todas se pueden reservar para ese tiempo
        return  reservas.stream().noneMatch(res -> res.estaReservada(desde,hasta));
    }
*/
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

    public List<Atuendo> generarAllAtuendos(){



        AtuendosRecomendationHelper atuendosHelper = new AtuendosRecomendationHelper();
        List<Atuendo> atuendos = atuendosHelper.generarAtuendosCero( this.getPrendas() );

        return  atuendos;
        //return atuendos.stream().filter(a->a.esCorrecto()).collect(Collectors.toList());
    }

    public List<Atuendo> generarAtuendoParaEvento(Evento evento,ClimaService climaService){
        Float temperatura = climaService.getTemperatura(evento);
        AtuendosRecomendationHelper atuendosHelper = new AtuendosRecomendationHelper();

        List<Atuendo> atuendos = atuendosHelper.generarAllAtuendos(
                this.getPrendas(),
                // COndicion para filtrar prendas
                (prenda -> {return true;}),
                //Condicion para filtrar el accesorio
                (prenda -> {return true;})
        );

        return atuendos.stream().filter(a->a.esCorrecto()).filter(a->a.esSuficienteAbrigado(temperatura)).collect(Collectors.toList());
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Prenda> getPrendas() {
        return prendas;
    }
}

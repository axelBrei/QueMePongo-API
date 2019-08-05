package utn.frba.dds.que_me_pongo.Model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import utn.frba.dds.que_me_pongo.Exceptions.GuardarropaNotFoundException;
import utn.frba.dds.que_me_pongo.Exceptions.NoSePuedoReservarException;
import utn.frba.dds.que_me_pongo.Exceptions.PrendaNotFoundException;
import utn.frba.dds.que_me_pongo.Helpers.AtuendosRecomendationHelper;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Guardarropas")
@NoArgsConstructor
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional
public class Guardarropa{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String descripcion;

    @ElementCollection( targetClass = Prenda.class)
    Set<Prenda> prendas = new HashSet<>();


    public Guardarropa(String descripcion) {
        this.descripcion = descripcion;
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

    public boolean deletePrenda(int idPrenda){
        return this.prendas.removeIf( p -> p.getId()==(idPrenda));
    }

    public String getDescripcion() {
        return descripcion;
    }


    public int getId() {
        return id;
    }

    public Set<Prenda> getPrendas() {
        return prendas;
    }

/*
    public List<Atuendo> generarAtuendoSolo(){
    int cantAccesorios = prendas.stream().filter( prenda -> prenda.descripcion == "Accessorio").collect(Collectors.toList()).size();
    List<Prenda> accesorios = prendas.stream().filter( prenda -> prenda.descripcion == "Accessorio").collect(Collectors.toList());
    int cantSuperiores = prendas.stream().filter( prenda -> prenda.descripcion == "Superior").collect(Collectors.toList()).size();
    List<Prenda> superiores = prendas.stream().filter( prenda -> prenda.descripcion == "Superior").collect(Collectors.toList());
    int cantInferiores = prendas.stream().filter( prenda -> prenda.descripcion == "Inferior").collect(Collectors.toList()).size();
    List<Prenda> inferiores = prendas.stream().filter( prenda -> prenda.descripcion == "Inferior").collect(Collectors.toList());
    int cantCalzado = prendas.stream().filter( prenda -> prenda.descripcion == "Calzado").collect(Collectors.toList()).size();
    List<Prenda> calzados = prendas.stream().filter( prenda -> prenda.descripcion == "Calzado").collect(Collectors.toList());
    List<Atuendo> listaAtuendos;
    for(int i =0; i< cantAccesorios; i++){
        List<Prenda> listaPrendas = new ArrayList<Prenda>();
        listaPrendas.add(accesorios.get(i));
        for(int j = 0 ; j<cantCalzado; j++){
            listaPrendas.add(calzados.get(j));
            for(int k =0; k< cantInferiores; k++){
                listaPrendas.add(inferiores.get(k));
                for(int z=0;z< cantSuperiores;z++){
                    if(listaPrendas)
                }
            }
        }

    }

    }*/
public Atuendo generarAtuendo(){
    AtuendosRecomendationHelper atuendosHelper = new AtuendosRecomendationHelper();
    Atuendo atuendo = atuendosHelper.generarAtuendoRecomendado(
            this.prendas,
            // COndicion para filtrar prendas
            (prenda -> {return true;}),
            //Condicion para filtrar el accesorio
            (prenda -> {return true;})
    );

    return atuendo;
}


    public List<Atuendo> generarAllAtuendos(){
        AtuendosRecomendationHelper atuendosHelper = new AtuendosRecomendationHelper();
        List<Atuendo> atuendos = atuendosHelper.generarAllAtuendos(
                this.getPrendas(),
                // COndicion para filtrar prendas
                (prenda -> {return true;}),
                //Condicion para filtrar el accesorio
                (prenda -> {return true;})
        );


        return atuendos.stream().filter(a->a.esCorrecto()).collect(Collectors.toList());
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
}

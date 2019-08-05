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

    @ElementCollection( targetClass = Atuendo.class)
    Set<Atuendo> atuendos = new HashSet<>();

    public void addAtuendo(Atuendo atuendo){this.atuendos.add(atuendo);}
    public Set<Atuendo> getAtuendos(){return this.atuendos; }

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
        return this.prendas.removeIf( p -> p.getId().equals(idPrenda));
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Prenda getPrenda(int idPrenda){return  this.prendas.stream().filter(p->p.getId().equals(idPrenda)).findAny().orElse(null);}


    public int getId() {
        return id;
    }

    public Set<Prenda> getPrendas() {
        return prendas;
    }



}

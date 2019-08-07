package utn.frba.dds.que_me_pongo.Model;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "Guardarropas")
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional
public class Guardarropa{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String descripcion;
    String uidDueno;

    @ElementCollection( targetClass = Prenda.class)
    Set<Prenda> prendas = new HashSet<>();

    @ElementCollection( targetClass = Atuendo.class)
    Set<Atuendo> atuendos = new HashSet<>();

    public Guardarropa(String descripcion) {
        this.descripcion = descripcion;
    }

    public Guardarropa(int id, String descripcion, String uidDueno) {
        this.id = id;
        this.descripcion = descripcion;
        this.uidDueno = uidDueno;
    }

    public void addAtuendo(Atuendo atuendo){this.atuendos.add(atuendo);}

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

}

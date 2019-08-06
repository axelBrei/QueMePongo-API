package utn.frba.dds.que_me_pongo.Model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

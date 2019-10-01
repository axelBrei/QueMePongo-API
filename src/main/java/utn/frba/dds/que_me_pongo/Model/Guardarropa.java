package utn.frba.dds.que_me_pongo.Model;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

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
        this.setDescripcion(descripcion);
    }

    public Guardarropa(int id, String descripcion, String uidDueno) {
        this.setId(id);
        this.setDescripcion(descripcion);
        this.setUidDueno(uidDueno);
    }

    public void addAtuendo(Atuendo atuendo){
        this.getAtuendos().add(atuendo);}

    public void aniadirPrendas(List<Prenda> prendas) {
        this.getPrendas().addAll(prendas);
    }
    public void addPrenda(Prenda prenda){
        getPrendas().add(prenda);
    }

    public  boolean  deletePrenda(Prenda prenda){
       return getPrendas().remove(prenda);
    }

    public boolean deletePrenda(int idPrenda){
        return this.getPrendas().removeIf(p -> p.getId().equals(idPrenda));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUidDueno() {
        return uidDueno;
    }

    public void setUidDueno(String uidDueno) {
        this.uidDueno = uidDueno;
    }

    public Set<Prenda> getPrendas() {
        return prendas;
    }

    public void setPrendas(Set<Prenda> prendas) {
        this.prendas = prendas;
    }

    public Set<Atuendo> getAtuendos() {
        return atuendos;
    }

    public void setAtuendos(Set<Atuendo> atuendos) {
        this.atuendos = atuendos;
    }
}

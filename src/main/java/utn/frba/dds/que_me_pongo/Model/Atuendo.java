package utn.frba.dds.que_me_pongo.Model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Atuendos")
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Atuendo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    float calificacion;

    @ManyToMany
    @JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = {"atuendo_id","prendas_id"}))
    List<Prenda> prendas = new ArrayList<>();

    public Atuendo(List<Prenda> prendas) {
        this.setPrendas(prendas);
    }

    public Atuendo(Integer id, List<Prenda> prendas) {
        this.setPrendas(prendas);
        this.setId(id);
    }


    public void anadirPrenda(Prenda p){
        getPrendas().add(p);
    }
    public void anadirPrendas(List<Prenda> p){
        getPrendas().addAll(p);
    }

    public Boolean tieneAlgunaPrenda(List<Prenda> prendas){
        return  this.getPrendas().stream().anyMatch(p-> prendas.stream().anyMatch(prenda -> prenda.getId().equals(p.getId())));
    }

    public Boolean esSuficienteAbrigado(Float temperatura){
        Double top = 55.0;
        Double margen = 5.0;
        Double necesita = top - temperatura;

        if(necesita<20.0){
            return this.getAbrigo()==20.0;
        }else{

            return (this.getAbrigo()<=necesita+margen && this.getAbrigo()>=necesita-margen);
        }


    }

    public Double getAbrigo(){
        Double abrigo;
        try {
            abrigo = this.getPrendas().stream().mapToDouble(p -> p.getAbrigo()).sum();
        }catch (NullPointerException e){
            return 0.0;
        }
        return  abrigo;
    }


    public void eliminarPrenda(Prenda p){
        getPrendas().remove(p);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public List<Prenda> getPrendas() {
        return prendas;
    }

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }
}

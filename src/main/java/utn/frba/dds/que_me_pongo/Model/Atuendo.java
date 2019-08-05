package utn.frba.dds.que_me_pongo.Model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.data.annotation.TypeAlias;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

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
    List<Prenda> prendas = new ArrayList<>();


    public Atuendo(List<Prenda> prendas) {
        this.prendas = prendas;
    }

    public Atuendo(Integer id, List<Prenda> prendas) {
        this.prendas = prendas;
        this.id = id;
    }

    public void anadirPrenda(Prenda p){
        prendas.add(p);
    }
    public void anadirPrendas(List<Prenda> p){
        prendas.addAll(p);
    }

    public Boolean tieneAlgunaPrenda(List<Prenda> prendas){
        return  this.prendas.stream().anyMatch(p-> prendas.stream().anyMatch(prenda -> prenda.getId().equals(p.getId())));
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
        Double abrigo =  this.getPrendas().stream().mapToDouble(p -> p.getAbrigo()).sum();
        return  abrigo;
    }

//    public Boolean esCorrecto(){
//        List<Superior> prendaList = this.prendas.stream().filter(p -> p.getClass().equals(Superior.class)).map(prenda -> new Superior().prendaToSuperior(prenda)).collect(Collectors.toList());
//
//
//        Integer cant = prendaList.size();
//
//
//        switch (cant){
//            case 1:
//                return (prendaList.get(0).getTipoSuperior()==0);
//            case 2:
//                return (1==prendaList.stream().mapToInt(Superior::getTipoSuperior).sum());
//            case 3:
//                return (3==prendaList.stream().mapToInt(Superior::getTipoSuperior).sum());
//            case 4:
//                return (6==prendaList.stream().mapToInt(Superior::getTipoSuperior).sum());
//        }
//        return false;
//    }



    public void eliminarPrenda(Prenda p){
        prendas.remove(p);
    }

    public List<Prenda> getPrendas() {
        return prendas;
    }

}

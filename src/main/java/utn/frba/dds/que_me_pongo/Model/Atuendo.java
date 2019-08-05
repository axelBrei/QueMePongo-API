package utn.frba.dds.que_me_pongo.Model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Atuendos")
//@NoArgsConstructor  si no quito esto produce error: constructor Atuendo ya definido
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Atuendo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    float calificacion;

    @ElementCollection(targetClass = Prenda.class)
    List<Prenda> prendas = new ArrayList<>();

    public Atuendo(List<Prenda> prendas) {
        this.prendas = prendas;
    }

    public Atuendo(Integer id, List<Prenda> prendas) {
        this.prendas = prendas;
        this.id = id;
    }
    public Atuendo(){

    }

    public void anadirPrenda(Prenda p){
        prendas.add(p);
    }
    public void anadirPrendas(List<Prenda> p){
        prendas.addAll(p);
    }

    public Boolean tieneAlgunaPrenda(List<Prenda> prendas){
        return  this.prendas.stream().anyMatch(p-> prendas.stream().anyMatch(prenda -> prenda.getId()==p.getId()));
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
        Double abrigo = 0.0;
        for (int i = 0; i < prendas.size(); i++) {
            abrigo += prendas.get(i).getAbrigo();
        }
        return abrigo;
    }

    public Boolean esCorrecto(){
        List<Prenda> prendaList = this.prendas.stream().filter(p -> p.getTipoDePrenda() == "Superior").collect(Collectors.toList());


        Integer cant = prendaList.size();


        switch (cant){
            case 1:
                return (prendaList.get(0).getIndiceSuperposicion()==0);
            case 2:
                return (1==prendaList.stream().mapToInt(Prenda::getIndiceSuperposicion).sum());
            case 3:
                return (3==prendaList.stream().mapToInt(Prenda::getIndiceSuperposicion).sum());
            case 4:
                return (6==prendaList.stream().mapToInt(Prenda::getIndiceSuperposicion).sum());
        }
        return false;
   }



    public void eliminarPrenda(Prenda p){
        prendas.remove(p);
    }

    public List<Prenda> getPrendas() {
        return prendas;
    }

}

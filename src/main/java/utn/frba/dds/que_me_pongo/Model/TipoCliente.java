package utn.frba.dds.que_me_pongo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "TipoCliente")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TipoCliente {

    @Id
    @GeneratedValue
    int id;

    @JsonProperty("nombreTipo")
    String nombre;

    Integer PrendasMax;

    public TipoCliente setTipoClienteGratuito(){
        TipoCliente  cli = new TipoCliente();
        cli.setNombre("Gratuito");
        cli.setPrendasMax(10);
        return cli;
    }

    public TipoCliente setTipoClientePremium(){
        TipoCliente  cli = new TipoCliente();
        cli.setNombre("Premium");
        cli.setPrendasMax(null);
        return cli;
    }

    public Boolean esGratuito(TipoCliente tipoCliente){
        return tipoCliente.getNombre().equals(new TipoCliente().setTipoClienteGratuito().getNombre());
    }

    public Boolean esPremium(TipoCliente tipoCliente){
        return tipoCliente.getNombre().equals(new TipoCliente().setTipoClientePremium().getNombre());
    }

    public boolean puedeAgregarPrenda(Guardarropa guardarropa){
        return !this.getNombre().equals("Gratuito") || guardarropa.getPrendas().size() < this.getPrendasMax();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPrendasMax() {
        return PrendasMax;
    }

    public void setPrendasMax(Integer prendasMax) {
        PrendasMax = prendasMax;
    }
}



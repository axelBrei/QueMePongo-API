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
        return tipoCliente.nombre.equals(new TipoCliente().setTipoClienteGratuito().nombre);
    }

    public Boolean esPremium(TipoCliente tipoCliente){
        return tipoCliente.nombre.equals(new TipoCliente().setTipoClientePremium().nombre);
    }

    public boolean puedeAgregarPrenda(Guardarropa guardarropa){
        return !this.nombre.equals("Gratuito") || guardarropa.getPrendas().size() < this.PrendasMax;
    }
}



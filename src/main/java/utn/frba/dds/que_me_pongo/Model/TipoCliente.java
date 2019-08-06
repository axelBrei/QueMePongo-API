package utn.frba.dds.que_me_pongo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Data
@Entity
@Table(name = "TipoCliente")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TipoCliente {

    @Id
    @Getter
    @Setter
    @GeneratedValue
    int id;

    @JsonProperty("nombreTipo")
    String nombre;

    @JsonProperty("prendasMax")
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
        cli.setPrendasMax(5000);
        return cli;
    }

    public Integer getPrendasMax() {
        return PrendasMax;
    }

    public String getNombre() {
        return nombre;
    }

    public Boolean esGratuito(TipoCliente tipoCliente){
        return tipoCliente.nombre.equals(new TipoCliente().setTipoClienteGratuito().nombre);
    }

    public Boolean esPremium(TipoCliente tipoCliente){
        return tipoCliente.nombre.equals(new TipoCliente().setTipoClientePremium().nombre);
    }

    public void setPrendasMax(int max){
        PrendasMax = max;
    }

    public void setNombre(String Unnombre){
        nombre = Unnombre;
    }
}



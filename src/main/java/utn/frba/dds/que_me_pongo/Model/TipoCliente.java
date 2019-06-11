package utn.frba.dds.que_me_pongo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TipoCliente {
    @JsonProperty("nombreTipo")
    private String nombre;
    @JsonProperty("prendasMax")
    private Integer PrendasMax;

    public TipoCliente setTipoClienteGratuito(){
        this.nombre = "Gratuito";
        this.PrendasMax = 10;
        return this;
    }

    public TipoCliente setTipoClientePremium(){
        this.nombre = "Premium";
        this.PrendasMax = null;
        return this;
    }

    public String getNombre(){return this.nombre;}

    public Integer getPrendasMax(){return this.PrendasMax; }

    public Boolean esGratuito(TipoCliente tipoCliente){
        return tipoCliente.nombre.equals(new TipoCliente().setTipoClienteGratuito().getNombre());
    }

    public Boolean esPremium(TipoCliente tipoCliente){
        return tipoCliente.nombre.equals(new TipoCliente().setTipoClientePremium().getNombre());
    }
}



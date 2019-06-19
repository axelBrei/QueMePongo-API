package utn.frba.dds.que_me_pongo.Model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import utn.frba.dds.que_me_pongo.Helpers.Deserializer.PrendaDeserializer;
import utn.frba.dds.que_me_pongo.Helpers.Serializer.PrendaSerializer;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Prendas")
@JsonDeserialize( using = PrendaDeserializer.class)
@JsonSerialize(using = PrendaSerializer.class)
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Prenda {

    @Id
     Integer id;
     String tipoDeTela = "";
     String descripcion = "";
     String colorP = "";
     String colorS = "";
    // EG. Camprera Remera
     String tipoDePrenda = "";

    public Prenda(Integer id, String tipoDeTela, String descripcion, String colorP, String colorS, String tipoDePrenda) {
        this.id = id;
        this.tipoDeTela = tipoDeTela != null ? tipoDeTela : "";
        this.descripcion = descripcion != null ? descripcion : "";
        this.colorP = colorP != null ? colorP : "";
        this.colorS = colorS != null ? colorS : "";
        this.tipoDePrenda = tipoDePrenda != null ? tipoDePrenda: "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoDeTela() {
        return tipoDeTela;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public String getColorP() {
        return colorP;
    }


    public String getColorS() {
        return colorS;
    }


    public String getTipoDePrenda() {
        return tipoDePrenda;
    }


    public Double getAbrigo(){
        return 0.0;
    }

    @Override
    public boolean equals(Object obj) {
        Prenda p = (Prenda)obj;
        return p.getId() != null && this.id.equals(p.getId());
    }
}



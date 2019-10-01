package utn.frba.dds.que_me_pongo.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Prendas")
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Prenda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String tipoDeTela = "";
    String descripcion = "";
    String colorP = "";
    String colorS = "";
    Double abrigo;
    // FORMAL O INFORMAL
    String formalidad = "";
    // Ej. Superior-4, Inferior
    String tipoDePrenda = "";
    Integer indiceSuperposicion;

    public Prenda(int id, String tipoDeTela, String descripcion, String colorP, String colorS, String tipoDePrenda,Double indiceAbrigo,String formalidad,Integer superposicion) {
      this.setId(id);
      this.setTipoDeTela(tipoDeTela);
      this.setTipoDePrenda(tipoDePrenda);
      this.setDescripcion(descripcion);
      this.setColorP(colorP);
      this.setColorS(colorS);
      this.setAbrigo(indiceAbrigo);
      this.setFormalidad(formalidad);
      this.setIndiceSuperposicion(superposicion);
    }

    @Override
    public boolean equals(Object obj) {
        Prenda p = (Prenda)obj;
        return p.getId() != null && this.getId().equals(p.getId());
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

    public void setTipoDeTela(String tipoDeTela) {
        this.tipoDeTela = tipoDeTela;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getColorP() {
        return colorP;
    }

    public void setColorP(String colorP) {
        this.colorP = colorP;
    }

    public String getColorS() {
        return colorS;
    }

    public void setColorS(String colorS) {
        this.colorS = colorS;
    }

    public Double getAbrigo() {
        return abrigo;
    }

    public void setAbrigo(Double abrigo) {
        this.abrigo = abrigo;
    }

    public String getFormalidad() {
        return formalidad;
    }

    public void setFormalidad(String formalidad) {
        this.formalidad = formalidad;
    }

    public String getTipoDePrenda() {
        return tipoDePrenda;
    }

    public void setTipoDePrenda(String tipoDePrenda) {
        this.tipoDePrenda = tipoDePrenda;
    }

    public Integer getIndiceSuperposicion() {
        return indiceSuperposicion;
    }

    public void setIndiceSuperposicion(Integer indiceSuperposicion) {
        this.indiceSuperposicion = indiceSuperposicion;
    }
}



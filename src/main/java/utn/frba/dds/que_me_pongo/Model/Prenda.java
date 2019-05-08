package utn.frba.dds.que_me_pongo.Model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import utn.frba.dds.que_me_pongo.Helpers.Deserializer.PrendaRequestDeserializer;

@JsonDeserialize( using = PrendaRequestDeserializer.class)
public class Prenda {

    private String tipo = "";
    private Integer id;
    private String tipoDeTela = "";
    private String descripcion = "";
    private String colorP = "";
    private String colorS = "";
    // EG. Camprera Remera
    private String tipoDePrenda = "";

    public Prenda(String tipo, Integer id, String tipoDeTela, String descripcion, String colorP, String colorS, String tipoDePrenda) {
        this.tipo = tipo != null ? tipo : "";
        this.id = id;
        this.tipoDeTela = tipoDeTela != null ? tipoDeTela : "";
        this.descripcion = descripcion != null ? descripcion : "";
        this.colorP = colorP != null ? colorP : "";
        this.colorS = colorS != null ? colorS : "";
        this.tipoDePrenda = tipoDePrenda != null ? tipoDePrenda: "";
    }

    public Prenda() {
    }

    public String getTipo(){
        return tipo;
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

    public String getTipoDePrenda() {
        return tipoDePrenda;
    }

    public void setTipoDePrenda(String tipoDePrenda) {
        this.tipoDePrenda = tipoDePrenda;
    }

    @Override
    public boolean equals(Object obj) {
        Prenda p = (Prenda)obj;
        return p.getId() != null && this.id.equals(p.getId());
    }
}



package utn.frba.dds.que_me_pongo.WebServices.Responses.ResponseObjects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import utn.frba.dds.que_me_pongo.Helpers.Deserializer.PrendaRequestDeserializer;
import utn.frba.dds.que_me_pongo.Model.Prenda;

@JsonDeserialize(using = PrendaRequestDeserializer.class)
public class PrendaResponseObject extends Prenda {
    private String parteQueOcupa;

    public PrendaResponseObject(Prenda p, String parteQueOcupa) {
        super(p.getTipo(), p.getId(), p.getTipoDeTela(), p.getDescripcion(), p.getColorP(), p.getColorS(), p.getTipoDePrenda());
        this.parteQueOcupa = parteQueOcupa.split("utn.frba.dds.que_me_pongo.Model.TiposPrenda.")[1];
    }

    public PrendaResponseObject() {
    }

    public void setParteQueOcupa(String parteQueOcupa) {
        this.parteQueOcupa = parteQueOcupa;
    }

    public String getParteQueOcupa() {
        return parteQueOcupa;
    }
}

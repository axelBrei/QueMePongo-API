package utn.frba.dds.que_me_pongo.Responses.ResponseObjects;

import utn.frba.dds.que_me_pongo.Model.Prenda;

public class PrendaResponseObject extends Prenda {
    private String parteQueOcupa;

    public PrendaResponseObject(Prenda p, String parteQueOcupa) {
        super(p.getTipo(), p.getId(), p.getTipoDeTela(), p.getDescripcion(), p.getColorP(), p.getColorS(), p.getTipoDePrenda());
        this.parteQueOcupa = parteQueOcupa.split("utn.frba.dds.que_me_pongo.Model.TiposPrenda.")[1];
    }

    public void setParteQueOcupa(String parteQueOcupa) {
        this.parteQueOcupa = parteQueOcupa;
    }

    public String getParteQueOcupa() {
        return parteQueOcupa;
    }
}

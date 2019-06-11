package utn.frba.dds.que_me_pongo.Model.TiposPrenda;

import utn.frba.dds.que_me_pongo.Model.Prenda;

public class Inferior extends Prenda {

    public Inferior() {
    }

    public Inferior( Integer id, String tipoDeTela, String descripcion, String colorP, String colorS, String tipoDePrenda) {
        super(id, tipoDeTela, descripcion, colorP, colorS, tipoDePrenda);
    }
    @Override
    public Double getAbrigo(){
        return 5.0;
    }
}

package utn.frba.dds.que_me_pongo.Model.TiposPrenda;

import utn.frba.dds.que_me_pongo.Model.Prenda;

public class Inferior extends Prenda {
    private String tipo = "Inferior";

    public Inferior() {
    }

    public Inferior(String tipo, Integer id, String tipoDeTela, String descripcion, String colorP, String colorS, String tipoDePrenda) {
        super(tipo, id, tipoDeTela, descripcion, colorP, colorS, tipoDePrenda);
    }
}

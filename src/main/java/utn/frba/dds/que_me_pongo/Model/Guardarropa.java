package utn.frba.dds.que_me_pongo.Model;

import utn.frba.dds.que_me_pongo.QueMePongoApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Guardarropa {

    private String descripcion;
    private int id;
    private List<Prenda> prendas ;



    public Guardarropa() {
        this.prendas = new ArrayList<>();
    }

    public List<Prenda> getPrendas() {
        return prendas;
    }

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }



    void eliminarPrenda(Prenda unaPrenda){
        prendas.remove(unaPrenda);
        unaPrenda.liberarDeGuardarropa();
    }

    void aniadirPrenda(Prenda unaPrenda){
        prendas.add(unaPrenda);
        unaPrenda.ocuparEnGuardarropa();
    }
    /*
    List<Prenda> sugerir(){

    }
*/
}

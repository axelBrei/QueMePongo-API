package utn.frba.dds.que_me_pongo.Model;

import utn.frba.dds.que_me_pongo.Model.TiposPrenda.PrendaSuperior;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private List<Guardarropa> guardarropas;
    public List<Prenda> prendasMaestras ;

    public Cliente() {
        this.guardarropas = new ArrayList<>();
    }

    public List<Guardarropa> getGuardarropas() {
        return guardarropas;
    }

    public void setGuardarropas(List<Guardarropa> guardarropas) {
        this.guardarropas = guardarropas;
    }


    public List<Prenda> getPrendasMaestras(){
        return  prendasMaestras;
    };

    public void addGuardarropa(Guardarropa g){
        guardarropas.add(g);
    }

    // FUNCIONALIDAD PARA AGREGAR VARIAS PRENDAS DE UNA
    //    public void setPrendasMaestras( Prenda unaPrenda) { prendasMaestras.add(unaPrenda); }

    public List<Prenda> recibirSugerenciaDe(Guardarropa unGuardarropa){
        return unGuardarropa.sugerirPrendas(this);
    }




}


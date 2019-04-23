package utn.frba.dds.que_me_pongo.Model;

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

  //  public void cargarGuardarropas()

    public List<Prenda> getPrendasMaestras(){
        return  prendasMaestras;
    }

    public List<Prenda> recibirSugerenciaDe(Guardarropa unGuardarropa){
        return unGuardarropa.sugerirPrendas(this);
    }

}


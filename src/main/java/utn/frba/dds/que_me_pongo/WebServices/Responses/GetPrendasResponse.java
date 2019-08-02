package utn.frba.dds.que_me_pongo.WebServices.Responses;

import utn.frba.dds.que_me_pongo.Model.Prenda;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GetPrendasResponse {
    private Set<Prenda> prendas = new HashSet<>();

    public void setPrendas(Set<Prenda> prendas) {
        this.prendas = prendas;
    }

    public Set<Prenda> getPrendas() {
        return prendas;
    }
}


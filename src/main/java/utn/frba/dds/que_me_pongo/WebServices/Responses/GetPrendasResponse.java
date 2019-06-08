package utn.frba.dds.que_me_pongo.WebServices.Responses;

import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.WebServices.Responses.ResponseObjects.PrendaResponseObject;

import java.util.ArrayList;
import java.util.List;

public class GetPrendasResponse {
    private List<Prenda> prendas = new ArrayList<>();

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }

    public List<Prenda> getPrendas() {
        return prendas;
    }
}


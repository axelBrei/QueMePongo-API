package utn.frba.dds.que_me_pongo.WebServices.Responses;

import utn.frba.dds.que_me_pongo.WebServices.Responses.ResponseObjects.PrendaResponseObject;

import java.util.ArrayList;
import java.util.List;

public class GetPrendasResponse {
    private List<PrendaResponseObject> prendas = new ArrayList<>();

    public void setPrendas(List<PrendaResponseObject> prendas) {
        this.prendas = prendas;
    }

    public List<PrendaResponseObject> getPrendas() {
        return prendas;
    }

    public void addPrenda(PrendaResponseObject prenda){
        prendas.add(prenda);
    }
}


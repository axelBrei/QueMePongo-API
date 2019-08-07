package utn.frba.dds.que_me_pongo.Utilities.WebServices.Responses;

import utn.frba.dds.que_me_pongo.Utilities.WebServices.Responses.ResponseObjects.CantidadGuardarropaResponseObject;

import java.util.ArrayList;
import java.util.List;

public class GetCantidadGuardarropasResponse {
    private List<CantidadGuardarropaResponseObject> guardarropas;

    public GetCantidadGuardarropasResponse() {
        guardarropas = new ArrayList<>();
    }

    public GetCantidadGuardarropasResponse(List<CantidadGuardarropaResponseObject> guardarropas) {
        this.guardarropas = guardarropas;
    }

    public List<CantidadGuardarropaResponseObject> getGuardarropas() {
        return guardarropas;
    }

    public void setGuardarropas(List<CantidadGuardarropaResponseObject> guardarropas) {
        this.guardarropas = guardarropas;
    }
}

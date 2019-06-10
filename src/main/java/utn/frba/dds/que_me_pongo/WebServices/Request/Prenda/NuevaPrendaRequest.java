package utn.frba.dds.que_me_pongo.WebServices.Request.Prenda;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import utn.frba.dds.que_me_pongo.Helpers.Deserializer.PrendaDeserializer;
import utn.frba.dds.que_me_pongo.Model.Prenda;

public class NuevaPrendaRequest {
    private String uid;
    private String idGuardarropa;

    @JsonDeserialize(using = PrendaDeserializer.class)
    private Prenda prenda;

    public NuevaPrendaRequest() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIdGuardarropa() {
        return idGuardarropa;
    }

    public void setIdGuardarropa(String idGuardarropa) {
        this.idGuardarropa = idGuardarropa;
    }

    public Prenda getPrenda() {
        return prenda;
    }

    public void setPrenda(Prenda prenda) {
        this.prenda = prenda;
    }
}

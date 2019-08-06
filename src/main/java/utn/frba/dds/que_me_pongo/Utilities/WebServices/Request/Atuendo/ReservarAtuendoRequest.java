package utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Atuendo;

import utn.frba.dds.que_me_pongo.Model.AtuendoReservado;

public class ReservarAtuendoRequest {
    private String username;
    private Integer idGuardarropa;
    private AtuendoReservado atuendo;

    public ReservarAtuendoRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIdGuardarropa() {
        return idGuardarropa;
    }

    public void setIdGuardarropa(Integer idGuardarropa) {
        this.idGuardarropa = idGuardarropa;
    }

    public AtuendoReservado getAtuendo() {
        return atuendo;
    }

    public void setAtuendo(AtuendoReservado atuendo) {
        this.atuendo = atuendo;
    }
}

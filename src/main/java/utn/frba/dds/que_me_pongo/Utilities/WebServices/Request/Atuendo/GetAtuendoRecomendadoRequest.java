package utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Atuendo;

import utn.frba.dds.que_me_pongo.Model.Atuendo;

public class GetAtuendoRecomendadoRequest {
    private String username;
    private Integer idGuardarropa;
    private Atuendo atuendo;

    public GetAtuendoRecomendadoRequest() {
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

    public Atuendo getAtuendo() {
        return atuendo;
    }

    public void setAtuendo(Atuendo atuendo) {
        this.atuendo = atuendo;
    }
}

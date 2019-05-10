package utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo;

public class GetAtuendoRecomendadoRequest {
    private String username;
    private Integer idGuardarropa;

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
}

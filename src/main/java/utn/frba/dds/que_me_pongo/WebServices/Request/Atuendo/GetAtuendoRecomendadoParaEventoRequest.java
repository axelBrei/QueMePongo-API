package utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo;


import utn.frba.dds.que_me_pongo.Model.Evento;

public class GetAtuendoRecomendadoParaEventoRequest {
    private String username;
    private Integer idGuardarropa;
    private Evento  evento;

    public GetAtuendoRecomendadoParaEventoRequest() {
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

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

}

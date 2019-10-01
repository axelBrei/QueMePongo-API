package utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import utn.frba.dds.que_me_pongo.Model.Atuendo;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
public class CalificarAtuendoRequest {
    private String uid;
    private Atuendo atuendo;
    private Integer calificacion;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Atuendo getAtuendo() {
        return atuendo;
    }

    public void setAtuendo(Atuendo atuendo) {
        this.atuendo = atuendo;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }
}

package utn.frba.dds.que_me_pongo.WebServices.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.frba.dds.que_me_pongo.Model.Atuendo;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class AtuendoReservadoResponse {
    private String uid;
    private Date desde;
    private Date hasta;
    private Atuendo atuendo;

    public AtuendoReservadoResponse(String uid ,Date desde, Date hasta, Atuendo atuendo) {
        this.uid = uid;
        this.desde = desde;
        this.hasta = hasta;
        this.atuendo = atuendo;
    }

    public AtuendoReservadoResponse(Date desde, Date hasta, Atuendo atuendo) {
        this.desde = desde;
        this.hasta = hasta;
        this.atuendo = atuendo;
    }


    public Boolean isEqual(AtuendoReservadoResponse a){
        if(a.getAtuendo().getId()==this.getAtuendo().getId() &&
                a.getDesde().getTime()==this.getDesde().getTime() &&
                    a.getHasta().getTime()==this.getHasta().getTime()){
            return true;
        }

        return false;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Atuendo getAtuendo() {
        return atuendo;
    }

    public void setAtuendo(Atuendo atuendo) {
        this.atuendo = atuendo;
    }
}

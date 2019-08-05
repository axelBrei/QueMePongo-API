package utn.frba.dds.que_me_pongo.WebServices.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import utn.frba.dds.que_me_pongo.Model.Atuendo;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
public class AtuendoReservadoResponse {
    private Date desde;
    private Date hasta;
    private Atuendo atuendo;

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

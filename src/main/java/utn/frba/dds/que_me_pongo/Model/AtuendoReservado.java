package utn.frba.dds.que_me_pongo.Model;

import java.util.Date;

public class AtuendoReservado {
    private Atuendo atuendo;
    private Date desde;
    private Date hasta;

    public AtuendoReservado( Atuendo atuendo, Date desde, Date hasta) {

        this.atuendo = atuendo;
        this.desde = desde;
        this.hasta = hasta;
    }


    public Atuendo getAtuendo() {
        return atuendo;
    }

    public void setAtuendo(Atuendo atuendo) {
        this.atuendo = atuendo;
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

    Boolean estaReservada(Date desde, Date hasta){
        return ( (desde.getTime() < this.getDesde().getTime() && hasta.getTime() < this.getDesde().getTime() ) ||
                    (desde.getTime() > this.getHasta().getTime() && hasta.getTime() > this.getHasta().getTime() ));
    }


}

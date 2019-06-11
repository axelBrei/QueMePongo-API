package utn.frba.dds.que_me_pongo.Model.TiposPrenda;

import utn.frba.dds.que_me_pongo.Model.Prenda;

public class Superior extends Prenda {
    private Integer TipoSuperior;
    public Superior() {
    }

    public Superior(Integer id, String tipoDeTela, String descripcion, String colorP, String colorS, String tipoDePrenda) {
        super(id, tipoDeTela, descripcion, colorP, colorS, tipoDePrenda);
    }

    public Integer getTipoSuperior() {
        if(TipoSuperior==null)
            return 0;

        return TipoSuperior;
    }

    public void setTipoSuperior(Integer tipoSuperior) {
        TipoSuperior = tipoSuperior;
    }

    public Superior prendaToSuperior(Prenda p){
        return (Superior)p;
    }



    @Override
    public Double getAbrigo(){
        return 8.0;
    }

}

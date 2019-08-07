package utn.frba.dds.que_me_pongo.Services;

import utn.frba.dds.que_me_pongo.Model.Evento;

public interface ClimaService {

    public  float getTemperatura(Evento evento) throws NullPointerException;
    
}

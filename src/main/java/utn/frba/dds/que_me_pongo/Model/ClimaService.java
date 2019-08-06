package utn.frba.dds.que_me_pongo.Model;

import java.util.concurrent.ExecutionException;

public interface ClimaService {

    public float getTemperatura(Evento evento) throws NullPointerException;
    
}

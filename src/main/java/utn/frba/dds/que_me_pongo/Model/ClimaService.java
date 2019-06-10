package utn.frba.dds.que_me_pongo.Model;

import java.util.concurrent.ExecutionException;

public interface ClimaService {

    public  Float getTemperatura(Evento evento) throws NullPointerException;
    
}

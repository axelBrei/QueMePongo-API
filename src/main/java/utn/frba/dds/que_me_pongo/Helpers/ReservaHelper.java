package utn.frba.dds.que_me_pongo.Helpers;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Model.Prenda;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import utn.frba.dds.que_me_pongo.Helpers.PrendasReservadas;
import utn.frba.dds.que_me_pongo.Utilities.Exceptions.NoSePuedoReservarException;

@NoArgsConstructor
public class ReservaHelper {


    public List<Prenda> getLibres(List<Prenda> prendas , List<PrendasReservadas> ocupadas){
        List<Prenda> PrendasLibres = new ArrayList<>();

        prendas.forEach(p ->{
            if(ocupadas.stream().noneMatch(l->l.getPrendas_id().equals(p.getId().longValue())))
                PrendasLibres.add(p);
        });

        return  PrendasLibres;

    }

    public  Boolean sePuedeReservarAtuendo(Atuendo a, Evento e, List<PrendasReservadas> reservadas) throws NoSePuedoReservarException{
        if (a.getPrendas().stream().anyMatch(p -> estaReservada(p, reservadas))) {
            return false;
        }
        return true;
    }

    private Boolean estaReservada(Prenda p ,List<PrendasReservadas> prendasReservadas){
        return prendasReservadas.stream().anyMatch(pr ->  pr.getPrendas_id().equals( p.getId().longValue() ));
    }






}



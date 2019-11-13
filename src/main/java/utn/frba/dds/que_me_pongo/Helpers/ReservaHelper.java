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

    public  Boolean sePuedeReservarAtuendo(Atuendo a, Evento e, List<PrendasReservadas> reservadas){
        System.out.println(reservadas.toString());
        if(!a.getPrendas().stream().noneMatch(p-> estaReservada(p,reservadas)))
            throw new ResponseStatusException(HttpStatus.OK,"No se puede reservar atuendo");

        return true;
    }

    private Boolean estaReservada(Prenda p ,List<PrendasReservadas> prendasReservadas){
        return prendasReservadas.stream().anyMatch(r ->  r.getPrendas_id().equals(p.getId().longValue()));
    }






}



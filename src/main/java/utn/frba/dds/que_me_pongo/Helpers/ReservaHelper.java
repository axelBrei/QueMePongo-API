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
    public  List<Prenda> prendasDisponibles(List<Prenda> prendas, Evento e, List<PrendasReservadas> reservadas){
        List<Prenda> filtradas =   prendas.stream().filter( p -> !estaReservada(p,e.getDesde(),e.getHasta(),reservadas) ).collect(Collectors.toList());
        return  filtradas;
    }

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
        if(!a.getPrendas().stream().noneMatch(p-> estaReservada(p,e.getDesde(),e.getHasta(),reservadas)))
            throw new ResponseStatusException(HttpStatus.OK,"No se puede reservar atuendo");

        return true;
    }

    private Boolean estaReservada(Prenda p ,Date desde, Date hasta, List<PrendasReservadas> prendasReservadas){
        List<PrendasReservadas> soloEstaPrenda = prendasReservadas.stream().filter(r -> new Long(p.getId()).equals(r.getPrendas_id())).collect(Collectors.toList());
        return  soloEstaPrenda.stream().anyMatch(reserva -> !sePuedeReserva(reserva,desde,hasta));
    }

    private Boolean sePuedeReserva(PrendasReservadas res,Date desde, Date hasta){

        if(!( (desde.getTime() < res.getDesde().getTime() && hasta.getTime() < res.getDesde().getTime() ) ||
                (desde.getTime() > res.getHasta().getTime() && hasta.getTime() > res.getHasta().getTime() ))){
        return false;
        }
        return true;
    }




}



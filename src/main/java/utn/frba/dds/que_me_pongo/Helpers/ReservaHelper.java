package utn.frba.dds.que_me_pongo.Helpers;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Model.Prenda;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class ReservaHelper {
    public  List<Prenda> prendasDisponibles(List<Prenda> prendas, Evento e, List<PrendasReservadas> reservadas){
        List<Prenda> filtradas =   prendas.stream().filter( p -> !estaReservada(p,e.getDesde(),e.getHasta(),reservadas) ).collect(Collectors.toList());
        return  filtradas;
    }

    public  Boolean sePuedeReservarAtuendo(Atuendo a, Evento e, List<PrendasReservadas> reservadas){
        if(e==null)
            throw new ResponseStatusException(HttpStatus.OK,"No se ecuentra el evento");

        if(a==null)
            throw new ResponseStatusException(HttpStatus.OK,"No se ecuentra el atuendo");

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



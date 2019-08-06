package utn.frba.dds.que_me_pongo.Helpers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Repository.Implementation.PrendaReservadaRespositoryImp;
import utn.frba.dds.que_me_pongo.Repository.PrendaReservadaRespository;
import utn.frba.dds.que_me_pongo.Repository.ReservasRepository;
import utn.frba.dds.que_me_pongo.Model.PrendasReservadas;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class ReservaHelper {
    public  Boolean sePuedeReservarAtuendo(Atuendo a, Evento e, List<PrendasReservadas> reservadas){
        System.out.println(reservadas.toString());
        if(!a.getPrendas().stream().noneMatch(p-> estaReservada(p,e.getDesde(),e.getHasta(),reservadas)))
            throw new ResponseStatusException(HttpStatus.OK,"No se puede reservar atuendo");

        return true;
    }

    private Boolean estaReservada(Prenda p ,Date desde, Date hasta, List<PrendasReservadas> prendasReservadas){
        List<PrendasReservadas> soloEstaPrenda = prendasReservadas.stream().filter(r -> !new Long(p.getId()).equals(r.getPrendas_id())).collect(Collectors.toList());
        return  soloEstaPrenda.stream().anyMatch(reserva -> !sePuedeReserva(reserva,desde,hasta));
    }

    private Boolean sePuedeReserva(PrendasReservadas res,Date desde, Date hasta){

        if(!( (desde.getTime() < res.getDesde().getTime() && hasta.getTime() < res.getDesde().getTime() ) ||
                (desde.getTime() > res.getHasta().getTime() && hasta.getTime() > res.getHasta().getTime() ))){
            throw  new ResponseStatusException(HttpStatus.OK,"La prenda "+res.getPrendas_id()+" ya fue reservada");
        }
        return true;
    }



}



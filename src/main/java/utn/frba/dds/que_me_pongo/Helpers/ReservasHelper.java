package utn.frba.dds.que_me_pongo.Helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Model.Reserva;
import utn.frba.dds.que_me_pongo.Repository.ReservasRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;



public class ReservasHelper {
    @Autowired
    ReservasRepository reservasRepository;
    Set<Reserva> reservas = new HashSet<>();

    public boolean estaReservada(Prenda p , Date desde , Date hasta){
        try {
            reservas = reservasRepository.findAllByPrendaId(p.getId());
            return  reservas.stream().anyMatch(reserva -> !reserva.noEstaReservada(desde,hasta));
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.OK,"No existe la prenda " + p.getId());
        }

    }

}

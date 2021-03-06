package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import utn.frba.dds.que_me_pongo.Helpers.PrendasReservadas;
import utn.frba.dds.que_me_pongo.Helpers.ReservaHelper;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Repository.AtuendoRepository;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.EventosRespository;
import utn.frba.dds.que_me_pongo.Repository.PrendaReservadaRespository;
import utn.frba.dds.que_me_pongo.Utilities.Exceptions.NoSePuedoReservarException;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Atuendo.ReservarAtuendoRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.DeleteReservaRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    EventosRespository eventosRespository;
    @Autowired
    AtuendoRepository atuendoRepository;
    @Autowired
    PrendaReservadaRespository prendaReservadaRespository;

    /*
        Para reservar un autendo debe existir el evneto selccionado, el cual se elgie en la app, y encaso de no existir se crea desde la app
     */
    @RequestMapping(value = "reservarAtuendo", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity reservarAtuendo(@RequestParam String uid,@RequestParam Long idEvento,@RequestParam Long idAtuendo) throws IOException {
        ReservaHelper reservaHelper = new ReservaHelper();
        Cliente cliente = clientesRepository.findClienteByUid(uid);
        Evento evento = cliente
                .getEventos()
                .stream()
                .filter(e -> e.getId().equals(idEvento))
                .findFirst()
                .orElseThrow(() -> new NoSePuedoReservarException(HttpStatus.NOT_FOUND));

        Atuendo atuendo = atuendoRepository.getAtuendoById(idAtuendo);
        List<PrendasReservadas> reservadas = prendaReservadaRespository.prendasOcupadas(evento.getDesde(),evento.getHasta(),evento.getId_guardarropa());
        if(reservaHelper.sePuedeReservarAtuendo(atuendo, evento, reservadas)){
            evento.setAtuendo(atuendo);
            cliente.getGuardarropa(evento.getId_guardarropa()).addAtuendo(atuendo);
            Set<Atuendo> borrar = new HashSet<>(evento.getGenerados());
            evento.getGenerados().removeIf(a->atuendo.getId()!=a.getId());
            eventosRespository.save(evento);
            borrar.removeIf(at->at.equals(atuendo));
            borrar.forEach(at -> atuendoRepository.delete(at));
            clientesRepository.save(cliente);

            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            evento.setNotificado(false);
            evento.setGenerados(new HashSet<>());
            eventosRespository.save(evento);
            HashMap<String, Object> resp = new HashMap<>();
            resp.put("message", "No se ha podido reservar el atuendo porque la prenda ya estaba reservada");
            return new ResponseEntity(resp, HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = "atuendosReservados", method = RequestMethod.GET)
    public ResponseEntity getAtuendosReservados(@RequestParam String uid){
        Cliente cliente = clientesRepository.findClienteByUid(uid);
        List<Evento> reservas = cliente.getEventos().stream().filter(r -> r.tieneReserva()).collect(Collectors.toList());
        return new ResponseEntity(reservas, HttpStatus.OK);
    }


    @RequestMapping(value = "deleteReserva", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAtuendoReservado(@RequestBody DeleteReservaRequest body){
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Evento evento = cliente
                .getEventos()
                .stream()
                .filter(e -> e.getId() == body.getIdEvento())
                .findFirst()
                .orElseThrow(() -> new NoSePuedoReservarException(HttpStatus.NOT_FOUND));

        evento.setAtuendo(null);
        clientesRepository.save(cliente);

        return new ResponseEntity("Reserva eliminada", HttpStatus.OK);
    }


}
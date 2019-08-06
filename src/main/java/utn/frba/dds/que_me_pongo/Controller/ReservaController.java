package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import utn.frba.dds.que_me_pongo.Exceptions.NoSePuedoReservarException;
import utn.frba.dds.que_me_pongo.Helpers.PrendasReservadas;
import utn.frba.dds.que_me_pongo.Helpers.ReservaHelper;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Model.Reserva;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.EventosRespository;
import utn.frba.dds.que_me_pongo.Repository.PrendaReservadaRespository;
import utn.frba.dds.que_me_pongo.Repository.ReservasRepository;
import utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo.GetAtuendoRecomendadoRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo.ReservarAtuendoRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.Prenda.DeletePrendaRequest;
import utn.frba.dds.que_me_pongo.WebServices.Responses.AtuendoReservadoResponse;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    EventosRespository eventosRespository;

    /*
        Para reservar un autendo debe existir el evneto selccionado, el cual se elgie en la app, y encaso de no existir se crea desde la app
     */
    @RequestMapping(value = "reservarAtuendo", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity reservarAtuendo(@RequestBody ReservarAtuendoRequest body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Evento evento = cliente
                .getEventos()
                .stream()
//                .filter(e -> e.getId() == body.)
                .findFirst()
                .orElseThrow(() -> new NoSePuedoReservarException(HttpStatus.NOT_FOUND));

        ReservaHelper reservaHelper = new ReservaHelper();

//        reservaHelper.sePuedeReservarAtuendo(body.getAtuendo(),body.getEvento(),pr);

        evento.setAtuendo(body.getAtuendo());
        cliente.getEventos().add(cliente.getEventos().indexOf(evento), evento);
        clientesRepository.save(cliente);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    @RequestMapping(value = "atuendosReservados", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity getAtuendosReservados(@RequestBody GetAtuendoRecomendadoRequest body){
//        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
//        Set<Reserva> reservas = cliente.getReservas();
//        return new ResponseEntity(reservas, HttpStatus.OK);
//    }

//    @RequestMapping(value = "deleteReservaAtuendo", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity deleteAtuendoReservado(@RequestBody AtuendoReservadoResponse body){
//        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
//        cliente.getReservas().removeIf(reserva->reserva.getId()==body.getReserva().getId());
//        clientesRepository.save(cliente);
//        reservasRepository.deleteById(body.getReserva().getId());
//
//        return new ResponseEntity("Reserva eliminada", HttpStatus.OK);
//    }
//

}
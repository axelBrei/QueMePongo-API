package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utn.frba.dds.que_me_pongo.Helpers.AtuendosRecomendationHelper;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Reserva;
import utn.frba.dds.que_me_pongo.Repository.AtuendoGuardarropaRepository;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.ReservaPrendaRepository;
import utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo.GetAtuendoRecomendadoRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo.ReservarAtuendoRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.Prenda.DeletePrendaRequest;
import utn.frba.dds.que_me_pongo.WebServices.Responses.AtuendoReservadoResponse;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/reserva")
public class ReservaController {
    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    ReservaPrendaRepository reservaPrendaRepository;

    @RequestMapping(value = "reservarAtuendo", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity reservarAtuendo(@RequestBody ReservarAtuendoRequest body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        reservaPrendaRepository.sePuedeReservar(body.getAtuendo(),body.getDesde(),body.getHasta());
        Atuendo atuendo = reservaPrendaRepository.addReservaAtuendoToCliente(cliente,body.getAtuendo(),body.getDesde(),body.getHasta());
        return new ResponseEntity<>(atuendo, HttpStatus.OK);
    }


    @RequestMapping(value = "atuendosReservados", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAtuendosReservados(@RequestBody GetAtuendoRecomendadoRequest body){
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Set<Reserva> reservas = cliente.getReservas();
        Set<AtuendoReservadoResponse> responseReservados = new Reserva().atuendosReservados(reservas);
        return new ResponseEntity(responseReservados.toArray(), HttpStatus.OK);
    }

    @RequestMapping(value = "deleteReservaAtuendo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAtuendoReservado(@RequestBody AtuendoReservadoResponse body){
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        reservaPrendaRepository.removeReservaDelCliente(cliente,body);

        return new ResponseEntity("Reserva eliminada", HttpStatus.OK);
    }

    @RequestMapping(value = "prendasReservadas" ,  method = RequestMethod.POST)
    public ResponseEntity prendasReservadas(@RequestBody DeletePrendaRequest body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Set<Reserva> reservas = cliente.getReservas();
        return new ResponseEntity<>(reservas.toArray(), HttpStatus.OK);
    }

}
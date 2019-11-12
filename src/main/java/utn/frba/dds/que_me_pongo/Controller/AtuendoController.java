package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Repository.*;
import utn.frba.dds.que_me_pongo.Utilities.Helpers.RecomendacionDeAtuendos.AtuendosRecomendationHelper;
import utn.frba.dds.que_me_pongo.Utilities.Helpers.DateHelper;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Atuendo.ReservarAtuendoRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo.CalificarAtuendoRequest;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping("/atuendo")
public class AtuendoController {

    @Autowired
    private AtuendosRecomendationHelper atuendosHelper;
    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    EventosRespository eventosRespository;
    @Autowired
    AtuendoRepository atuendoRepository;
    @Autowired
    PrendaReservadaRespository prendaReservadaRespository;
    @Autowired
    AtuendoGuardarropaRepository atuendoGuardarropaRepository;


    /*
    Cuando el usuario desdepues de recibir las recomendaciones selcciones una, primero la guarda (Que es esto)
    ACA ES CUANDO SE LE ASIGNA UN ID UNICO A UN ATUENDO
    Y despues se puede reservar y calificar(esta en otra tarjeta)
     */
    @RequestMapping(value = "guardar", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity guardarAtuendo(@RequestBody ReservarAtuendoRequest body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Guardarropa guardarropa = cliente.getGuardarropa(body.getIdGuardarropa());
        Atuendo atuendo = atuendoGuardarropaRepository.addAtuendoToGuardarropa(guardarropa, body.getAtuendo());
        clientesRepository.save(cliente);
        return new ResponseEntity<>(atuendo, HttpStatus.OK);
    }

    @RequestMapping(value = "getAtuendo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAtuendo(@RequestBody ReservarAtuendoRequest body) {
        AtuendosRecomendationHelper helper = new AtuendosRecomendationHelper();
        if(body.getEvento() == null){
            Evento evento = new Evento();
            evento.setDesde(DateHelper.sumarMinutosAFecha(new Date(), 5));
            evento.setHasta(DateHelper.sumarDiasAFecha(new Date(), 1));
            evento.setFormalidad("Informal");
            evento.setLatitud(12.123);
            evento.setLongitud(24.123);
             body.setEvento(evento);
        }
        Set<Atuendo> atuendoSet = helper.generarAtuendos(body.getUid(), body.getIdGuardarropa(), body.getEvento() , clientesRepository, prendaReservadaRespository);
        return new ResponseEntity(atuendoSet.toArray(), HttpStatus.OK);
    }

    @RequestMapping(value = "guardados", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity atuendosGuardados(@RequestBody ReservarAtuendoRequest body) {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Guardarropa g = cliente.getGuardarropa(body.getIdGuardarropa());
        return new ResponseEntity(g.getAtuendos(), HttpStatus.OK);
    }


    @RequestMapping(value = "calificar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity calificarAtuendo(@RequestBody CalificarAtuendoRequest body){
        Atuendo atuendo;
        try {
            atuendo = atuendoRepository.getAtuendoById(body.getAtuendo().getId());
            atuendo.setCalificacion(body.getCalificacion());
            atuendoRepository.save(atuendo);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.OK,"No se econtro el atuendo "+body.getAtuendo().getId());
        }

        return new ResponseEntity(atuendo, HttpStatus.OK);
    }

    @RequestMapping(value = "notificados",  method = RequestMethod.GET)
    public ResponseEntity atuendosNotificados(@RequestParam Long idEvento){
        Evento evento = eventosRespository.getOne(idEvento);
        return new ResponseEntity(evento.getGenerados().toArray(), HttpStatus.OK);
    }



}
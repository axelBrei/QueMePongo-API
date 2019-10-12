package utn.frba.dds.que_me_pongo.Controller.Eventos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.EventosClienteRepository;
import utn.frba.dds.que_me_pongo.Repository.EventosRespository;
import utn.frba.dds.que_me_pongo.Utilities.Exceptions.EventoNotFoundException;
import utn.frba.dds.que_me_pongo.Utilities.Helpers.DateHelper;
import utn.frba.dds.que_me_pongo.WebServices.Request.AgregarEventoRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.Evento.AbmEvento;
import utn.frba.dds.que_me_pongo.WebServices.Responses.Notificacion.FirebaseNotificationrResponse;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    EventosRespository eventosRespository;
    @Autowired
    EventosClienteRepository eventosClienteRepository;

    @Transactional
    @RequestMapping(value = "agregar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity agregarEvento(@RequestBody AgregarEventoRequest body){
        Cliente cliente = clientesRepository.findClienteByUid( body.getUid());
        Evento evento = body.getEvento();
        Evento nuevo  = eventosRespository.save(evento);
        cliente.getEventos().add(nuevo);
        clientesRepository.save(cliente);
        HashMap<String, Object> resp = new HashMap<>();
        resp.put("message", "Se ha añadido la evento con exito");
        resp.put("idEvento", nuevo.getId());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Modifying
    @RequestMapping(value = "eliminar")
    public ResponseEntity eliminarEvento(@RequestBody AbmEvento body){
        Cliente cliente = clientesRepository.findClienteByUid( body.getUid() );
        Evento evento = cliente
                .getEventos()
                .stream()
                .filter(e -> e.getId() == body.getIdEvento())
                .findFirst()
                .orElseThrow( () -> new EventoNotFoundException(HttpStatus.NOT_FOUND, ""));
        evento = EventControllerHelper.clonarEventorepetitivo(evento);
        cliente.getEventos().removeIf(e -> e.getId() == body.getIdEvento());
        clientesRepository.save(cliente);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "eliminarFrecuencia", method = RequestMethod.POST)
    public ResponseEntity eliminarEventoConFrecuencia(@RequestBody AbmEvento body) {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        cliente.getEventos()
                .removeIf(
                        e -> e.getId() == body.getIdEvento()
                );
        clientesRepository.save(cliente);
        eventosRespository.deleteById(body.getIdEvento());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "getEventos", method = RequestMethod.GET)
    public ResponseEntity getEventosDelUsuario(@RequestParam  String uid){
        Cliente cliente = clientesRepository.findClienteByUid(uid);
        return new ResponseEntity(cliente.getEventos(), HttpStatus.OK);
    }


    // REVISA LOS USUARIOS QUE TIENEN EVENTOS EN LOS PROXIMOS A UNA HORA

    @Scheduled(fixedDelay = 5*1000)
    public void corroborarEventosCercanos(){
        Date ahora = new Date();
        Date incial = new Date ( ahora.getTime() - (3*60*60*1000));
        Date dentroDeCincoM = new Date (ahora.getTime() + (5*60*1000)  - (3*60*60*1000));
        Set<Evento> eventos = eventosRespository.findAllByDesdeBetween(incial,dentroDeCincoM);

        eventos.forEach(evento ->{
                if(!evento.getNotificado() && evento.getAtuendo() == null){
                    Cliente cliente = eventosClienteRepository.clienteDelEvento(evento.getId());
                    //GENERAR LOS ATUENDOS Y ENVIAR
                    FirebaseNotificationrResponse response = EventControllerHelper.sendFirebaseNotification(cliente.getFirebaseToken(), evento.getId());
                    if(response.getSuccess() == 1){
                        // SALIO TDO BIEN
                        if(!evento.getFrecuencia().isEmpty()){
                            eventosRespository.deleteById(evento.getId());
                            evento = EventControllerHelper.clonarEventorepetitivo(evento);
                        }else {
                            evento.setNotificado(true);
                        }

                        eventosRespository.save(evento);
                    }
                }
            }
        );
    }

}
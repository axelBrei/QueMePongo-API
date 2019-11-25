package utn.frba.dds.que_me_pongo.Controller.Eventos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.EventosClienteRepository;
import utn.frba.dds.que_me_pongo.Repository.EventosRespository;
import utn.frba.dds.que_me_pongo.Repository.PrendaReservadaRespository;
import utn.frba.dds.que_me_pongo.Utilities.Exceptions.EventoNotFoundException;
import utn.frba.dds.que_me_pongo.Utilities.Helpers.RecomendacionDeAtuendos.AtuendosRecomendationHelper;
import utn.frba.dds.que_me_pongo.WebServices.Request.AgregarEventoRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.Evento.AbmEvento;
import utn.frba.dds.que_me_pongo.WebServices.Responses.Notificacion.FirebaseNotificationrResponse;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    EventosRespository eventosRespository;
    @Autowired
    EventosClienteRepository eventosClienteRepository;
    @Autowired
    PrendaReservadaRespository prendaReservadaRespository;
    AtuendosRecomendationHelper helper;

    @Transactional
    @RequestMapping(value = "agregar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity agregarEvento(@RequestBody AgregarEventoRequest body){
        Cliente cliente = clientesRepository.findClienteByUid( body.getUid());
        Evento evento = body.getEvento();
        cliente.getEventos().add(evento);
        int index = cliente.getEventos().size() - 1;
        cliente = clientesRepository.save(cliente);

        evento = cliente.getEventos().get(index);

        if(!evento.getFrecuencia().equals("Sin repetición")){
            cliente = EventControllerHelper.getEventosFuturos(clientesRepository, cliente, evento);
            cliente = clientesRepository.save(cliente);
        }

        HashMap<String, Object> resp = new HashMap<>();
        resp.put("idEvento", evento.getId());
        resp.put("message", "Se ha añadido con exito el evento");
        return new ResponseEntity( resp,HttpStatus.OK);
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
                        e -> Long.parseLong(e.getUidEvento()) == body.getIdEvento() && e.getDesde().after(body.getDesde())
                );
        clientesRepository.save(cliente);
//        eventosRespository.deleteById(body.getIdEvento());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "getEventos", method = RequestMethod.GET)
    public ResponseEntity getEventosDelUsuario(@RequestParam  String uid){
        Cliente cliente = clientesRepository.findClienteByUid(uid);
        return new ResponseEntity(cliente.getEventos(), HttpStatus.OK);
    }

    // BORRA LOS ATUENDOS GENERADOS Y SELECCIONADO DE LOS EVENTOS PASADOS
//    @Scheduled(fixedDelay = 5*1000)
//    public void borrarPrendasDeEventosPasados(){
//        Calendar calendar = Calendar.getInstance(new Locale("es_AR"));
//        Date now = calendar.getTime();
//        calendar.add(Calendar.DAY_OF_MONTH, -1);
//        Date oneDayBack = calendar.getTime();
//        Set<Evento> eventoList = eventosRespository.findAllByHastaBetween(oneDayBack, now);
//        if(!eventoList.isEmpty()){
//            eventoList = eventoList.stream()
//                    .filter( e -> e.getGenerados().size() > 0 || e.getAtuendo() != null)
//                    .map( evento -> {
//                        evento.setAtuendo(null);
//                        evento.setGenerados(new HashSet<>());
//                        return evento;
//                    })
//                    .collect(Collectors.toSet());
//            if(eventoList.size() > 0){
//                eventosRespository.saveAll(eventoList);
//            }
//        }
//    }


    // REVISA LOS USUARIOS QUE TIENEN EVENTOS EN LOS PROXIMOS A UNA HORA
    @Scheduled(fixedDelay = 5*1000)
    public void corroborarEventosCercanos(){
        Calendar now = Calendar.getInstance(new Locale("es_AR"));
        now.setTime(new Date());
        Calendar dentroDeCincoM = Calendar.getInstance(new Locale("es_AR"));
        dentroDeCincoM.setTime(new Date());
        dentroDeCincoM.add(Calendar.MINUTE, 5);
        Set<Evento> eventos = eventosRespository.findAllByDesdeBetween(now.getTime(),dentroDeCincoM.getTime());
        helper = new AtuendosRecomendationHelper();

        eventos.forEach(evento ->{
                if(evento.getGenerados().isEmpty() || evento.getGenerados()==null){
                    Cliente cliente = eventosClienteRepository.clienteDelEvento(evento.getId());

                    int eventIndex = cliente.getEventos().indexOf(evento);

                    if(evento.getNotificado() == null){
                        cliente = EventControllerHelper.borrarEventosAPartirDeUnaSemana(cliente);
                        cliente = EventControllerHelper.getEventosFuturos(clientesRepository, cliente, evento);
                        evento.setNotificado(false);
                    }
                    if(evento.getGenerados().isEmpty() || evento.getGenerados()==null) {
                        Set<Atuendo> atuendoSet = helper.generarAtuendos(cliente.getUid(), (int) evento.getId_guardarropa(), evento, clientesRepository, prendaReservadaRespository);
                        evento.setGenerados(atuendoSet);

                        if(!evento.getNotificado() && atuendoSet.size() == 0) {
                            sendEventoNoTieneGenerados(cliente, evento);
                            evento.setNotificado(true);
                        }
                        eventosRespository.saveAndFlush(evento);
                        clientesRepository.save(cliente);
                    }
                    if((!evento.getNotificado() && !evento.getGenerados().isEmpty())){
                        //GENERAR LOS ATUENDOS Y ENVIAR
                        FirebaseNotificationrResponse response = EventControllerHelper.sendFirebaseNotification(cliente.getFirebaseToken(), evento.getId(), "Ya estan listos tus atuendos!", null);
                        if(response.getSuccess() == 1){
                            // SALIO TDO BIEN
                            evento.setNotificado(true);
                        }
                        eventosRespository.save(evento);
                        cliente.getEventos().add(eventIndex, evento);
                        Cliente c = clientesRepository.save(cliente);
                    }
                }
            }
        );
    }

    private void sendEventoNoTieneGenerados(Cliente cliente, Evento evento){
        EventControllerHelper.sendFirebaseNotification(cliente.getFirebaseToken(), evento.getId(),
                "No hemos podido generar prendas para el evento: " + evento.getNombre(),
                null
                );
    }

}
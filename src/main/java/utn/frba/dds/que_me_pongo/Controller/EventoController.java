package utn.frba.dds.que_me_pongo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utn.frba.dds.que_me_pongo.Controller.ClimaAPIs.ClimaApiDOS;
import utn.frba.dds.que_me_pongo.Controller.ClimaAPIs.ClimaApiUNO;
import utn.frba.dds.que_me_pongo.Exceptions.GuardarropaPrendasException;
import utn.frba.dds.que_me_pongo.Helpers.AtuendosRecomendationHelper;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.ClimaService;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.EventosRespository;
import utn.frba.dds.que_me_pongo.WebServices.Request.AgregarEventoRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo.GetAtuendoRecomendadoParaEventoRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/evento")
public class EventoController {
    /* ESTE ES EL REQUEST BODY
    {
	"username":"Q7xKaH8qPiUW2tz7DF7eVqdQ7253",
	"idGuardarropa":0,
	"climaApi":0,
	"evento":{"nombre":"casamiento","fecha":"2019-06-10T15:10:00","hora":0,"ubicacion":{"latitud":-34.603,"longitud":-58.424,"radio":5.0},"sugeridos":[]}
    }
     */

    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    EventosRespository eventosRespository;

    @Transactional
    @RequestMapping(value = "agregar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity agregarEvento(@RequestBody AgregarEventoRequest body){
        // NECESITO EL EVENTO O LOS DATOS DEL EVENTO <- viene del req
        // EL CLIENTE <- viene el id en el req
        // AGREGAR LOS DATOS DEL EVENTO AL CLIENTE
        Cliente cliente = clientesRepository.findClienteByUid( body.getUid());
        Evento evento = body.getEvento();
        cliente.getEventos().add(evento);
        clientesRepository.save(cliente);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "eliminar")
    public ResponseEntity eliminarEvento(@RequestBody Map<String, Object> body){
        Cliente cliente = clientesRepository.findClienteByUid( (String) body.get("uid") );
        cliente.getEventos().removeIf( e -> e.getId() == (int) body.get("idEvento"));
        clientesRepository.save(cliente);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "getEventos", method = RequestMethod.GET)
    public ResponseEntity getEventosDelUsuario(@RequestParam  String uid){
        Cliente cliente = clientesRepository.findClienteByUid(uid);
        return new ResponseEntity(cliente.getEventos(), HttpStatus.OK);
    }

    @Scheduled(fixedDelay = 5*60*1000)
    public void corroborarEventosCercanos(){
        //CADA 5 mins revisar los eventos cercanos.
    }
}
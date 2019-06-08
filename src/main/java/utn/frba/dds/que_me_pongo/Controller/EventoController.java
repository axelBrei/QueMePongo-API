package utn.frba.dds.que_me_pongo.Controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frba.dds.que_me_pongo.Helpers.AtuendosRecomendationHelper;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer.ClienteContainer;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo.GetAtuendoRecomendadoParaEventoRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo.GetAtuendoRecomendadoRequest;
import utn.frba.dds.que_me_pongo.WebServices.Responses.GetCantidadGuardarropasResponse;
import utn.frba.dds.que_me_pongo.WebServices.Responses.ResponseObjects.CantidadGuardarropaResponseObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/evento")
public class EventoController {
    /* ESTE ES EL REQUEST BODY
    {
	"username":"Q7xKaH8qPiUW2tz7DF7eVqdQ7253",
	"idGuardarropa":0,
	"evento":{"nombre":"casamiento","fecha":"2019-06-08T15:10:00","hora":0,"ubicacion":{"latitud":-34.603,"longitud":-58.424,"radio":5.0},"sugeridos":[]}
    }
     */

    @Autowired
    private AtuendosRecomendationHelper atuendosHelper;
    @RequestMapping(value = "/atuendo", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Atuendo> getPrendas(@RequestBody GetAtuendoRecomendadoParaEventoRequest body) throws IOException {

        ClienteContainer clienteC = new ClienteJsonParser().getCliente(body.getUsername());
        Cliente cliente = clienteC.getCliente();
        Evento evento = body.getEvento();


        Atuendo atuendo = atuendosHelper.generarAtuendoRecomendadoParaEvento(
                cliente.getGuardarropa(body.getIdGuardarropa()).getPrendas(),
                // Evento ,para saber temp
                evento,
                // COndicion para filtrar prendas
                (prenda -> {return true;}),
                //Condicion para filtrar el accesorio
                (prenda -> {return true;})
        );

        return new ResponseEntity<>(atuendo, HttpStatus.OK);
    }
}
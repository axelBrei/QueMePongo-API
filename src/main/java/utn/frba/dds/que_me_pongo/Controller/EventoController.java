package utn.frba.dds.que_me_pongo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import utn.frba.dds.que_me_pongo.Controller.ClimaAPIs.ClimaApiDOS;
import utn.frba.dds.que_me_pongo.Controller.ClimaAPIs.ClimaApiUNO;
import utn.frba.dds.que_me_pongo.Utilities.Exceptions.GuardarropaPrendasException;
import utn.frba.dds.que_me_pongo.Utilities.Helpers.AtuendosRecomendationHelper;
import utn.frba.dds.que_me_pongo.Utilities.Helpers.ClienteJsonParser;

import utn.frba.dds.que_me_pongo.Model.*;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Atuendo.GetAtuendoRecomendadoParaEventoRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private AtuendosRecomendationHelper atuendosHelper;
    @RequestMapping(value = "/atuendo", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<List<Atuendo>> getAtuendos(@RequestBody GetAtuendoRecomendadoParaEventoRequest body) throws IOException {

        Cliente cliente = ClienteJsonParser.getCliente(body.getUsername());
        Evento evento = body.getEvento();


        int apiNumero = body.getClimaApi();
        ClimaService climaService;

        switch (apiNumero){
            case 0:
                climaService = new ClimaApiUNO();
            break;
            case 1:
                climaService = new ClimaApiDOS();
            break;
            default:
                climaService = new ClimaApiUNO();
            break;
        }

        //nuevo generar
        List<Atuendo> atuendos = new ArrayList<Atuendo>();
        try {
//            atuendos = cliente.getGuardarropa(body.getIdGuardarropa()).generarAtuendoParaEvento(evento,climaService);
            if(atuendos.isEmpty())
                throw new GuardarropaPrendasException(HttpStatus.NOT_FOUND);
        }catch (NullPointerException e){
            throw new GuardarropaPrendasException(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(atuendos, HttpStatus.OK);
    }
}
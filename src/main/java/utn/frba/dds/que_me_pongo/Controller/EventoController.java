package utn.frba.dds.que_me_pongo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo.GetAtuendoRecomendadoParaEventoRequest;

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

    @RequestMapping(value = "agregar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity agregarEvento(){
        return new ResponseEntity(HttpStatus.OK);
    }
}
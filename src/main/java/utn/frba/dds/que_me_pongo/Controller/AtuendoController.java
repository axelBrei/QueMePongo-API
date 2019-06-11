package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import utn.frba.dds.que_me_pongo.Exceptions.GuardarropaPrendasException;
import utn.frba.dds.que_me_pongo.Helpers.AtuendosRecomendationHelper;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.TipoCliente;
import utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo.GetAtuendoRecomendadoRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/atuendo")
public class AtuendoController {

    @Autowired
    private AtuendosRecomendationHelper atuendosHelper;

    @RequestMapping(value = "getRecomendadosDesdeGuardaropa", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Atuendo> getPrendas(@RequestBody GetAtuendoRecomendadoRequest body) throws IOException {
        Cliente cliente = ClienteJsonParser.getCliente(body.getUsername());



        Atuendo atuendo = cliente.getGuardarropa(body.getIdGuardarropa()).generarAtuendo();

        return new ResponseEntity<>(atuendo, HttpStatus.OK);
    }

    @RequestMapping(value = "getAllAtuendos", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<List<Atuendo>> getAllAtuendos(@RequestBody GetAtuendoRecomendadoRequest body) throws IOException {
        Cliente cliente = ClienteJsonParser.getCliente(body.getUsername());


        List<Atuendo> atuendos = new ArrayList<Atuendo>();
        try {
            atuendos = cliente.getGuardarropa(body.getIdGuardarropa()).generarAllAtuendos();
            if(atuendos.isEmpty())
                throw new GuardarropaPrendasException(HttpStatus.NOT_FOUND);
        }catch (NullPointerException e){
            throw new GuardarropaPrendasException(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(atuendos, HttpStatus.OK);
    }



}
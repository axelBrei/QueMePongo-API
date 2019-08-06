package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import utn.frba.dds.que_me_pongo.Utilities.Exceptions.GuardarropaPrendasException;
import utn.frba.dds.que_me_pongo.Utilities.Helpers.AtuendosRecomendationHelper;
import utn.frba.dds.que_me_pongo.Utilities.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Model.*;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Atuendo.GetAtuendoRecomendadoRequest;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Atuendo.ReservarAtuendoRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/atuendo")
public class AtuendoController {

    @Autowired
    private AtuendosRecomendationHelper atuendosHelper;
    @Autowired
    ClientesRepository clientesRepository;

    @RequestMapping(value = "getRecomendadosDesdeGuardaropa", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Atuendo> getPrendas(@RequestBody GetAtuendoRecomendadoRequest body) throws IOException {
        Cliente cliente = ClienteJsonParser.getCliente(body.getUsername());

        Atuendo atuendo = new Atuendo();
        AtuendosRecomendationHelper helper = new AtuendosRecomendationHelper();

        return new ResponseEntity<>(atuendo, HttpStatus.OK);
    }

    @RequestMapping(value = "getAllAtuendos", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<List<Atuendo>> getAllAtuendos(@RequestBody GetAtuendoRecomendadoRequest body) throws IOException {
        Cliente cliente = ClienteJsonParser.getCliente(body.getUsername());


        List<Atuendo> atuendos = new ArrayList<Atuendo>();
        try {
//            atuendos = cliente.getGuardarropa(body.getIdGuardarropa()).generarAllAtuendos();
            if(atuendos.isEmpty())
                throw new GuardarropaPrendasException(HttpStatus.NOT_FOUND);
        }catch (NullPointerException e){
            throw new GuardarropaPrendasException(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(atuendos, HttpStatus.OK);
    }

    @RequestMapping(value = "reservarAtuendo", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Atuendo> reservarAtuendo(@RequestBody ReservarAtuendoRequest body) throws IOException {
        Cliente cliente = ClienteJsonParser.getCliente(body.getUsername());
        Guardarropa guardarropa= cliente.getGuardarropa(body.getIdGuardarropa());
        //Atuendo a reservar
        AtuendoReservado reserva = body.getAtuendo();
        //guardarropa.reservarAtuendo(reserva.getAtuendo(),reserva.getDesde(),reserva.getHasta());

        return new ResponseEntity<>(reserva.getAtuendo(), HttpStatus.OK);
    }

    @RequestMapping(value = "getAtuendo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAtuendo(@RequestBody GetAtuendoRecomendadoRequest body){
        AtuendosRecomendationHelper helper = new AtuendosRecomendationHelper();
        Set<Atuendo> atuendoSet = helper.generarAtuendos(body.getUsername(),body.getIdGuardarropa(), clientesRepository);
        return new ResponseEntity(atuendoSet.toArray(), HttpStatus.OK);
    }

}
package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Repository.AtuendoGuardarropaRepository;

import utn.frba.dds.que_me_pongo.Utilities.Exceptions.GuardarropaPrendasException;
import utn.frba.dds.que_me_pongo.Utilities.Helpers.AtuendosRecomendationHelper;
import utn.frba.dds.que_me_pongo.Utilities.Helpers.ClienteJsonParser;
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
    @Autowired
    AtuendoGuardarropaRepository atuendoGuardarropaRepository;

    @RequestMapping(value = "getRecomendadosDesdeGuardaropa", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Atuendo> getPrendas(@RequestBody GetAtuendoRecomendadoRequest body) throws IOException {
        Cliente cliente = ClienteJsonParser.getCliente(body.getUid());

        Atuendo atuendo = new Atuendo();
        AtuendosRecomendationHelper helper = new AtuendosRecomendationHelper();

        return new ResponseEntity<>(atuendo, HttpStatus.OK);
    }

    @RequestMapping(value = "getAllAtuendos", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity getAllAtuendos(@RequestBody GetAtuendoRecomendadoRequest body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Guardarropa guardarropa = cliente.getGuardarropa(body.getIdGuardarropa());


        List<Atuendo> atuendos = new ArrayList<Atuendo>();
        try {
            //atuendos = cliente.getGuardarropa(body.getIdGuardarropa()).generarAllAtuendos();
            if (atuendos.isEmpty())
                throw new GuardarropaPrendasException(HttpStatus.NOT_FOUND);
        } catch (NullPointerException e) {
            throw new GuardarropaPrendasException(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(guardarropa.getAtuendos().toArray(), HttpStatus.OK);
    }

    @RequestMapping(value = "getAllAtuendosParaEvento", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity getAllAtuendosParaEvento(@RequestBody GetAtuendoRecomendadoRequest body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Guardarropa guardarropa = cliente.getGuardarropa(body.getIdGuardarropa());


        List<Atuendo> atuendos = new ArrayList<Atuendo>();
        try {
           // atuendos = cliente.getGuardarropa(body.getIdGuardarropa()).generarAllAtuendos();
            if (atuendos.isEmpty())
                throw new GuardarropaPrendasException(HttpStatus.NOT_FOUND);
        } catch (NullPointerException e) {
            throw new GuardarropaPrendasException(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(guardarropa.getAtuendos().toArray(), HttpStatus.OK);
    }

    /*
    Cuando el usuario desdepues de recibir las recomendaciones selcciones una, primero la guarda (Que es esto)
    ACA ES CUANDO SE LE ASIGNA UN ID UNICO A UN ATUENDO
    *Y despues se puede reservar y calificar(esta en otra tarjeta)
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
        Set<Atuendo> atuendoSet = helper.generarAtuendos(body.getUid(), body.getIdGuardarropa(), clientesRepository);
        return new ResponseEntity(atuendoSet.toArray(), HttpStatus.OK);
    }


    @RequestMapping(value = "guardados", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity atuendosGuardados(@RequestBody ReservarAtuendoRequest body) {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Guardarropa g = cliente.getGuardarropa(body.getIdGuardarropa());
        return new ResponseEntity(g.getAtuendos(), HttpStatus.OK);
    }


}
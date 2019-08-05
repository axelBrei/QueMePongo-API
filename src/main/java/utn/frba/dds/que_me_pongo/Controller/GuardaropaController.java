package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Repository.ClienteGuardarropaRepository;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.GuardarropasRepository;
import utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo.GetAtuendoRecomendadoParaEventoRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.Guardarropa.CompartirGuardaropaRequest;
import utn.frba.dds.que_me_pongo.WebServices.Responses.GetCantidadGuardarropasResponse;
import utn.frba.dds.que_me_pongo.WebServices.Responses.ResponseObjects.CantidadGuardarropaResponseObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/guardaropa")
public class GuardaropaController {

    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    GuardarropasRepository guardarropasRepository;
    @Autowired
    ClienteGuardarropaRepository clienteGuardarropaRepository;

    @RequestMapping(value = "/nuevo",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity nuevoGuardarropa(@RequestParam("userName") String userName,@RequestParam("descripcion") String desc) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(userName);
        Guardarropa guardarropa = new Guardarropa(desc);
        guardarropa = clienteGuardarropaRepository.addGuardarropaToCliente(cliente, guardarropa);
        return new ResponseEntity(guardarropa.getId(),HttpStatus.OK);
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity nuevoGuardarropa(@RequestParam("userName") String userName,@RequestParam("id") int id) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(userName);
        clienteGuardarropaRepository.removeGuardarropaDelCliente(cliente, id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping( value = "getCantidad", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getcantidadDeGuardarropas(@RequestBody HashMap<String,String> body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.get("uid"));
        List<CantidadGuardarropaResponseObject> responseList = cliente.getGuardarropas().stream().map(guardarropa ->
                new CantidadGuardarropaResponseObject(guardarropa.getId(), guardarropa.getDescripcion())
        ).collect(Collectors.toList());
        return new ResponseEntity<GetCantidadGuardarropasResponse>(new GetCantidadGuardarropasResponse(responseList), HttpStatus.OK);
    }

    @RequestMapping( value = "compartir", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity compartirGuardarropa(@RequestBody CompartirGuardaropaRequest body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Cliente aCliente = clientesRepository.findClienteByUid(body.getA_uid());
        Guardarropa guardarropa = cliente.getGuardarropa(body.getIdG());
        clienteGuardarropaRepository.compartirToCliente(aCliente,guardarropa);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping( value = "dejarDeCompartir", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity dejarDeCompartirGuardarropa(@RequestBody CompartirGuardaropaRequest body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Cliente aCliente = clientesRepository.findClienteByUid(body.getA_uid());
        Guardarropa guardarropa = cliente.getGuardarropa(body.getIdG());
        clienteGuardarropaRepository.dejarDeCompartirToCliente(aCliente,guardarropa);

        return new ResponseEntity(HttpStatus.OK);
    }
}

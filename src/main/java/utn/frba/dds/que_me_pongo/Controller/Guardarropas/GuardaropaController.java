package utn.frba.dds.que_me_pongo.Controller.Guardarropas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.GuardarropaCompartido;
import utn.frba.dds.que_me_pongo.Repository.ClienteGuardarropaRepository;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.GuardarropasRepository;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Guardarropa.CompartirGuardarropaRequest;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.Responses.GetCantidadGuardarropasResponse;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.Responses.ResponseObjects.CantidadGuardarropaResponseObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
        guardarropa.setUidDueno(cliente.getUid());
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

    @RequestMapping(value = "get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getGuardarropas(@RequestParam String uid, @RequestParam int idGuardarropa) {
        Cliente cliente = clientesRepository.findClienteByUid(uid);
        return new ResponseEntity(cliente.getGuardarropa(idGuardarropa), HttpStatus.OK);
    }

    @RequestMapping( value = "compartir", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity compartirGuardarropa(@RequestBody CompartirGuardarropaRequest body) {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        if(GuardarropasHelper.puedeCompartir(cliente, body.getIdGuardarropa())){
            Cliente clienteDestino = clientesRepository.findClienteByUid(body.getUidDestino());
            if(GuardarropasHelper.clientesDelMismoRangoSocial(cliente, clienteDestino)){
                Guardarropa guardarropa = cliente.getGuardarropa(body.getIdGuardarropa());
                clienteDestino.addGuardarropa(guardarropa);
                clientesRepository.save(clienteDestino);
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping( value = "dejarDeCompartir", method = RequestMethod.POST)
    public ResponseEntity dejarDeCompartirGuardarropa(@RequestBody CompartirGuardarropaRequest body){
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        if(GuardarropasHelper.puedeCompartir(cliente, body.getIdGuardarropa())){
            Cliente clienteDestino = clientesRepository.findClienteByUid(body.getUidDestino());
            Guardarropa guardarropa = cliente.getGuardarropa(body.getIdGuardarropa());
            clienteDestino.deleteGuardarropa(guardarropa.getId());
            clientesRepository.save(clienteDestino);
            return new ResponseEntity(HttpStatus.OK);
        }else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping( value = "getCompartidos", method = RequestMethod.GET)
    public ResponseEntity getGuardarropasCompartidos(@RequestParam String uid, @RequestParam(required = false) Integer idGuardarropa) {
        List<GuardarropaCompartido> guardarropasCompartidos = clienteGuardarropaRepository.getGuardarropsCompartidosDelCliente(uid, idGuardarropa);
        return new ResponseEntity(guardarropasCompartidos, HttpStatus.OK);
    }
}

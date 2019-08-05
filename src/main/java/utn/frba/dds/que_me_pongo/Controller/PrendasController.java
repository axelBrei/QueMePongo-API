package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Model.Reserva;
import utn.frba.dds.que_me_pongo.Repository.*;
import utn.frba.dds.que_me_pongo.WebServices.Request.Guardarropa.GetPrendasRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.Prenda.DeletePrendaRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.Prenda.NuevaPrendaRequest;
import utn.frba.dds.que_me_pongo.WebServices.Responses.GetPrendasResponse;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/prendas")
public class PrendasController {

    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    PrendasRepository prendasRepository;
    @Autowired
    PrendaGuardarroparepository prendaGuardarroparepository;
    @Autowired
    ReservaPrendaRepository reservaPrendaRepository;

    @RequestMapping(value = "getPrendas", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity getPrendasGuardarropas(@RequestBody GetPrendasRequest body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Set<Prenda> prendas = cliente.getGuardarropa(body.getIdGuardarropa()).getPrendas();

        GetPrendasResponse response = new GetPrendasResponse();
        response.setPrendas(prendas);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "addPrenda" ,  method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPrendaToGuardarropa(@RequestBody NuevaPrendaRequest request) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(request.getUid());
        Guardarropa guardarropa = cliente.getGuardarropa(Integer.parseInt(request.getIdGuardarropa()));
        Prenda p = prendaGuardarroparepository.addPrendaToGuardarropa(guardarropa, request.getPrenda());
        HashMap<String, Object> resp = new HashMap<>();
        resp.put("message", "Se ha añadido la prenda con exito");
        resp.put("idPrenda", p.getId());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = "deletePrenda" ,  method = RequestMethod.POST)
    public ResponseEntity deletePrenda(@RequestBody DeletePrendaRequest body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Guardarropa guardarropa = cliente.getGuardarropa(body.getIdGuardarropa());
        prendaGuardarroparepository.eleminiarPrendaDelGuardarropa(guardarropa, body.getIdPrenda());
        return new ResponseEntity<>("Prenda eliminada con exito", HttpStatus.OK);
    }



    @RequestMapping(value = "prendasReservadas" ,  method = RequestMethod.POST)
    public ResponseEntity prendasReservadas(@RequestBody DeletePrendaRequest body) throws IOException {

        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Set<Reserva> reservas = cliente.getReservas();


        return new ResponseEntity<>(reservas.toArray(), HttpStatus.OK);
    }


}

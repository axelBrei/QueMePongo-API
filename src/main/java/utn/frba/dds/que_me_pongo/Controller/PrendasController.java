package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.WebServices.Request.Guardarropa.GetPrendasRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.Prenda.NuevaPrendaRequest;
import utn.frba.dds.que_me_pongo.WebServices.Responses.GetPrendasResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/prendas")
public class PrendasController {


    @RequestMapping(value = "getPrendas", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity getPrendasGuardarropas(@RequestBody GetPrendasRequest body) throws IOException {
        Cliente cliente = ClienteJsonParser.getCliente(body.getUid());
        Set<Prenda> prendas = cliente.getGuardarropa(body.getIdGuardarropa()).getPrendas();

        GetPrendasResponse response = new GetPrendasResponse();
        response.setPrendas(prendas);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "addPrenda" ,  method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPrendaToGuardarropa(@RequestBody NuevaPrendaRequest request) throws IOException {
        Cliente cliente = ClienteJsonParser.getCliente(request.getUid());
        if(request.getPrenda().getId() == null){
            Random r = new Random();
            request.getPrenda().setId( r.nextInt( 1000000 - 1) + 1);
        }
        cliente.anadirPrendaAlGuardarropa(request.getPrenda(), Integer.parseInt(request.getIdGuardarropa()));
        ClienteJsonParser.modifyNew(cliente);

        HashMap<String, Object> resp = new HashMap<>();
        resp.put("message", "Se ha añadido la prenda con exito");
        resp.put("idPrenda", request.getPrenda().getId());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = "deletePrenda" ,  method = RequestMethod.POST)
    public ResponseEntity deletePrenda(@RequestBody NuevaPrendaRequest body) throws IOException {
        Cliente cliente = ClienteJsonParser.getCliente(body.getUid());
        cliente.deletePrendaDelGuardarropa(body.getPrenda(), Integer.parseInt(body.getIdGuardarropa()));

        ClienteJsonParser.modifyNew(cliente);

        return new ResponseEntity<>("Prenda eliminada con exito", HttpStatus.OK);
    }


}

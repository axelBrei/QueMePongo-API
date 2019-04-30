package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer.ClienteContainer;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonParser;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.WebServices.Request.GetPrendasRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.NuevaPrendaRequest;
import utn.frba.dds.que_me_pongo.WebServices.Responses.GetPrendasResponse;
import utn.frba.dds.que_me_pongo.WebServices.Responses.ResponseObjects.PrendaResponseObject;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prendas")
public class PrendasController {

    private List<Prenda> prendas = PrendasJsonParser.getJsonPrendasJson();

    @RequestMapping(value = "getPrendas", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity getPrendasGuardarropas(@RequestBody GetPrendasRequest body) throws IOException {
        Cliente cliente = ClienteJsonParser.getCliente(body.getUid()).getCliente();
        List<Prenda> prendas = cliente.getGuardarropa(body.getIdGuardarropa()).getPrendas();

        GetPrendasResponse response = new GetPrendasResponse();
        response.setPrendas(
                prendas
                    .stream()
                    .map( p -> new PrendaResponseObject(p, p.getClass().getName()))
                    .collect(Collectors.toList())
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "addPrenda" ,  method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPrendaToGuardarropa(@RequestBody NuevaPrendaRequest request) throws IOException {

        ClienteContainer clienteC = ClienteJsonParser.getCliente(request.getUid());
        Cliente cliente = clienteC.getCliente();

        cliente.anadirPrendaAlGuardarropa(request.getPrenda(), Integer.parseInt(request.getIdGuardarropa()));
        ClienteJsonParser.modifyNew(new ClienteContainer(cliente));

        return new ResponseEntity<>("Prenda añadida con exito", HttpStatus.OK);
    }

    @RequestMapping(value = "deletePrenda" ,  method = RequestMethod.POST)
    public ResponseEntity deletePrenda(@RequestBody NuevaPrendaRequest body) throws IOException {

        ClienteContainer clienteC = ClienteJsonParser.getCliente(body.getUid());
        Cliente cliente = clienteC.getCliente();
        cliente.deletePrendaDelGuardarropa(body.getPrenda().getId(), Integer.parseInt(body.getIdGuardarropa()));

        ClienteJsonParser.modifyNew(new ClienteContainer(cliente));

        return new ResponseEntity<>("Prenda eliminada con exito", HttpStatus.OK);
    }


}

package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer.ClienteContainer;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
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

    @RequestMapping(value = "/nuevo",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity nuevoGuardarropa(@RequestParam("userName") String userName,@RequestParam("desc") String desc) throws IOException {

        ClienteContainer clienteC = new ClienteJsonParser().getCliente(userName);
        Cliente cliente = clienteC.getCliente();

        Guardarropa guardarropa = new Guardarropa(desc);
        Random r = new Random();
        guardarropa.setId( r.nextInt( 1000000 - 1) + 1 );
        cliente.addGuardarropa(guardarropa);
        ClienteJsonParser.modifyNew(new ClienteContainer(cliente));

        return new ResponseEntity(guardarropa.getId(),HttpStatus.OK);
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity nuevoGuardarropa(@RequestParam("userName") String userName,@RequestParam("id") int id) throws IOException {

        ClienteContainer clienteC = new ClienteJsonParser().getCliente(userName);
        Cliente cliente = clienteC.getCliente();
        cliente.deleteGuardarropa(id);
        ClienteJsonParser.modifyNew(new ClienteContainer(cliente));

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping( value = "getCantidad", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getcantidadDeGuardarropas(@RequestBody HashMap<String,String> body) throws IOException {
        ClienteContainer container = ClienteJsonParser.getCliente(body.get("uid"));
        Cliente cliente = container.getCliente();
        List<CantidadGuardarropaResponseObject> responseList = cliente.getGuardarropas().stream().map(guardarropa ->
                new CantidadGuardarropaResponseObject(guardarropa.getId(), guardarropa.getDesc())
        ).collect(Collectors.toList());
        return new ResponseEntity<GetCantidadGuardarropasResponse>(new GetCantidadGuardarropasResponse(responseList), HttpStatus.OK);
    }
}

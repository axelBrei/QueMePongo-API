package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.WebServices.Request.Cliente.NuevoClienteRequestBody;

import java.io.IOException;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @RequestMapping(value = "/nuevo",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registrarCliente(@RequestBody NuevoClienteRequestBody body) throws IOException {
        Cliente c = new Cliente(body.getUid(), body.getMail(), body.getName());
        ClienteJsonParser.newJsonCliente(c);
        return new ResponseEntity(HttpStatus.OK);
    }


}

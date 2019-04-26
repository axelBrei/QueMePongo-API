package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer.ClienteContainer;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.Prenda;

import java.io.IOException;

@RestController
@RequestMapping("/guardaropa")
public class GuardaropaController {

    @RequestMapping(value = "/nuevo",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity nuevoGuardarropa(@RequestParam("userName") String userName,@RequestParam("desc") String desc) throws IOException {

        ClienteContainer clienteC = new ClienteJsonParser().getCliente(userName);
        Cliente cliente = clienteC.getCliente();

        Guardarropa guardarropa = new Guardarropa(desc);
        cliente.addGuardarropa(guardarropa);
        ClienteJsonParser.modifyNew(new ClienteContainer(cliente));

        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity nuevoGuardarropa(@RequestParam("userName") String userName,@RequestParam("id") int id) throws IOException {

        ClienteContainer clienteC = new ClienteJsonParser().getCliente(userName);
        Cliente cliente = clienteC.getCliente();
        cliente.deleteGuardarropa(id);
        ClienteJsonParser.modifyNew(new ClienteContainer(cliente));

        return new ResponseEntity(HttpStatus.OK);
    }
}

package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer.ClienteContainer;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Prenda;

import java.io.IOException;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    /*
        METODO PARA AL Q SE ACCEDE CON url/login/loginAs
        TIENE COMO BODY UNA PRENDA, LA CUAL SE ENCARGA SPRING DE TRANSFORMARLA A LA CLASE CORRESPONDIENTE
        consume un Json y responde un Json y es un HTTP POST
     */
    @RequestMapping(value = "/nuevo",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity loginAs(@RequestParam("userName") String userName,@RequestParam("name") String name) throws IOException {
        ClienteContainer cliente = new ClienteContainer(new Cliente(userName,name));
        ClienteJsonParser.newJsonCliente(cliente);
        return new ResponseEntity(HttpStatus.OK);
    }


}

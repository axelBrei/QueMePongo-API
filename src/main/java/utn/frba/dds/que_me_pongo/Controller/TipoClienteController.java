package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import utn.frba.dds.que_me_pongo.Exceptions.AtuendoIncompletoException;
import utn.frba.dds.que_me_pongo.Exceptions.ClienteException;
import utn.frba.dds.que_me_pongo.Helpers.AtuendosRecomendationHelper;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Model.TipoCliente;
import utn.frba.dds.que_me_pongo.WebServices.Request.Cliente.ClienteUidRequestBody;
import utn.frba.dds.que_me_pongo.WebServices.Request.Cliente.NuevoClienteRequestBody;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
public class TipoClienteController {
    @Autowired
    private AtuendosRecomendationHelper atuendosHelper;

    @RequestMapping(value = "/premium",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setPremium(@RequestBody ClienteUidRequestBody body) throws IOException {
        Cliente cliente = ClienteJsonParser.getCliente(body.getUid());

        if(new TipoCliente().esGratuito(cliente.getTipoCliente())) {
            cliente.setTipoCliente(new TipoCliente().setTipoClientePremium());
        }else {
            throw new ClienteException(HttpStatus.NOT_FOUND, cliente.getTipoCliente().getNombre());
        }

        ClienteJsonParser.newJsonCliente(cliente);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/gratuito",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setGratuito(@RequestBody ClienteUidRequestBody body) throws IOException {
        /* @RequestMapping(value = "getRecomendadosDesdeGuardaropa", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Atuendo> getPrendas(@RequestBody GetAtuendoRecomendadoRequest body) throws IOException {*/

        Cliente cliente = ClienteJsonParser.getCliente(body.getUid());


        if(new TipoCliente().esPremium(cliente.getTipoCliente())) {
            cliente.setTipoCliente(new TipoCliente().setTipoClienteGratuito());
            ClienteJsonParser.newJsonCliente(cliente);
        }else {
            throw new ClienteException(HttpStatus.NOT_FOUND, cliente.getTipoCliente().getNombre());
        }



        return new ResponseEntity(HttpStatus.OK);
    }


}
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
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.TipoCliente;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.TipoClienteRepository;
import utn.frba.dds.que_me_pongo.Utilities.Exceptions.ClienteException;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.TipoCliente;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Cliente.ClienteUidRequestBody;

import java.io.IOException;

@RestController
@RequestMapping("/cliente")
public class TipoClienteController {
    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    TipoClienteRepository tipoClienteRepository;

    @RequestMapping(value = "/premium",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setPremium(@RequestBody ClienteUidRequestBody body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());

        if(new TipoCliente().esGratuito(cliente.getTipoCliente())) {
            TipoCliente t = tipoClienteRepository.findByNombre("Premium");
            cliente.setTipoCliente(t);
            clientesRepository.save(cliente);
        }else {
            throw new ClienteException(HttpStatus.NOT_FOUND, cliente.getTipoCliente().getNombre());
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/gratuito",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setGratuito(@RequestBody ClienteUidRequestBody body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());

        if(new TipoCliente().esPremium(cliente.getTipoCliente())) {
            TipoCliente t = tipoClienteRepository.findByNombre("Gratuito");
            cliente.setTipoCliente(t);
            clientesRepository.save(cliente);
        }else {
            throw new ClienteException(HttpStatus.NOT_FOUND, cliente.getTipoCliente().getNombre());
        }

        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "/cargarTipos",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity agregarTiposDeCliente(){
        TipoCliente tipo = new TipoCliente();
        if(tipoClienteRepository.findByNombre("Gratuito") != null)
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Ya existe los tipos de cliente en la base de datos");

        tipoClienteRepository.save(tipo.setTipoClienteGratuito());
        tipoClienteRepository.save(tipo.setTipoClientePremium());
        return new ResponseEntity(HttpStatus.OK);
    }


}
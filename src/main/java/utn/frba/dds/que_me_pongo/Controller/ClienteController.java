package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.TipoCliente;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.TipoClienteRepository;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Cliente.NuevoClienteRequestBody;

import java.io.IOException;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClientesRepository clientesRepository;
    @Autowired
    TipoClienteRepository tipoClienteRepository;

    @RequestMapping(value = "/nuevo",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registrarCliente(@RequestBody NuevoClienteRequestBody body) throws IOException {
        Cliente c = new Cliente(body.getUid(), body.getMail(), body.getName());
        TipoCliente tipoCliente = tipoClienteRepository.findByNombre("Gratuito");
        c.setTipoCliente(tipoCliente);
        clientesRepository.save(c);

        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "/borrar",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity eliminarCliente(@RequestParam String id){
        clientesRepository.deleteByUid(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getall",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllClientes(){
        return new ResponseEntity(clientesRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "updateToken", method = RequestMethod.GET)
    public ResponseEntity actualizarToquenDelUsuario(@RequestParam String uid, @RequestParam String userToken){
        Cliente cliente = clientesRepository.findClienteByUid(uid);
        cliente.setFirebaseToken(userToken);
        clientesRepository.save(cliente);
        return new ResponseEntity(HttpStatus.OK);
    }


}

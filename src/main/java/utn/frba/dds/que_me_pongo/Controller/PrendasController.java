package utn.frba.dds.que_me_pongo.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer.ClienteContainer;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer.PrendasContainer;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonParser;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Responses.GetPrendasResponse;
import utn.frba.dds.que_me_pongo.Responses.ResponseObjects.PrendaResponseObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/prendas")
public class PrendasController {


    // TODO: crear metodo que devuela las prendas desde un JSOn
    @RequestMapping(value = "getPrendas", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity getPrendasGuardarropas(@RequestParam("userName") String userName,@RequestParam("idGuardarropa") int id) throws IOException {
        ClienteContainer clienteC = ClienteJsonParser.getCliente(userName);
        Cliente cliente = clienteC.getCliente();

        List<Prenda> prendas = cliente.getGuardarropa(id).getPrendas();

        GetPrendasResponse response = new GetPrendasResponse();
        for (Prenda p :prendas) {
            response.addPrenda(new PrendaResponseObject(p, p.getClass().getName()));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "sendPrenda" ,  method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetPrendasResponse> sendPrenda(@RequestBody String prenda) throws IOException {
        //System.out.println(prenda);
        /*
        List<Prenda> prendas = PrendasJsonParser.sendJsonPrenda(prenda);
        GetPrendasResponse response = new GetPrendasResponse();
        for (Prenda p :prendas) {
            response.addPrenda(new PrendaResponseObject(p, p.getClass().getName()));
        }

        ResponseEntity res = new ResponseEntity<>(response, HttpStatus.OK);

        Gson gson = new GsonBuilder().registerTypeAdapter(res.getBody().getClass(), new PrendasJsonParser()).create();
        FileWriter writeFile = new FileWriter("src/main/resources/data.json");
        writeFile.write(gson.toJson(res.getBody()));
        writeFile.close();
        */
        return null;
    }

    @RequestMapping(value = "addPrenda" ,  method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPrendaToGuardarropa(@RequestParam("userName") String userName,@RequestParam("idGuardarropa") int id,@RequestBody String prenda) throws IOException {

        ClienteContainer clienteC = ClienteJsonParser.getCliente(userName);
        Cliente cliente = clienteC.getCliente();

        Prenda prendaNueva = PrendasJsonParser.JsonPrendaObject(prenda);
        cliente.anadirPrendaAlGuardarropa(prendaNueva,id);
        ClienteJsonParser.modifyNew(new ClienteContainer(cliente));



        return new ResponseEntity<>("", HttpStatus.OK);
    }
    @RequestMapping(value = "deletePrenda" ,  method = RequestMethod.POST)
    public ResponseEntity deletePrenda(@RequestParam("userName") String userName,@RequestParam("idGuardarropa") int id,@RequestParam("idPrenda") int idPrenda) throws IOException {

        ClienteContainer clienteC = ClienteJsonParser.getCliente(userName);
        Cliente cliente = clienteC.getCliente();
        cliente.deletePrendaDelGuardarropa(idPrenda,id);

        ClienteJsonParser.modifyNew(new ClienteContainer(cliente));



        return new ResponseEntity<>("", HttpStatus.OK);
    }



}

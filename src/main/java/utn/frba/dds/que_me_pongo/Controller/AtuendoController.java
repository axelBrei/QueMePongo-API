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
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer.GuardarropasContainer;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer.PrendasContainer;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonParser;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Responses.GetPrendasResponse;
import utn.frba.dds.que_me_pongo.Responses.ResponseObjects.PrendaResponseObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/atuendo")
public class AtuendoController {


    // TODO: crear metodo que devuela las prendas desde un JSOn
    @RequestMapping(value = "getRecomendadosDesdeGuardaropa", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<String> getPrendas(@RequestParam("userName") String userName,@RequestParam("idGuardarropa") int  id) throws IOException {

        ClienteContainer clienteC = new ClienteJsonParser().getCliente(userName);
        Cliente cliente = clienteC.getCliente();

        List<Prenda> prendas  = cliente.getGuardarropa(id).getPrendas();

        List<Atuendo> atuendos = cliente.getGuardarropa(id).allAtuendos();

        return new ResponseEntity<>("", HttpStatus.OK);
    }




}
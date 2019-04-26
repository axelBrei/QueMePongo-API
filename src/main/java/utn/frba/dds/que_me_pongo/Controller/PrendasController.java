package utn.frba.dds.que_me_pongo.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer.PrendasContainer;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonParser;
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
    public ResponseEntity<GetPrendasResponse> getPrendas(){
        List<Prenda> prendas = PrendasJsonParser.getJsonPrendasJson();
        GetPrendasResponse response = new GetPrendasResponse();
        for (Prenda p :prendas) {
            response.addPrenda(new PrendaResponseObject(p, p.getClass().getName()));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "sendPrenda" ,  method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetPrendasResponse> sendPrenda(@RequestBody String prenda) throws IOException {
        //System.out.println(prenda);
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
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}

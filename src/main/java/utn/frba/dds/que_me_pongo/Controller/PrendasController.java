package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonParser;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Responses.GetPrendasResponse;
import utn.frba.dds.que_me_pongo.Responses.ResponseObjects.PrendaResponseObject;

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
}

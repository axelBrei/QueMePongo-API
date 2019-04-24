package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prendas")
public class PrendasController {


    // TODO: crear metodo que devuela las prendas desde un JSOn
    @RequestMapping("getPrendas")
    public ResponseEntity getPrendas(){
        return new ResponseEntity(HttpStatus.OK);
    }
}

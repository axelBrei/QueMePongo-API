package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utn.frba.dds.que_me_pongo.Model.Prenda;

@RestController
@RequestMapping("/login")
public class LoginController {

    /*
        METODO PARA AL Q SE ACCEDE CON url/login/loginAs
        TIENE COMO BODY UNA PRENDA, LA CUAL SE ENCARGA SPRING DE TRANSFORMARLA A LA CLASE CORRESPONDIENTE
        consume un Json y responde un Json y es un HTTP POST
     */
    @RequestMapping(value = "/loginAs",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity loginAs(@RequestBody Prenda p){
        return new ResponseEntity(HttpStatus.OK);
    }
}

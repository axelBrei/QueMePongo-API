package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frba.dds.que_me_pongo.Exceptions.AtuendoIncompletoException;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer.ClienteContainer;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Accesorios;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Calzado;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Inferior;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Superior;
import utn.frba.dds.que_me_pongo.WebServices.Responses.ResponseObjects.PrendaResponseObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/atuendo")
public class AtuendoController {
    private List<Class<? extends Prenda>> tiposPrendaObligatorio = Arrays.asList(Superior.class, Inferior.class, Calzado.class);


    // TODO: crear metodo que devuela las prendas desde un JSOn
    @RequestMapping(value = "getRecomendadosDesdeGuardaropa", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Atuendo> getPrendas(@RequestParam("userName") String userName,@RequestParam("idGuardarropa") int  id) throws IOException {

        ClienteContainer clienteC = new ClienteJsonParser().getCliente(userName);
        Cliente cliente = clienteC.getCliente();
        Atuendo atuendo = new Atuendo();

        List<Prenda> prendas  = cliente.getGuardarropa(id).getPrendas();
        tiposPrendaObligatorio.forEach( type -> {
            Optional<PrendaResponseObject> prendaOptional = prendas
                    .stream()
                    .filter(p -> p.getClass().equals(type))
                    .filter(prendaObligatoriaFilterCondition())
                    .map(convertirAPrenda())
                    .findFirst();
            atuendo.anadirPrenda(
                    prendaOptional.orElseThrow( () -> new AtuendoIncompletoException(HttpStatus.NOT_FOUND, type.getSimpleName()))
            );
        });

        Optional<PrendaResponseObject> prenda = prendas
                .stream()
                .filter( p -> p.getClass().equals(Accesorios.class))
                .filter(accesoriosFilterCondition())
                .map(convertirAPrenda())
                .findFirst();
        atuendo.anadirPrenda(prenda.orElse(null));

        return new ResponseEntity<>(atuendo, HttpStatus.OK);
    }

    private Predicate<? super Prenda> prendaObligatoriaFilterCondition(){
        return prenda -> true;
    }


    private Predicate<? super Prenda> accesoriosFilterCondition(){
        return prenda -> true;
    }

    private Function<? super Prenda, PrendaResponseObject> convertirAPrenda(){
        return prenda -> new PrendaResponseObject(prenda, prenda.getClass().getName());
    }

}
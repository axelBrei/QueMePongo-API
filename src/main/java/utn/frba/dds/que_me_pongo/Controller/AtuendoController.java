package utn.frba.dds.que_me_pongo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import utn.frba.dds.que_me_pongo.Exceptions.GuardarropaPrendasException;
import utn.frba.dds.que_me_pongo.Helpers.AtuendosRecomendationHelper;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Model.*;
import utn.frba.dds.que_me_pongo.Repository.AtuendoGuardarropaRepository;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.ReservaPrendaRepository;
import utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo.GetAtuendoRecomendadoRequest;
import utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo.ReservarAtuendoRequest;
import utn.frba.dds.que_me_pongo.WebServices.Responses.AtuendoReservadoResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/atuendo")
public class AtuendoController {

    @Autowired
    private AtuendosRecomendationHelper atuendosHelper;
    @Autowired
    ClientesRepository clientesRepository;

    @Autowired
    ReservaPrendaRepository reservaPrendaRepository;

    @Autowired
    AtuendoGuardarropaRepository atuendoGuardarropaRepository;

    @RequestMapping(value = "getRecomendadosDesdeGuardaropa", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Atuendo> getPrendas(@RequestBody GetAtuendoRecomendadoRequest body) throws IOException {
        Cliente cliente = ClienteJsonParser.getCliente(body.getUid());

        Atuendo atuendo = new Atuendo();
        AtuendosRecomendationHelper helper = new AtuendosRecomendationHelper();

        return new ResponseEntity<>(atuendo, HttpStatus.OK);
    }

    @RequestMapping(value = "getAllAtuendos", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<List<Atuendo>> getAllAtuendos(@RequestBody GetAtuendoRecomendadoRequest body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Guardarropa guardarropa = cliente.getGuardarropa(body.getIdGuardarropa());

        /*
        List<Atuendo> atuendos = new ArrayList<Atuendo>();
        try {
//            atuendos = cliente.getGuardarropa(body.getIdGuardarropa()).generarAllAtuendos();
            if(atuendos.isEmpty())
                throw new GuardarropaPrendasException(HttpStatus.NOT_FOUND);
        }catch (NullPointerException e){
            throw new GuardarropaPrendasException(HttpStatus.NOT_FOUND);
        }
        */


        // HARCODEO UN POCO PARA PROBAR
        List<Prenda> prendas =  new ArrayList<>(cliente.getGuardarropa(body.getIdGuardarropa()).getPrendas());
        List<Atuendo> atuendos = new ArrayList<>();
        int n = prendas.size()-1;
        for (int i = 0; i < 5; i++) {
            int numero1 = 2;
            int numero2 = 3;
            int numero3 = 4;
            List<Prenda> p = new ArrayList<>();
            p.add(prendas.get(numero1));
            p.add(prendas.get(numero2));
            p.add(prendas.get(numero3));
            atuendos.add(new Atuendo(p));
            atuendoGuardarropaRepository.addAtuendoToGuardarropa(guardarropa,new Atuendo(p));

        }


        return new ResponseEntity<>(atuendos, HttpStatus.OK);
    }

    @RequestMapping(value = "reservarAtuendo", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Atuendo> reservarAtuendo(@RequestBody ReservarAtuendoRequest body) throws IOException {
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Date date = new Date();
        date.setTime(65464);
        //cliente.reservarAtuendo(body.getAtuendo(),date,date);//body.getDesde(),body.getHasta());
        reservaPrendaRepository.addReservaAtuendoToCliente(cliente,body.getAtuendo(),date,date);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "getAtuendo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAtuendo(@RequestBody GetAtuendoRecomendadoRequest body){
        AtuendosRecomendationHelper helper = new AtuendosRecomendationHelper();
        Set<Atuendo> atuendoSet = helper.generarAtuendos(body.getUid(),body.getIdGuardarropa(), clientesRepository);
        return new ResponseEntity(atuendoSet.toArray(), HttpStatus.OK);
    }

    @RequestMapping(value = "reservados", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAtuendosReservados(@RequestBody GetAtuendoRecomendadoRequest body){
        Cliente cliente = clientesRepository.findClienteByUid(body.getUid());
        Set<Reserva> reservas = cliente.getReservas();

        Set<AtuendoReservadoResponse> at = new Reserva().atuendosReservados(reservas);


        return new ResponseEntity(at.toArray(), HttpStatus.OK);
    }

}
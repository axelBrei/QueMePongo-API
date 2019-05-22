package utn.frba.dds.que_me_pongo;

import com.google.gson.Gson;
import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import utn.frba.dds.que_me_pongo.Helpers.ClimaJsonDeserializer.ResponseWeather;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonParser;
import utn.frba.dds.que_me_pongo.Model.*;
import utn.frba.dds.que_me_pongo.Model.ClimaAPIs.ClimaApiUNO;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Accesorios;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Calzado;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Inferior;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Superior;

import java.util.Date;

@SpringBootApplication
public class QueMePongoApplication {


    public static void main(String[] args) {
        SpringApplication.run(QueMePongoApplication.class, args);

        Ubicacion ubicacion = new Ubicacion(-34.603, -58.424);
        Date date = new Date(2019,05,25);

        Evento evento = new Evento("casamiento",date,ubicacion);

        ClimaApiUNO uno = new ClimaApiUNO();
        System.out.println(Float.toString(uno.getTemperatura(evento)));




        // FUNCION Q PARSEA EL JSON DE PRENDAS Y LO RETORNA
        /*
        Cliente cliente = new Cliente("peter","nombre");
        cliente.addGuardarropa(new Guardarropa("minuevo"));
        List<Prenda> prendaList  = PrendasJsonParser.getJsonPrendasJson();
        cliente.anadirPrendaAlGuardarropa(prendaList.get(0),0);
*/

       // System.out.println(cliente.toString());
        Guardarropa guardarropa = new Guardarropa("des");
        guardarropa.allAtuendos();

    }






}

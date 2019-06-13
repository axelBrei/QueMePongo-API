package utn.frba.dds.que_me_pongo;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.security.smartcardio.SunPCSC;
import utn.frba.dds.que_me_pongo.Controller.ClimaAPIs.ClimaApiDOS;
import utn.frba.dds.que_me_pongo.Controller.ClimaAPIs.ClimaApiUNO;
import utn.frba.dds.que_me_pongo.Model.ClimaService;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Model.Ubicacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;





@SpringBootApplication
public class QueMePongoApplication {


    public static void main(String[] args) {
        SpringApplication.run(QueMePongoApplication.class, args);

        Ubicacion ubicacion = new Ubicacion(-34.603, -58.424);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        String fecha = "2019-06-10 15:10:00";

        Date d = new Date();
        try {
            //return format.parse(this.dt_txt);
            d = format.parse(fecha);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Factory factory = new Factory();
        ICombinatoricsVector initialvector = Factory.createVector(new String[] {"hola","chau","peter","nida"});
        Generator gen = Factory.createSimpleCombinationGenerator(initialvector,3);

        List<ICombinatoricsVector<String>> list = gen.generateAllObjects();

        list.forEach(c->System.out.println(c.getVector().toString()));


        Evento evento = new Evento("casamiento",d,ubicacion);
        /*
        ObjectMapper j = new ObjectMapper();
        String json = null;
        try {
            json = j.writeValueAsString(evento);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(json);
        */


        ClimaService uno = new ClimaApiUNO();
        try {
            System.out.println(Float.toString(uno.getTemperatura(evento)));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        ClimaService dos = new ClimaApiDOS();
        try {
            System.out.println(Float.toString(dos.getTemperatura(evento)));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }




        // FUNCION Q PARSEA EL JSON DE PRENDAS Y LO RETORNA
        /*
        Cliente cliente = new Cliente("peter","nombre");
        cliente.addGuardarropa(new Guardarropa("minuevo"));
        List<Prenda> prendaList  = PrendasJsonParser.getJsonPrendasJson();
        cliente.anadirPrendaAlGuardarropa(prendaList.get(0),0);
*/


    }






}

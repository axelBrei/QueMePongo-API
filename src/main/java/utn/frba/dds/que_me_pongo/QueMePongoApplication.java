package utn.frba.dds.que_me_pongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import utn.frba.dds.que_me_pongo.Model.*;
import utn.frba.dds.que_me_pongo.Controller.ClimaAPIs.ClimaApiDOS;
import utn.frba.dds.que_me_pongo.Controller.ClimaAPIs.ClimaApiUNO;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class QueMePongoApplication {


    public static void main(String[] args) {
        SpringApplication.run(QueMePongoApplication.class, args);

        Ubicacion ubicacion = new Ubicacion(-34.603, -58.424);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        String fecha = "2019-06-11 15:10:00";

        Date d = new Date();
        try {
            //return format.parse(this.dt_txt);
            d = format.parse(fecha);

        } catch (ParseException e) {
            e.printStackTrace();
        }

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

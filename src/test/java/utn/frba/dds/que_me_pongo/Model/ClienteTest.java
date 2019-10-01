package utn.frba.dds.que_me_pongo.Model;

import org.junit.Assert;
import org.junit.Test;
import utn.frba.dds.que_me_pongo.Controller.ClienteController;
import utn.frba.dds.que_me_pongo.Controller.ClimaAPIs.ClimaApiDOS;
import utn.frba.dds.que_me_pongo.Controller.ClimaAPIs.ClimaApiUNO;
import utn.frba.dds.que_me_pongo.Controller.TipoClienteController;
import utn.frba.dds.que_me_pongo.Helpers.ClimeHelper;
import utn.frba.dds.que_me_pongo.Repository.ClienteGuardarropaRepository;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.Implementation.ClienteGuardarropaRepositoryImpl;
import utn.frba.dds.que_me_pongo.Utilities.Exceptions.LimiteGuardarropaException;
import utn.frba.dds.que_me_pongo.Utilities.RecomendationGenerator.AtuendosFilter;
import utn.frba.dds.que_me_pongo.Utilities.RecomendationGenerator.PrendasCombiner;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.PronosticoClassOpenWeather.Sys;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Cliente.NuevoClienteRequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class ClienteTest {

    @Test
    public void addGuardarropa() {
        Cliente axel = new Cliente("jhIWEVhowqX6Nh2aD76TpB1hsPR2","axel@gmail.com","axel");
        Prenda prenda1 = new Prenda(1,"lana","remera","rojo","","Superior",8.0,"Informal",1);
        Prenda prenda2 = new Prenda(2,"jean","pantalon","azul","","Inferior",8.0,"Formal",1);
        Prenda prenda3 = new Prenda(3,"tela","zapatillas","rojo","negro","Calzado",8.0,"Informal",1);
        Prenda prenda4 = new Prenda(4,"tela","zapatillas","rojo","azul","Calzado",8.0,"Informal",1);
        Prenda prenda5 = new Prenda(5, "cuero","campera","negro","","Superior",8.0,"Informal",3);
        Prenda prenda6 = new Prenda(6,"","collar","plata","","Accesorio",0.0,"Formal",1);
        Prenda prenda7 = new Prenda(7,"algodon","remera","verde","negro","Superior",8.0,"Informal",1);
        Guardarropa guardarropa = new Guardarropa("guardarropa");
        guardarropa.addPrenda(prenda1);
        guardarropa.addPrenda(prenda2);
        guardarropa.addPrenda(prenda3);
        guardarropa.addPrenda(prenda4);
        guardarropa.addPrenda(prenda5);
        guardarropa.addPrenda(prenda6);
        guardarropa.addPrenda(prenda7);
        axel.addGuardarropa(guardarropa);
        Guardarropa guardarropa2 = new Guardarropa("guardarropa 2");
        Prenda prenda8 = new Prenda(6,"","collar","plata","","Accesorio",0.0,"Formal",1);
        Prenda prenda9 = new Prenda(7,"algodon","remera","verde","negro","Superior",8.0,"Formal",1);
        axel.addGuardarropa(guardarropa2);

        Assert.assertEquals(2,axel.getGuardarropas().size());
    }

    @Test
    public void getGuardarropa() {
        Cliente axel = new Cliente("jhIWEVhowqX6Nh2aD76TpB1hsPR2","axel@gmail.com","axel");
        Prenda prenda1 = new Prenda(1,"lana","remera","rojo","","Superior",8.0,"Informal",1);
        Prenda prenda2 = new Prenda(2,"jean","pantalon","azul","","Inferior",8.0,"Formal",1);
        Prenda prenda3 = new Prenda(3,"tela","zapatillas","rojo","negro","Calzado",8.0,"Informal",1);
        Prenda prenda4 = new Prenda(4,"tela","zapatillas","rojo","azul","Calzado",8.0,"Informal",1);
        Prenda prenda5 = new Prenda(5, "cuero","campera","negro","","Superior",8.0,"Informal",3);
        Prenda prenda6 = new Prenda(6,"","collar","plata","","Accesorio",0.0,"Formal",1);
        Prenda prenda7 = new Prenda(7,"algodon","remera","verde","negro","Superior",8.0,"Informal",1);
        Guardarropa guardarropa = new Guardarropa("guardarropa");
        guardarropa.setId(1);
        guardarropa.addPrenda(prenda1);
        guardarropa.addPrenda(prenda2);
        guardarropa.addPrenda(prenda3);
        guardarropa.addPrenda(prenda4);
        guardarropa.addPrenda(prenda5);
        guardarropa.addPrenda(prenda6);
        guardarropa.addPrenda(prenda7);
        axel.addGuardarropa(guardarropa);
        Guardarropa guardarropa2 = new Guardarropa("guardarropa 2");
        Prenda prenda8 = new Prenda(6,"","collar","plata","","Accesorio",0.0,"Formal",1);
        Prenda prenda9 = new Prenda(7,"algodon","remera","verde","negro","Superior",8.0,"Formal",1);
        axel.addGuardarropa(guardarropa2);
        Assert.assertEquals(guardarropa, axel.getGuardarropa(1));
    }

    @Test
    public void deleteGuardarropa() throws IOException {
        Cliente axel = new Cliente("jhIWEVhowqX6Nh2aD76TpB1hsPR2","axel@gmail.com","axel");
        axel.setTipoCliente(new TipoCliente().setTipoClienteGratuito());
       Prenda prenda1 = new Prenda(1,"lana","remera","rojo","","Superior",8.0,"Informal",1);
        Prenda prenda2 = new Prenda(2,"jean","pantalon","azul","","Inferior",8.0,"Formal",1);
        Prenda prenda3 = new Prenda(3,"tela","zapatillas","rojo","negro","Calzado",8.0,"Informal",1);
        Prenda prenda4 = new Prenda(4,"tela","zapatillas","rojo","azul","Calzado",8.0,"Informal",1);
        Prenda prenda5 = new Prenda(5, "cuero","campera","negro","","Superior",8.0,"Informal",3);
        Prenda prenda6 = new Prenda(6,"","collar","plata","","Accesorio",0.0,"Formal",1);
        Prenda prenda7 = new Prenda(7,"algodon","remera","verde","negro","Superior",8.0,"Informal",1);
        Guardarropa guardarropa = new Guardarropa("guardarropa");
        guardarropa.setId(1);
        guardarropa.addPrenda(prenda1);
        guardarropa.addPrenda(prenda2);
        guardarropa.addPrenda(prenda3);
        guardarropa.addPrenda(prenda4);
        guardarropa.addPrenda(prenda5);
        guardarropa.addPrenda(prenda6);
        guardarropa.addPrenda(prenda7);
        axel.addGuardarropa(guardarropa);
        axel.deleteGuardarropa(1);
       Assert.assertEquals(true, axel.getGuardarropas().isEmpty());

    }
    @Test(expected = LimiteGuardarropaException.class)
    public void testTipoClienteGratuitoNoPuedeAlmacenarMasDe10Prendas() throws LimiteGuardarropaException {
        Cliente axel = new Cliente("jhIWEVhowqX6Nh2aD76TpB1hsPR2","axel@gmail.com","axel");
        axel.setTipoCliente(new TipoCliente().setTipoClienteGratuito());
        Prenda prenda1 = new Prenda(1,"lana","Remera","rojo","","Superior",8.0,"Informal",1);
        Prenda prenda2 = new Prenda(2,"jean","Pantalon","azul","","Inferior",8.0,"Formal",1);
        Prenda prenda3 = new Prenda(3,"tela","Zapatillas","rojo","negro","Calzado",8.0,"Informal",1);
        Prenda prenda4 = new Prenda(4,"tela","Zapatillas","rojo","azul","Calzado",8.0,"Informal",1);
        Prenda prenda5 = new Prenda(5, "cuero","Campera","negro","","Superior",8.0,"Informal",3);
        Prenda prenda6 = new Prenda(6,"","collar","plata","","Accesorio",0.0,"Formal",1);
        Prenda prenda7 = new Prenda(7,"algodon","Remera","verde","negro","Superior",8.0,"Informal",1);
        Guardarropa guardarropa = new Guardarropa("guardarropa");
        guardarropa.setId(1);
        guardarropa.addPrenda(prenda1);
        guardarropa.addPrenda(prenda2);
        guardarropa.addPrenda(prenda3);
        guardarropa.addPrenda(prenda4);
        guardarropa.addPrenda(prenda5);
        guardarropa.addPrenda(prenda6);
        guardarropa.addPrenda(prenda7);
        axel.addGuardarropa(guardarropa);
        Prenda prenda8 = new Prenda(8,"","collar","plata","","Accesorio",0.0,"Formal",1);
        Prenda prenda9 = new Prenda(9,"algodon","Remera","verde","negro","Superior",8.0,"Formal",1);
       Prenda prenda10 = new Prenda(10,"lana","Buzo","azul","","Superior",8.0,"Informal",2);
       Prenda prenda11 = new Prenda(11, "tela","Pantalon","blanco","","Inferior",8.0,"Informal",1);
       axel.addPrendaToGuardarropa(guardarropa,prenda8);
       axel.addPrendaToGuardarropa(guardarropa,prenda9);
       axel.addPrendaToGuardarropa(guardarropa,prenda10);
       Assert.assertEquals(10,axel.getGuardarropa(1).getPrendas().size());
        axel.addPrendaToGuardarropa(guardarropa,prenda11);
        Assert.assertEquals(10,axel.getGuardarropa(1).getPrendas().size());
    }
    @Test
    public void generarAtuendoParaEvento(){
        Cliente axel = new Cliente("jhIWEVhowqX6Nh2aD76TpB1hsPR2","axel@gmail.com","axel");
        axel.setTipoCliente(new TipoCliente().setTipoClienteGratuito());
        Prenda prenda1 = new Prenda(1,"lana","Remera","rojo","","Superior",8.0,"Informal",1);
        Prenda prenda2 = new Prenda(2,"jean","Pantalon","azul","","Inferior",8.0,"Formal",1);
        Prenda prenda3 = new Prenda(3,"tela","Zapatillas","rojo","negro","Calzado",8.0,"Informal",1);
        Prenda prenda4 = new Prenda(4,"tela","Zapatillas","rojo","azul","Calzado",8.0,"Informal",1);
        Prenda prenda5 = new Prenda(5, "cuero","Campera","negro","","Superior",8.0,"Informal",3);
        Prenda prenda6 = new Prenda(6,"","collar","plata","","Accesorio",0.0,"Formal",1);
        Prenda prenda7 = new Prenda(7,"algodon","Remera","verde","negro","Superior",8.0,"Informal",1);
        Guardarropa guardarropa = new Guardarropa("guardarropa");
        guardarropa.setId(1);
        guardarropa.addPrenda(prenda1);
        guardarropa.addPrenda(prenda2);
        guardarropa.addPrenda(prenda3);
        guardarropa.addPrenda(prenda4);
        guardarropa.addPrenda(prenda5);
        guardarropa.addPrenda(prenda6);
        guardarropa.addPrenda(prenda7);
        axel.addGuardarropa(guardarropa);
        Evento evento = new Evento("0001","Cumpleaños",new Date(119,9,5,12,0,0),new Date(119,9,5,12,30,0),-34.598356,-58.419919,"Anual","Informal");
        System.out.println( axel.generarAtuendoParaEvento(evento,guardarropa) );


    }
    @Test
    public void testPrendaCombiner(){
        Prenda prenda1 = new Prenda(1,"lana","Remera","rojo","","Superior",70.0,"Informal",1);
        Prenda prenda2 = new Prenda(2,"jean","Pantalon","azul","","Inferior",70.0,"Formal",1);
        Prenda prenda3 = new Prenda(3,"tela","Zapatillas","rojo","negro","Calzado",70.0,"Informal",1);
        Prenda prenda4 = new Prenda(4,"tela","Pantalon","rojo","azul","Inferior",70.0,"Informal",1);
        Prenda prenda5 = new Prenda(5, "cuero","Campera","negro","","Superior",70.0,"Informal",3);
        Prenda prenda6 = new Prenda(6,"","Collar","plata","","Accesorio",0.0,"Formal",1);
        Prenda prenda7 = new Prenda(7,"algodon","Buzo","verde","negro","Superior",70.0,"Informal",2);

        List<Prenda> listaPrendas = new ArrayList<Prenda>();
        listaPrendas.add(prenda1);
        listaPrendas.add(prenda2);
        listaPrendas.add(prenda3);
        listaPrendas.add(prenda4);
        listaPrendas.add(prenda5);
        listaPrendas.add(prenda6);
        listaPrendas.add(prenda7);
        //TOdo: nos da una lista de las posibles combinaciones de atuendos sin ninguna reestriccion. AtuendoFilter se encargara de eliminarlas
       // System.out.println(PrendasCombiner.execute(listaPrendas).collect(Collectors.toList()).stream().map(atuendo -> atuendo.getPrendas().size() ).collect(Collectors.toList())      );
        List<Atuendo> resultado = PrendasCombiner.execute(listaPrendas).collect(Collectors.toList());
        for (int i=0; i<PrendasCombiner.execute(listaPrendas).collect(Collectors.toList()).size();i++ ){
            System.out.println(resultado.get(i));
        }
    }

    @Test
    public void testClimaApi(){
        Evento evento = new Evento("0001","Cumpleaños",new Date(119,9,5,12,0,0),new Date(119,9,5,12,30,0),-34.598356,-58.419919,"Anual","Informal");
        ClimaApiUNO api = new ClimaApiUNO();
        System.out.println( api.getTemperatura(evento) );
    }

    @Test
    public void testClimaApi2(){
        Evento evento = new Evento("0001","Cumpleaños",new Date(119,9,5,12,0,0),new Date(119,9,5,12,30,0),-34.598356,-58.419919,"Anual","Informal");
        ClimaApiDOS api = new ClimaApiDOS();
        System.out.println( api.getTemperatura(evento) );
    }

    @Test
    public void testPrendaFilter(){
        Prenda prenda1 = new Prenda(1,"lana","Remera","rojo","","Superior",70.0,"Informal",1);
        Prenda prenda2 = new Prenda(2,"jean","Pantalon","azul","","Inferior",70.0,"Formal",1);
        Prenda prenda3 = new Prenda(3,"tela","Zapatillas","rojo","negro","Calzado",70.0,"Informal",1);
        Prenda prenda4 = new Prenda(4,"tela","Pantalon","rojo","azul","Inferior",70.0,"Informal",1);
        Prenda prenda5 = new Prenda(5, "cuero","Campera","negro","","Superior",70.0,"Informal",3);
        Prenda prenda6 = new Prenda(6,"","Collar","plata","","Accesorio",0.0,"Formal",1);
        Prenda prenda7 = new Prenda(7,"algodon","Buzo","verde","negro","Superior",70.0,"Informal",2);
        List<Prenda> listaPrendas = new ArrayList<Prenda>();
        listaPrendas.add(prenda1);
        listaPrendas.add(prenda2);
        listaPrendas.add(prenda3);
        listaPrendas.add(prenda4);
        listaPrendas.add(prenda5);
        listaPrendas.add(prenda6);
        listaPrendas.add(prenda7);
        Stream<Atuendo> streamAtuendo = PrendasCombiner.execute(listaPrendas);
        Evento evento = new Evento("0001","Cumpleaños",new Date(119,9,5,12,0,0),new Date(119,9,5,12,30,0),-34.598356,-58.419919,"Anual","Informal");
        System.out.println(ClimeHelper.getClimaParaEvento(evento));
        System.out.println(AtuendosFilter.execute(streamAtuendo,evento));
    }


}
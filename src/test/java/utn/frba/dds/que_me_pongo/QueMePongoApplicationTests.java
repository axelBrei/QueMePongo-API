package utn.frba.dds.que_me_pongo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utn.frba.dds.que_me_pongo.Controller.ClimaAPIs.ClimaApiUNO;
import utn.frba.dds.que_me_pongo.Helpers.AtuendosRecomendationHelper;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import utn.frba.dds.que_me_pongo.Model.*;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class QueMePongoApplicationTests {



   @Test
   public void cargaClienteJason() throws IOException {
      ClienteJsonParser unCliente = new ClienteJsonParser()  ;
      Assert.assertEquals("axel",unCliente.getCliente("jhIWEVhowqX6Nh2aD76TpB1hsPR2").getName());



   }
   @Test
   public void tieneAlgunaPrenda() throws IOException  {
      ClienteJsonParser unCliente = new ClienteJsonParser()  ;
      Cliente axel = unCliente.getCliente("jhIWEVhowqX6Nh2aD76TpB1hsPR2");
      Assert.assertEquals(false,axel.getGuardarropa(0).getPrendas().isEmpty());
   }


   @Test
   public void cargaCliente(){
      Cliente axel = new Cliente("axel@gmail.com","axel");
      axel.setUid("jhIWEVhowqX6Nh2aD76TpB1hsPR2");
      Prenda prenda1 = new Prenda(1,"lana","remera","rojo","","Superior");
      Prenda prenda2 = new Prenda(2,"jean","pantalon","azul","","Inferior");
      Prenda prenda3 = new Prenda(3,"tela","zapatillas","rojo","negro","Calzado");
      Prenda prenda4 = new Prenda(4,"tela","zapatillas","rojo","azul","Calzado");
      Prenda prenda5 = new Prenda(5, "cuero","campera","negro","","Superior");
      Prenda prenda6 = new Prenda(6,"","collar","plata","","Accesorio");
      Prenda prenda7 = new Prenda(7,"algodon","remera","verde","negro","Superior");
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
      Prenda prenda8 = new Prenda(6,"","collar","plata","","Accesorio");
      Prenda prenda9 = new Prenda(7,"algodon","remera","verde","negro","Superior");


      Assert.assertEquals("axel",axel.getName());
   }
   @Test
   public void cantidadPrednas() throws IOException  {
      ClienteJsonParser unCliente = new ClienteJsonParser()  ;
      Cliente axel = unCliente.getCliente("jhIWEVhowqX6Nh2aD76TpB1hsPR2");
      Assert.assertEquals(6,axel.getGuardarropa(0).getPrendas().size());
   }

   @Test
   public void testAtuendo() throws IOException  {
      ClienteJsonParser unCliente = new ClienteJsonParser()  ;
      Cliente axel = unCliente.getCliente("jhIWEVhowqX6Nh2aD76TpB1hsPR2");
      Assert.assertEquals(false,axel.getGuardarropa(0).getPrendas().isEmpty());
   }

   @Test
   public void testGuardarropaEliminado() throws IOException  {
      Cliente axel = new Cliente("axel@gmail.com","axel");
      axel.setUid("jhIWEVhowqX6Nh2aD76TpB1hsPR2");
      Prenda prenda1 = new Prenda(1,"lana","remera","rojo","","Superior" );
      Prenda prenda2 = new Prenda(2,"jean","pantalon","azul","","Inferior");
      Prenda prenda3 = new Prenda(3,"tela","zapatillas","rojo","negro","Calzado");
      Prenda prenda4 = new Prenda(4,"tela","zapatillas","rojo","azul","Calzado");
      Prenda prenda5 = new Prenda(5, "cuero","campera","negro","","Superior");
      Prenda prenda6 = new Prenda(6,"","collar","plata","","Accesorio");
      Prenda prenda7 = new Prenda(7,"algodon","remera","verde","negro","Superior");
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
      Prenda prenda8 = new Prenda(6,"","collar","plata","","Accesorio");
      Prenda prenda9 = new Prenda(7,"algodon","remera","verde","negro","Superior");
      guardarropa2.setId(2);
      guardarropa2.addPrenda(prenda8);
      guardarropa2.addPrenda(prenda9);
      axel.deleteGuardarropa(1);
      axel.addGuardarropa(guardarropa2);
      Assert.assertEquals(guardarropa2,axel.getGuardarropa(2));
   }

   @Test
   public void AtuendoTest(){
      Cliente axel = new Cliente("axel@gmail.com","axel");
      axel.setUid("jhIWEVhowqX6Nh2aD76TpB1hsPR2");
      Prenda prenda1 = new Prenda(1,"lana","remera","rojo","","Superior");
      Prenda prenda2 = new Prenda(2,"jean","pantalon","azul","","Inferior");
      Prenda prenda3 = new Prenda(3,"tela","zapatillas","rojo","negro","Calzado");
      Prenda prenda4 = new Prenda(4,"tela","zapatillas","rojo","azul","Calzado");
      Prenda prenda5 = new Prenda(5, "cuero","campera","negro","","Superior");
      Prenda prenda6 = new Prenda(6,"","collar","plata","","Accesorio");
      Prenda prenda7 = new Prenda(7,"algodon","remera","verde","negro","Superior");
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
      Prenda prenda8 = new Prenda(6,"","collar","plata","","Accesorio");
      Prenda prenda9 = new Prenda(7,"algodon","remera","verde","negro","Superior");
      guardarropa2.setId(2);
      guardarropa2.addPrenda(prenda8);
      guardarropa2.addPrenda(prenda9);
      axel.addGuardarropa(guardarropa2);
      AtuendosRecomendationHelper helper = new AtuendosRecomendationHelper();
      List<Prenda> prendasCombinacion = new ArrayList<Prenda>();
      prendasCombinacion.add(prenda2);
      prendasCombinacion.add(prenda3);
      prendasCombinacion.add(prenda7);
      Atuendo atuendoPosible =  new Atuendo(0,null,prendasCombinacion);
      Assert.assertEquals( true,helper.generarAtuendos(guardarropa).contains(atuendoPosible)   );
   }
   @Test
   public void testCamperaDeCueroError(){
      Cliente cliente = new Cliente("cliente@hotmail.com","cliente");
      cliente.setUid("ElCLiente");
      Guardarropa guardarropa = new Guardarropa("MiGuardarropa");
      guardarropa.setId(0);
      cliente.addGuardarropa(guardarropa);
      Prenda remeraCuero = new Prenda(0,"Cuero","Remera","Negro","Blanco","Superior");
      cliente.anadirPrendaAlGuardarropa(remeraCuero,0);
      Assert.assertEquals(false,guardarropa.getPrendas().contains(remeraCuero));
   }

   @Test
   public void testAtuendoParaEvento(){

      Cliente axel = new Cliente("axel@gmail.com","axel");
      axel.setUid("jhIWEVhowqX6Nh2aD76TpB1hsPR2");
      Prenda prenda1 = new Prenda(1,"Lana","Remera","rojo","","Superior");
      Prenda prenda2 = new Prenda(2,"Jean","Pantalon","azul","","Inferior");
      Prenda prenda3 = new Prenda(3,"Tela","Zapatillas","rojo","negro","Calzado");
      Prenda prenda4 = new Prenda(4,"Tela","Zapatillas","rojo","azul","Calzado");
      Prenda prenda5 = new Prenda(5, "Cuero","Campera","negro","","Superior");
      Prenda prenda6 = new Prenda(6,"","Collar","plata","","Accesorio");
      Prenda prenda7 = new Prenda(7,"Algodon","Remera","verde","negro","Superior");
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
      Prenda prenda8 = new Prenda(6,"","collar","plata","","Accesorio");
      Prenda prenda9 = new Prenda(7,"algodon","remera","verde","negro","Superior");
      guardarropa2.setId(2);
      guardarropa2.addPrenda(prenda8);
      guardarropa2.addPrenda(prenda9);
      axel.addGuardarropa(guardarropa2);
      AtuendosRecomendationHelper helper = new AtuendosRecomendationHelper();
      Ubicacion utn = new Ubicacion(-34.598356,-58.419919);
      Date fechaCumpleanios = new Date(119,7,7);
      Evento evento= new Evento("Cumpleaños",fechaCumpleanios,utn );
      ClimaService climaService = new  ClimaApiUNO();
      List<Prenda> prendasCombinacion = new ArrayList<Prenda>();
      prendasCombinacion.add(prenda5);
      prendasCombinacion.add(prenda6);
      prendasCombinacion.add(prenda1);
      prendasCombinacion.add(prenda4);
      prendasCombinacion.add(prenda2);
      Atuendo atuendoPosible =  new Atuendo(0,null,prendasCombinacion);
      Assert.assertEquals(true, helper.generarAtuendosParaEvento(guardarropa,evento,climaService).contains(atuendoPosible) );
   }
   



}

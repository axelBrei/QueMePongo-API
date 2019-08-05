package utn.frba.dds.que_me_pongo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import utn.frba.dds.que_me_pongo.Helpers.ClienteJsonParser;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.Prenda;

import java.io.IOException;

public class QueMePongoApplicationTests {

    @Test
    public void cargaClienteJason() throws IOException  {
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
       Prenda prenda1 = new Prenda(1,"lana","remera","rojo","","Superior" );
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
      Assert.assertEquals(0,axel.getGuardarropa(0).generarAtuendo());
   }




}

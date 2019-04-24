package utn.frba.dds.que_me_pongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonParser;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Accesorios;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Calzado;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.PrendaInferior;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.PrendaSuperior;

import java.util.List;

@SpringBootApplication
public class QueMePongoApplication {



    public static void main(String[] args) {
        SpringApplication.run(QueMePongoApplication.class, args);

        // FUNCION Q PARSEA EL JSON DE PRENDAS Y LO RETORNA
        PrendasJsonParser.getJsonPrendasJson();

        PrendaSuperior remera = new PrendaSuperior();
        remera.setId(1);
        remera.setTipoDeTela("tela");
        remera.setColorP("roja");
        remera.setColorS("azul");
        remera.setDescripcion("remera de mangas largas");

        PrendaInferior pantalon = new PrendaInferior();
        pantalon.setId(2);
        pantalon.setTipoDeTela("jean");
        pantalon.setColorP("azul");
        pantalon.setColorS("");
        pantalon.setDescripcion("pantalon de jean");

        PrendaSuperior remera2 = new PrendaSuperior();
        remera2.setId(3);
        remera2.setTipoDeTela("tela");
        remera2.setColorP("verde");
        remera2.setColorS("azul");
        remera2.setDescripcion("remera deportiva");

        PrendaInferior pantalon2 = new PrendaInferior();
        pantalon2.setId(4);
        pantalon2.setTipoDeTela("algodon");
        pantalon2.setColorP("negro");
        pantalon2.setColorS("");
        pantalon2.setDescripcion("pantalon rojo");


        Calzado calzado = new Calzado();
        calzado.setId(5);
        pantalon2.setTipoDeTela("tela");
        pantalon2.setColorP("rojo");
        pantalon2.setColorS("blanco");
        pantalon2.setDescripcion("zapatillas converse");

        Accesorios accesorio = new Accesorios();
        accesorio.setId(6);
        accesorio.setTipoDeTela("");
        accesorio.setColorP("rojo");
        accesorio.setColorS("blanco");
        accesorio.setDescripcion("gorra deportiva");

        Prenda p;


        Cliente cliente = new Cliente();
/*        cliente.setPrendasMaestras(pantalon);
        cliente.setPrendasMaestras(remera);
        cliente.setPrendasMaestras(pantalon2);
        cliente.setPrendasMaestras(remera2);
        cliente.setPrendasMaestras(accesorio);
        cliente.setPrendasMaestras(calzado);*/

        Guardarropa guardarropa = new Guardarropa();
        guardarropa.setDescripcion("guardarropas de los lunes");
        guardarropa.aniadirPrenda(pantalon);
        guardarropa.aniadirPrenda(remera);
        guardarropa.aniadirPrenda(accesorio);
        guardarropa.aniadirPrenda(calzado);

        cliente.addGuardarropa(guardarropa);
    }






}

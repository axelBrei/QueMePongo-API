package utn.frba.dds.que_me_pongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonParser;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Accesorios;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Calzado;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Inferior;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Superior;

@SpringBootApplication
public class QueMePongoApplication {



    public static void main(String[] args) {
        SpringApplication.run(QueMePongoApplication.class, args);

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

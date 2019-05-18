package utn.frba.dds.que_me_pongo.Helpers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import utn.frba.dds.que_me_pongo.Exceptions.AtuendoIncompletoException;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Accesorios;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Calzado;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Inferior;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Superior;
import utn.frba.dds.que_me_pongo.WebServices.Responses.ResponseObjects.PrendaResponseObject;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class AtuendosRecomendationHelper {
    private List<Class<? extends Prenda>> tiposPrendaObligatorio = Arrays.asList(Superior.class, Inferior.class, Calzado.class);


    public Atuendo generarAtuendoRecomendado(List<Prenda> prendas) throws AtuendoIncompletoException{
        Atuendo atuendo = new Atuendo();


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
        try {
            atuendo.anadirPrenda(prenda.orElseThrow( () -> new Exception("Error")));
        }catch (Exception e){
            // no me importa el handleo de la excepcion porque lo estoy tirando a proposito para que no se agrege a la lista como null
        }
        return atuendo;
    }


    private Predicate<? super Prenda> prendaObligatoriaFilterCondition(){
        return this::condicionFiltroPrendasObligatorias;
    }

    private Predicate<? super Prenda> accesoriosFilterCondition(){
        return this::condicionFiltroAccesorio;
    }

    private Function<? super Prenda, PrendaResponseObject> convertirAPrenda(){
        return prenda -> new PrendaResponseObject(prenda, prenda.getClass().getName());
    }

    /**
       Funcion que establece la condicion por la cual se agrega o no se agrega una prenda al atuendo
        @param prenda la prenda que se esta verificando si se agrega o no al atuendo
        @return un valor booleano que define si se agrega o no
     */
    private boolean condicionFiltroPrendasObligatorias(Prenda prenda){
        //TODO: condicion para agregar o no una prenda al atuendo;
        return true;
    }

    /**
     Funcion que establece la condicion por la cual se agrega o no un accesorio al atuendo
         @param accesorio el accesorio que se esta verificando si se agrega o no al atuendo
         @return un valor booleano que define si se agrega o no
     */
    private boolean condicionFiltroAccesorio(Prenda accesorio){
        // TODO: condicion para agregar o no un accesorio al autendo
        return true;
    }
}

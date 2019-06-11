package utn.frba.dds.que_me_pongo.Helpers;

import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import utn.frba.dds.que_me_pongo.Exceptions.AtuendoIncompletoException;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.ClimaService;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Accesorios;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Calzado;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Inferior;
import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Superior;
import utn.frba.dds.que_me_pongo.WebServices.Responses.ResponseObjects.PrendaResponseObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AtuendosRecomendationHelper {
    private List<Class<? extends Prenda>> tiposPrendaObligatorio = Arrays.asList(Superior.class, Inferior.class, Calzado.class);


    public Atuendo generarAtuendoRecomendado(List<Prenda> prendas, Predicate<? super Prenda> condicionPrendas, Predicate<? super  Prenda> condicionAccesorio) throws AtuendoIncompletoException{
        Atuendo atuendo = new Atuendo();


        tiposPrendaObligatorio.forEach( type -> {
            Optional<Prenda> prendaOptional = prendas
                    .stream()
                    .filter(p -> p.getClass().equals(type))
                    .filter(condicionPrendas)
                    .findFirst();
            atuendo.anadirPrenda(
                    prendaOptional.orElseThrow( () -> new AtuendoIncompletoException(HttpStatus.NOT_FOUND, type.getSimpleName()))
            );
        });

        Optional<Prenda> prenda = prendas
                .stream()
                .filter( p -> p.getClass().equals(Accesorios.class))
                .filter(condicionAccesorio)
                .findFirst();
        try {
            atuendo.anadirPrenda(prenda.orElseThrow( () -> new Exception("Error")));
        }catch (Exception e){
            // no me importa el handleo de la excepcion porque lo estoy tirando a proposito para que no se agrege a la lista como null
        }
        return atuendo;
    }

    public List<Atuendo> generarAllAtuendoRecomendadoParaEvento(List<Prenda> prendas, Evento evento, ClimaService climaService, Predicate<? super Prenda> condicionPrendas, Predicate<? super  Prenda> condicionAccesorio) throws AtuendoIncompletoException{

        Float temperatura = climaService.getTemperatura(evento);

        List<Atuendo> todos = generarAllAtuendos(prendas,condicionPrendas,condicionAccesorio);

        List<Optional<Atuendo>>  filtrados = todos.stream()
                .filter(p -> p.esSuficienteAbrigado(temperatura))
                .filter(p -> p.esCorrecto())
                .map(Optional::ofNullable)
                .collect(Collectors.toList());


        return optionalToAtuendosList(filtrados);
    }

    public  List<Atuendo> optionalToAtuendosList(List<Optional<Atuendo>> optional){
        List<Atuendo> retornar = new ArrayList<Atuendo>();
        optional.forEach(a->{
            retornar.add(a.get());
        });
        return retornar;
    }

    public List<Atuendo> generarAllAtuendos(List<Prenda> prendas, Predicate<? super Prenda> condicionPrendas, Predicate<? super  Prenda> condicionAccesorio) throws AtuendoIncompletoException{
        List<List<Optional<Prenda>>> obligatorios = new  ArrayList<List<Optional<Prenda>>>();
        List<List<Optional<Prenda>>> opcionales = new  ArrayList<List<Optional<Prenda>>>();



        tiposPrendaObligatorio.forEach( type -> {
            if(type != Superior.class) {
            List<Optional<Prenda>> prendaList = prendas.stream()
                    .filter(p -> p.getClass().equals(type))
                    .filter(condicionPrendas)
                    .map(Optional::ofNullable)
                    .collect(Collectors.toList());
            //System.out.println(prendaList.toString());
            obligatorios.add(prendaList);
            }else {
                List<Optional<Prenda>> superioresObligatorias = prendas.stream()
                .filter(p -> p.getClass().equals(Superior.class))
                        .filter(condicionPrendas)
                //.map(p -> (Superior)p)
                        .filter(prendaSuperiorObligatoria())
                .map(Optional::ofNullable)
                .collect(Collectors.toList());



                obligatorios.add(superioresObligatorias);

                List<Optional<Prenda>> superioresNoObligatorias = prendas.stream()
                .filter(p -> p.getClass().equals(Superior.class))
                .filter(condicionPrendas)
                //.map(p -> (Superior)p)
                .filter(prendaSuperiorObligatoria().negate())
                .map(Optional::ofNullable)
                .collect(Collectors.toList());



               // opcionales.addAll(dividirPorTipoSuperior(superioresNoObligatorias));
            }
        });


        opcionales.add(
                prendas
                .stream()
                .filter( p -> p.getClass().equals(Accesorios.class))
                .filter(condicionAccesorio)

                .map(Optional::ofNullable)
                .collect(Collectors.toList())
        );




        List<Atuendo> completa = getAllConbinations(obligatorios,opcionales);


        return completa;
    }
    /*
    private List<List<Optional<Superior>> dividirPorTipoSuperior(List<Optional<Superior>> superiores){
        List<List<Optional<Superior>>> divididos = new  ArrayList<List<Optional<Superior>>>();

        for (int i = 0; i < 4; i++) {
            int finalI = i;
            List<Optional<Superior>> nuevo =
                            superiores.stream()

                            .filter(condicionPrendaSuperiorTipo(.get(),finalI))
                            .map(Optional::ofNullable)
                            .collect(Collectors.toList());


        }


        return divididos;
    }
    */
    private List<Atuendo> getAllConbinations(List<List<Optional<Prenda>>> obligatorios,List<List<Optional<Prenda>>> opcionales){
        Integer cantObligatorios = obligatorios.size();
        Integer cantOpcionales = opcionales.size();
        List<Atuendo> master = new  ArrayList<Atuendo>();

        try {
            List<Atuendo> atuendosAux = optionalPrendasToListAtuendo(obligatorios.get(0));
            for (int i = 1; i < cantObligatorios ; i++) {
                master = addToAtuendos(atuendosAux,obligatorios.get(i));
                atuendosAux = master;
            }

            List<Atuendo> atuendosConOpcionales = new  ArrayList<Atuendo>();
            for (int i = 0; i < cantOpcionales ; i++) {
                atuendosConOpcionales = addToAtuendos(atuendosAux,opcionales.get(i));
                atuendosAux = atuendosConOpcionales;
            }

            master.addAll(atuendosConOpcionales);

        }catch (Exception e){

        }


        return master;
    }

    private List<Atuendo> optionalPrendasToListAtuendo(List<Optional<Prenda>> prendas){
        List<Atuendo> atuendos = new  ArrayList<Atuendo>();
        prendas.forEach( uno -> {
            Atuendo atuendo = new Atuendo();
            try {
                atuendo.anadirPrenda(uno.orElseThrow( () -> new Exception("Error")));
                atuendos.add(atuendo);
            }catch (Exception e){
                // no me importa el handleo de la excepcion porque lo estoy tirando a proposito para que no se agrege a la lista como null
            }

        });
        return atuendos;
    }

    private List<Atuendo> addToAtuendos(List<Atuendo> atuendos ,List<Optional<Prenda>> prendas){
        List<Atuendo> nuevo = new  ArrayList<Atuendo>();
        atuendos.forEach(atuendo -> {
            prendas.forEach(prenda ->{
                Atuendo nuevoAtuendo= new Atuendo();
                try {
                    nuevoAtuendo.anadirPrendas(atuendo.getPrendas());
                    nuevoAtuendo.anadirPrenda(prenda.orElseThrow( () -> new Exception("Error")));
                    nuevo.add(nuevoAtuendo);
                }catch (Exception e){
                    // no me importa el handleo de la excepcion porque lo estoy tirando a proposito para que no se agrege a la lista como null
                }
            });
        });
        return nuevo;
    }


    private Predicate<? super Prenda> prendaObligatoriaFilterCondition(){
        return this::condicionFiltroPrendasObligatorias;
    }

    private Predicate<? super Prenda> accesoriosFilterCondition(){
        return this::condicionFiltroAccesorio;
    }

    private Predicate<? super Prenda> prendaSuperiorObligatoria(){

        return this::condicionSuperiorObligatoria;
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

    private boolean condicionSuperiorObligatoria(Prenda prenda){
        Superior superior = (Superior) prenda;
        //TODO: condicion para agregar o no una prenda al atuendo;
        return (superior.getTipoSuperior()==null || superior.getTipoSuperior()==0);
    }

    private boolean condicionPrendaSuperiorTipo(Superior prenda,Integer tipo){
        Superior superior = (Superior) prenda;
        //TODO: condicion para agregar o no una prenda al atuendo;
        return (superior.getTipoSuperior()==tipo);
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

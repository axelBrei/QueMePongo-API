package utn.frba.dds.que_me_pongo.Helpers;

import org.paukov.combinatorics.CombinatoricsFactory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;
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

import java.lang.reflect.Executable;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AtuendosRecomendationHelper {
    private List<Class<? extends Prenda>> tiposPrendaObligatorio = Arrays.asList(Superior.class, Inferior.class, Calzado.class);
    private List<Integer> superiorObligatorias = Arrays.asList(1,2);
    private List<Integer> inferiorObligatorias = Arrays.asList(1);
    private List<Integer> calzadoObligatorias = Arrays.asList(1);
    private List<Integer> accesoriosObligatorias = Arrays.asList();
    private Integer maxPorTipoPrenda=3;

    /*
    private List<List<Integer>> vectorOptionalCombination(Integer cantVect , Integer sizeObligatorios){
        Integer size = cantVect-sizeObligatorios;
        List<List<Integer>> obtenidos= new ArrayList<List<Integer>>();
        Vector<Integer> vec = new Vector<Integer>() ;
        for (int i = 0; i < cantVect ; i++) {
            vec.add(i);
        }
        ICombinatoricsVector originalVector = CombinatoricsFactory.createVector(vec);

        for (int i = 0; i <= (maxPorTipoPrenda-sizeObligatorios) && i <= size; i++) {
            Generator gen = CombinatoricsFactory.createSimpleCombinationGenerator(originalVector,i);
            List<ICombinatoricsVector> combinado =  gen.generateAllObjects();
            combinado.forEach(c -> obtenidos.add(c.getVector()));
        }

        return obtenidos;
    }
    */
    private List<List<Integer>> vectorOptionalCombination(Integer cantVect , Integer sizeObligatorios){
        Integer size = cantVect;
        List<List<Integer>> obtenidos= new ArrayList<List<Integer>>();
        Vector<Integer> vec = new Vector<Integer>() ;
        for (int i = 0; i < cantVect ; i++) {
            vec.add(i);
        }
        ICombinatoricsVector originalVector = CombinatoricsFactory.createVector(vec);

        Generator gen = CombinatoricsFactory.createSimpleCombinationGenerator(originalVector,sizeObligatorios);
        List<ICombinatoricsVector> combinado =  gen.generateAllObjects();
        combinado.forEach(c -> obtenidos.add(c.getVector()));


        return obtenidos;
    }


    public List<Atuendo> generarAtuendosCero(List<Prenda> superior){
        return generarAtuendos(superior.stream().filter(p->p.getClass().equals(Superior.class)).collect(Collectors.toList()),
                                superior.stream().filter(p->p.getClass().equals(Inferior.class)).collect(Collectors.toList()),
                                superior.stream().filter(p->p.getClass().equals(Calzado.class)).collect(Collectors.toList()),
                                    superior.stream().filter(p->p.getClass().equals(Accesorios.class)).collect(Collectors.toList()));
    }

    private List<List<Optional<Prenda>>> splitCapas(List<Prenda> prendas){
        List<List<Optional<Prenda>>> listaDeCapas = new ArrayList<List<Optional<Prenda>>>();
        List<Optional<Prenda>> prendasO = prendas.stream().map(Optional::ofNullable).collect(Collectors.toList());

        List<Integer> capas = prendas.stream()
                .map(Prenda::getPosicion)
                .distinct()
                .collect(Collectors.toList());
        /*
                .distinct()
                .collect(Collectors.toList());*/
                //prendasO.stream().map( p -> p.get().getPosicion()).distinct().collect(Collectors.toList());

        capas.forEach( posicion -> {
            listaDeCapas.add(
                    prendasO.stream()
                            .filter(p -> p.get().getPosicion().equals(posicion))

                            .collect(Collectors.toList()));
        });

        return listaDeCapas;
    }


    private List<Atuendo> generarAtuendos(List<Prenda> superior, List<Prenda> inferior, List<Prenda> calzado, List<Prenda> accesorio){

        List<Atuendo> atuendoList = new ArrayList<Atuendo>();

        List<Prenda> superiorObl = filterPorPosicion(superior,this.superiorObligatorias);
        List<Prenda> inferiorObl = filterPorPosicion(inferior,this.superiorObligatorias);
        List<Prenda> calzadoObl =  filterPorPosicion(calzado,this.superiorObligatorias);

        List<List<Optional<Prenda>>> superiorOpt = splitCapas( superior.stream().filter(s-> superiorObl.stream().noneMatch(o -> o.hashCode()==s.hashCode())).collect(Collectors.toList()));
        List<List<Optional<Prenda>>> inferiorOpt = splitCapas(inferior.stream().filter(s-> inferiorObl.stream().noneMatch(o -> o.hashCode()==s.hashCode())).collect(Collectors.toList()));
        List<List<Optional<Prenda>>> calzadoOpt = splitCapas(calzado.stream().filter(s-> calzadoObl.stream().noneMatch(o ->o.hashCode()==s.hashCode())).collect(Collectors.toList()));

        List<List<Optional<Prenda>>> obligatorios = splitCapas(superiorObl);
        obligatorios.addAll(splitCapas(inferiorObl));
        obligatorios.addAll(splitCapas(calzadoObl));


        List<List<Optional<Prenda>>> opcionales = superiorOpt;
        opcionales.addAll(inferiorOpt);
        opcionales.addAll(calzadoOpt);
        opcionales.addAll(splitCapas(accesorio));



        return getAllConbinations(obligatorios,superiorOpt);
    }




    private List<Atuendo> getAllConbinations(List<List<Optional<Prenda>>> obligatorios,List<List<Optional<Prenda>>> opcionales){
        Integer cantObligatorios = obligatorios.size();
        Integer cantOpcionales = opcionales.size();
        List<Atuendo> master = new  ArrayList<Atuendo>();
        List<Atuendo> secundario = new  ArrayList<Atuendo>();
        final List<Atuendo> copymaster = new  ArrayList<Atuendo>();

        try {
            List<Atuendo> atuendosAux = optionalPrendasToListAtuendo(obligatorios.get(0));
            for (int i = 1; i < cantObligatorios ; i++) {
                master = addToAtuendos(atuendosAux,obligatorios.get(i));
                atuendosAux = master;
            }

            copymaster.addAll(master);
            secundario.addAll(copymaster);
        }catch (Exception e){

        }

        for (int i = 1; i < cantOpcionales+1 ; i++) {
            List<List<Integer>> conv = vectorOptionalCombination(cantOpcionales,i);
            conv.forEach(unaConvinacion ->{

                List<List<Optional<Prenda>>> seleccionados = seleccionarDelVector(opcionales,unaConvinacion);
                secundario.addAll(addListToAtuendos(copymaster,convinaciones(seleccionados)));


            });

        }
        

        return secundario;
    }

    private List<List<Optional<Prenda>>> seleccionarDelVector(List<List<Optional<Prenda>>> opcionales,List<Integer> unaConvinacion){
        List<List<Optional<Prenda>>> response = new ArrayList<>();
        unaConvinacion.forEach(numero ->{
            response.add(opcionales.get(numero));
        });

        return response;
    }


    private List<Atuendo> addListToAtuendos(List<Atuendo> atuendos , List<Atuendo> atuendoList ){
        List<Atuendo> nuevo = new  ArrayList<Atuendo>();
        atuendos.forEach(atuendo -> {
            atuendoList.forEach(otroAtuendo ->{
                Atuendo nuevoAtuendo= new Atuendo();
                try {
                    nuevoAtuendo.anadirPrendas(atuendo.getPrendas());
                    nuevoAtuendo.anadirPrendas(otroAtuendo.getPrendas());
                    nuevo.add(nuevoAtuendo);
                }catch (Exception e){
                    // no me importa el handleo de la excepcion porque lo estoy tirando a proposito para que no se agrege a la lista como null
                }
            });
        });
        return nuevo;
    }



    private List<Atuendo> convinaciones(List<List<Optional<Prenda>>> prendasList){
        final List<Atuendo>  out = new ArrayList<Atuendo>();

        prendasList.get(0).forEach(prenda -> {
            out.add(new Atuendo(prenda.get()));
        });
        prendasList.remove(0);

        final List<Atuendo> nueva = new ArrayList<Atuendo>();



        prendasList.forEach(list -> {
            nueva.clear();
            nueva.addAll(out);
            out.clear();

                nueva.forEach(atuendo ->{
                    list.forEach(prenda -> {
                        Atuendo nuevo = new Atuendo();
                        nuevo.anadirPrendas(atuendo.getPrendas());
                        nuevo.anadirPrenda(prenda.get());
                        out.add(nuevo);
                    });

                });
        });

        return out;
    }




    public Atuendo generarAtuendoRecomendado(List<Prenda> prendas, Predicate<? super Prenda> condicionPrendas, Predicate<? super  Prenda> condicionAccesorio) throws AtuendoIncompletoException{
        Atuendo atuendo = new Atuendo();


        tiposPrendaObligatorio.forEach( type -> {
            if(type != Superior.class){
            Optional<Prenda> prendaOptional = prendas
                    .stream()
                    .filter(p -> p.getClass().equals(type))
                    .filter(condicionPrendas)
                    .findFirst();
            atuendo.anadirPrenda(
                    prendaOptional.orElseThrow( () -> new AtuendoIncompletoException(HttpStatus.NOT_FOUND, type.getSimpleName()))
            );
            }else {
                Optional<Prenda> prendaOptional = prendas
                        .stream()
                        .filter(p -> p.getClass().equals(type))
                        .filter(prendaSuperiorObligatoria())
                        .filter(condicionPrendas)
                        .findFirst();
                atuendo.anadirPrenda(
                        prendaOptional.orElseThrow( () -> new AtuendoIncompletoException(HttpStatus.NOT_FOUND, type.getSimpleName() , " de nievel 0"))
                );
            }
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



    public  List<Atuendo> optionalToAtuendosList(List<Optional<Atuendo>> optional){
        List<Atuendo> retornar = new ArrayList<Atuendo>();
        optional.forEach(a->{
            retornar.add(a.get());
        });
        return retornar;
    }

    public List<Atuendo> generarAllAtuendos(List<Prenda> prendas, Predicate<? super Prenda> condicionPrendas, Predicate<? super  Prenda> condicionAccesorio) throws AtuendoIncompletoException{
        List<List<Optional<Prenda>>> obligatorios = new  ArrayList<List<Optional<Prenda>>>();
        List<Optional<Prenda>> superioresNoObligatorias = new  ArrayList<Optional<Prenda>>();
        List<List<Optional<Prenda>>> opcionales = new  ArrayList<List<Optional<Prenda>>>();
        //Chequeo si estan las prendas basicas
        Atuendo at = generarAtuendoRecomendado(prendas,condicionPrendas,condicionAccesorio);

        tiposPrendaObligatorio.forEach( type -> {
            if(type != Superior.class) {
            List<Optional<Prenda>> prendaList = prendas.stream()
                    .filter(p -> p.getClass().equals(type))
                    .filter(condicionPrendas)
                    .map(Optional::ofNullable)
                    .collect(Collectors.toList());

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

                List<Optional<Prenda>> noObliga = prendas.stream()
                                        .filter(p -> p.getClass().equals(Superior.class))
                                        .filter(condicionPrendas)
                                        //.map(p -> (Superior)p)
                                        .filter(prendaSuperiorObligatoria().negate())
                                        .map(Optional::ofNullable)
                                        .collect(Collectors.toList());
                superioresNoObligatorias.addAll(noObliga);
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

        obligatorios.add(getTipo(superioresNoObligatorias,1));
        List<Atuendo> completaMasUno = getAllConbinations(obligatorios,opcionales);
        completa.addAll(completaMasUno);

        obligatorios.add(getTipo(superioresNoObligatorias,2));
        List<Atuendo> completaMasDos = getAllConbinations(obligatorios,opcionales);
        completa.addAll(completaMasDos);

        obligatorios.add(getTipo(superioresNoObligatorias,3));
        List<Atuendo> completaMasTres = getAllConbinations(obligatorios,opcionales);
        completa.addAll(completaMasTres);


        return completa;
    }

    private List<Prenda> filterPorPosicion(List<Prenda> all , List<Integer> posiciones){
        return all.stream().filter(prenda -> posiciones.stream().anyMatch(p -> p.equals(prenda.getPosicion()))).collect(Collectors.toList());
    }


    private List<Optional<Prenda>> getTipo(List<Optional<Prenda>> sup, Integer tipoNumero){
        List<Optional<Prenda>> tipo = new  ArrayList<Optional<Prenda>>();
        List<Prenda> superiores = optionalListPrendasToListPrendas(sup);

        try {
            tipo= superiores.stream()
                            .filter( p -> condicionPrendaSuperiorTipo(p,tipoNumero))
                            .map(Optional::ofNullable)
                            .collect(Collectors.toList());
            return tipo;
        }catch (Exception e){
            return tipo;
        }


    }





    private List<Prenda> optionalListPrendasToListPrendas(List<Optional<Prenda>> prendas){
        List<Prenda> prendaList = new  ArrayList<Prenda>();
        prendas.forEach( uno -> {
            try {
                prendaList.add(uno.get());
            }catch (Exception e){
                // no me importa el handleo de la excepcion porque lo estoy tirando a proposito para que no se agrege a la lista como null
            }

        });
        return prendaList;
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

    private boolean condicionPrendaSuperiorTipo(Prenda prenda,Integer tipo){
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

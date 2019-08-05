package utn.frba.dds.que_me_pongo.Helpers;

import org.paukov.combinatorics3.Generator;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.ArrayList;
import utn.frba.dds.que_me_pongo.Exceptions.AtuendoIncompletoException;
import utn.frba.dds.que_me_pongo.WebServices.Responses.ResponseObjects.*;

@Service
public class AtuendosRecomendationHelper {



    private static final Set<String> tiposPrendaObligatorio = new HashSet<>(Arrays.asList("Superior", "Inferior", "Calzado", "Accesorio"));



//    Filtrar prendas
    public Set<Atuendo>     generarAtuendos(String uid, int idGuardarropa, ClientesRepository clientesRepository){
        Set<Atuendo> atuendos = new HashSet<>();
        Guardarropa guardarropa = clientesRepository.findClienteByUid(uid).getGuardarropa(idGuardarropa);

        Generator.subset(guardarropa.getPrendas())
                .simple()
                .stream()
                // TODO: agregar un id unico a cada atuendo para guardarselo al usuario cuando deba usarlo
                .map( prendas -> new Atuendo(prendas))
                .filter(filtroPorTamano())
                .filter(filtrarPorAbrigo(100.0))
                .forEach( at -> atuendos.add(at));
        return atuendos;
    }

    private Predicate<? super Atuendo> filtroPorTamano(){
        return atuendo -> {
            if(atuendo.getPrendas().size() < 3)
                return false;

            Map<String, List<Prenda>> prendasSeparadas = atuendo.getPrendas().stream().collect(Collectors.groupingBy(Prenda::getTipoDePrenda));
            for (List<Prenda> pr: prendasSeparadas.values()) {
                if(pr.size() > 3)
                    return false;
            }
            if(isComposicionAuendoCorrecta(prendasSeparadas)){
                return noSuperposicion(prendasSeparadas);
            }
            return false;
        };
    }

    private Boolean isComposicionAuendoCorrecta(Map<String, List<Prenda>> map){
        return map.containsKey("Superior") &&
                map.containsKey("Inferior") &&
                map.containsKey("Calzado");
    }

    private Boolean noSuperposicion(Map<String, List<Prenda>> map){
        for (List<Prenda> p: map.values()) {
            Map<Integer, List<Prenda>> prendaMaped =  p.stream().collect(Collectors.groupingBy(Prenda::getIndiceSuperposicion));
            Long cantidad = prendaMaped.values().stream().filter(list -> list.size() > 1).count();
            if(cantidad > 0)
                return false;
        }
        return true;
    }

    private Predicate<? super Atuendo> filtrarPorAbrigo(Double abrigoRequerido){
        return atuendo -> {
            Double sumatoria =  atuendo.getPrendas().stream().mapToDouble(Prenda::getAbrigo).sum();
            return sumatoria > abrigoRequerido;
        };
    }

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


    public List<Atuendo> generarAllAtuendos(List<Prenda> prendas, Predicate<? super Prenda> condicionPrendas, Predicate<? super  Prenda> condicionAccesorio) throws AtuendoIncompletoException{
        List<List<Optional<Prenda>>> obligatorios = new  ArrayList<List<Optional<Prenda>>>();
        List<Optional<Prenda>> superioresNoObligatorias = new  ArrayList<Optional<Prenda>>();
        List<List<Optional<Prenda>>> opcionales = new  ArrayList<List<Optional<Prenda>>>();
        //Chequeo si estan las prendas basicas
        Atuendo at = generarAtuendoRecomendado(prendas,condicionPrendas,condicionAccesorio);

        tiposPrendaObligatorio.forEach( type -> {
            if(type != "Superior") {
                List<Optional<Prenda>> prendaList = prendas.stream()
                        .filter(p -> p.getTipoDePrenda().equals(type))
                        .filter(condicionPrendas)
                        .map(Optional::ofNullable)
                        .collect(Collectors.toList());

                obligatorios.add(prendaList);
            }else {
                List<Optional<Prenda>> superioresObligatorias = prendas.stream()
                        .filter(p -> p.getTipoDePrenda().equals("Superior"))
                        .filter(condicionPrendas)
                        //.map(p -> (Superior)p)
                        .filter(prendaSuperiorObligatoria())
                        .map(Optional::ofNullable)
                        .collect(Collectors.toList());
                obligatorios.add(superioresObligatorias);

                List<Optional<Prenda>> noObliga = prendas.stream()
                        .filter(p -> p.getTipoDePrenda().equals("Superior"))
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
                        .filter( p -> p.getTipoDePrenda().equals("Accesorio"))
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



    public  List<Atuendo> optionalToAtuendosList(List<Optional<Atuendo>> optional){
        List<Atuendo> retornar = new ArrayList<Atuendo>();
        optional.forEach(a->{
            retornar.add(a.get());
        });
        return retornar;
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

    private boolean condicionPrendaSuperiorTipo(Prenda prenda,Integer tipo){

        //TODO: condicion para agregar o no una prenda al atuendo;
        return (prenda.getIndiceSuperposicion()==tipo);
    }

    private Predicate<? super Prenda> prendaSuperiorObligatoria(){

        return this::condicionSuperiorObligatoria;
    }

    private boolean condicionSuperiorObligatoria(Prenda prenda){

        //TODO: condicion para agregar o no una prenda al atuendo;
        return ( prenda.getIndiceSuperposicion()==0);
    }

    public Atuendo generarAtuendoRecomendado(List<Prenda> prendas, Predicate<? super Prenda> condicionPrendas, Predicate<? super  Prenda> condicionAccesorio) throws AtuendoIncompletoException{
        Atuendo atuendo = new Atuendo();


        tiposPrendaObligatorio.forEach( type -> {
            if(type != "Superior"){
                Optional<Prenda> prendaOptional = prendas
                        .stream()
                        .filter(p -> p.getTipoDePrenda().equals(type))
                        .filter(condicionPrendas)
                        .findFirst();
                atuendo.anadirPrenda(
                        prendaOptional.orElseThrow( () -> new AtuendoIncompletoException(HttpStatus.NOT_FOUND, type))
                );
            }else {
                Optional<Prenda> prendaOptional = prendas
                        .stream()
                        .filter(p -> p.getTipoDePrenda().equals(type))
                        .filter(prendaSuperiorObligatoria())
                        .filter(condicionPrendas)
                        .findFirst();
                atuendo.anadirPrenda(
                        prendaOptional.orElseThrow( () -> new AtuendoIncompletoException(HttpStatus.NOT_FOUND, type , " de nievel 0"))
                );
            }
        });

        Optional<Prenda> prenda = prendas
                .stream()
                .filter( p -> p.getTipoDePrenda().equals("Accesorio"))
                .filter(condicionAccesorio)
                .findFirst();
        try {
            atuendo.anadirPrenda(prenda.orElseThrow( () -> new Exception("Error")));
        }catch (Exception e){
            // no me importa el handleo de la excepcion porque lo estoy tirando a proposito para que no se agrege a la lista como null
        }
        return atuendo;
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



}




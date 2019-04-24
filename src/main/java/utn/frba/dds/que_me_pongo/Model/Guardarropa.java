package utn.frba.dds.que_me_pongo.Model;

import ch.qos.logback.core.net.server.Client;
import utn.frba.dds.que_me_pongo.QueMePongoApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Guardarropa {

    private String descripcion;
    private int id;
    private List<Prenda> prendas ;
    List<String> prendasAdmitidas = Arrays.asList("Accesorio,PrendaSuperior,PrendaInferior,Calzado");


    public Guardarropa() {
        this.prendas = new ArrayList<>();
    }

    public List<Prenda> getPrendas() {
        return prendas;
    }

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }

    boolean seAdmitePrenda(Prenda unaPrenda){
        return( prendasAdmitidas.contains(  unaPrenda.getTipo() ));
    }

    public void ocuparPrenda(String unaCategoria){
        prendasAdmitidas.remove(unaCategoria);
    }

    public void liberarPrenda(String unaCategoria){
        prendasAdmitidas.add( unaCategoria);
    }

    void eliminarPrenda(Prenda unaPrenda){

        prendas.remove(unaPrenda);
        unaPrenda.liberarDeGuardarropa();
        liberarPrenda( unaPrenda.getTipo());
    }

    void aniadirPrenda(Prenda unaPrenda) throws Exception {
            if ( seAdmitePrenda(unaPrenda) )  {

            throw new Exception("Lugar Ocupado, Quitar o ingresar otro tipo de ropa");
            } else {
                prendas.add(unaPrenda);
                unaPrenda.ocuparEnGuardarropa();
                ocuparPrenda( unaPrenda.getTipo());
            }

    }

    List<Prenda> sugerirPrendas(Cliente unCliente){
    return unCliente.getPrendasMaestras().stream().filter(prenda -> this.seAdmitePrenda(prenda) && !prenda.estaOcupado()).collect(Collectors.toList());
    }

}

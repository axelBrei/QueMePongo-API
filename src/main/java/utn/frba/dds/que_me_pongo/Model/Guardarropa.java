package utn.frba.dds.que_me_pongo.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Guardarropa {

    private String descripcion;
    private int id;
    private List<Prenda> prendas = new ArrayList<Prenda>();

   public Guardarropa(String unaDescripcion){
        this.descripcion = unaDescripcion;

    }
    public void setId(int id){this.id = id;}

    public List<Prenda> getPrendas() {
        return this.prendas;
    }

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }

    public void eliminarPrenda(Prenda unaPrenda){
        this.prendas.remove(unaPrenda);
    }
    public String getDescripcion(){return this.descripcion;}
    public int getId(){return this.id;}
    public String getDesc(){return this.descripcion;}

    public void aniadirPrenda(Prenda unaPrenda) {
            this.prendas.add(unaPrenda);
    }
    public void aniadirPrendas(List<Prenda> prendas) {
        this.prendas.addAll(prendas);
    }

    public List<Atuendo> allAtuendos(){
       String clas = "utn.frba.dds.que_me_pongo.Model.TiposPrenda.";
        //List<Prenda> prendas = PrendasJsonParser.getJsonPrendasJson();
       List<Prenda> superiores = (List<Prenda>) prendas.stream().filter(p -> p.getClass().getName().equals(clas+"PrendaSuperior")).collect(Collectors.toList());
        List<Prenda> inferior = (List<Prenda>) prendas.stream().filter(p -> p.getClass().getName().equals(clas+"PrendaInferior")).collect(Collectors.toList());
        List<Prenda> calzado = (List<Prenda>) prendas.stream().filter(p -> p.getClass().getName().equals(clas+"Calzado")).collect(Collectors.toList());
        List<Prenda> accesorios = (List<Prenda>) prendas.stream().filter(p -> p.getClass().getName().equals(clas+"Accesorios")).collect(Collectors.toList());


        List<Atuendo> atuendoList = new ArrayList<>();

        for (Prenda s: superiores){
            for (Prenda i: inferior) {
                for (Prenda c: calzado ) {
                    List<Prenda> prendasAtuendo = new ArrayList<>();
                    prendasAtuendo.add(s);
                    prendasAtuendo.add(i);
                    prendasAtuendo.add(c);
                    atuendoList.add(new Atuendo(prendasAtuendo));
                }
            }
        }

        List<Atuendo> atuendoListConAcc = new ArrayList<>();
        for (Atuendo atuendo: atuendoList) {
            for (Prenda a: accesorios) {
                List<Prenda> prendasAtuendo = new ArrayList<>();
                prendasAtuendo.addAll(atuendo.getPrendas());
                prendasAtuendo.add(a);
                atuendoListConAcc.add(new Atuendo(prendasAtuendo));
            }
        }


        List<Atuendo> todos = new ArrayList<Atuendo>(atuendoList);
        todos.addAll(atuendoListConAcc);
        return todos;
    }

    public  void addPrenda(Prenda prenda){
        prendas.add(prenda);
    }


    public  void  deletePrenda(int idPrenda){
        int index=-1;
        for (Prenda p: prendas) {
            if (p.getId() == idPrenda) {
                index =  prendas.indexOf(p);
                break;
            }
        }
        if(index!=-1)
            prendas.remove(index);

    }
}

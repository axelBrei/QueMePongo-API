package utn.frba.dds.que_me_pongo.Model;

import utn.frba.dds.que_me_pongo.Model.TiposPrenda.Superior;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Atuendo {
    private List<Prenda> prendas;

    public Atuendo(List<Prenda> prendas) {
        this.prendas = prendas;
    }
    public Atuendo(Prenda prenda) {
        this.prendas = new ArrayList<Prenda>();
        this.prendas.add(prenda);
    }

    public Atuendo() {
        prendas = new ArrayList<>();
    }

    public void anadirPrenda(Prenda p){
        prendas.add(p);
    }
    public void anadirPrendas(List<Prenda> p){
        prendas.addAll(p);
    }

    public Boolean tieneAlgunaPrenda(List<Prenda> prendas){
        return  this.prendas.stream().anyMatch(p-> prendas.stream().anyMatch(prenda -> prenda.getId().equals(p.getId())));
    }

    public Boolean esSuficienteAbrigado(Float temperatura){
        Double top = 50.0;
        Double margen = 5.0;
        Double necesita = top - temperatura;

        if(necesita<20){
            return this.getAbrigo()==20;
        }else{
            System.out.println(necesita);
            return (this.getAbrigo()<=necesita+margen && this.getAbrigo()>=necesita-margen);
        }
    }

    public Double getAbrigo(){
        Double abrigo = 0.0;
        for (int i = 0; i < prendas.size(); i++) {
            abrigo += prendas.get(i).getAbrigo();
        }
        return abrigo;
    }

    public Boolean esCorrecto(){
        List<Superior> prendaList = this.prendas.stream().filter(p -> p.getClass().equals(Superior.class)).map(prenda -> new Superior().prendaToSuperior(prenda)).collect(Collectors.toList());


        Integer cant = prendaList.size();


        switch (cant){
            case 1:
                return (prendaList.get(0).getPosicion()==0);
            case 2:
                return (1==prendaList.stream().mapToInt(Superior::getPosicion).sum());
            case 3:
                return (3==prendaList.stream().mapToInt(Superior::getPosicion).sum());
            case 4:
                return (6==prendaList.stream().mapToInt(Superior::getPosicion).sum());
        }
        return false;
    }



    public void eliminarPrenda(Prenda p){
        prendas.remove(p);
    }

    public List<Prenda> getPrendas() {
        return prendas;
    }

    public void setPrendas(List<Prenda> prendas) {
        this.prendas = prendas;
    }
}

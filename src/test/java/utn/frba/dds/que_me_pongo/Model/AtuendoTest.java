package utn.frba.dds.que_me_pongo.Model;

import org.junit.Assert;
import org.junit.Test;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.PronosticoClassOpenWeather.Sys;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AtuendoTest {

    @Test
    public void anadirPrenda() {
        Prenda prenda1 = new Prenda(1,"lana","Remera","rojo","","Superior",8.0,"Informal",1);
        Prenda prenda2 = new Prenda(2,"jean","Pantalon","azul","","Inferior",8.0,"Formal",1);
        Prenda prenda3 = new Prenda(3,"tela","Zapatillas","rojo","negro","Calzado",8.0,"Informal",1);
        Prenda prenda4 = new Prenda(4,"tela","Zapatillas","rojo","azul","Calzado",8.0,"Informal",1);
        Prenda prenda5 = new Prenda(5, "cuero","Campera","negro","","Superior",8.0,"Informal",3);
        Prenda prenda6 = new Prenda(6,"","collar","plata","","Accesorio",0.0,"Formal",1);
        Prenda prenda7 = new Prenda(7,"algodon","Remera","verde","negro","Superior",8.0,"Informal",1);
        List<Prenda> listaPrendas = new ArrayList<Prenda>();
        listaPrendas.add(prenda1);
        listaPrendas.add(prenda2);
        listaPrendas.add(prenda3);
        listaPrendas.add(prenda4);
        listaPrendas.add(prenda5);
        listaPrendas.add(prenda6);
        Atuendo atuendo = new Atuendo(listaPrendas);
        Assert.assertEquals(6,atuendo.getPrendas().size());
        atuendo.anadirPrenda(prenda7);
        Assert.assertEquals(7,atuendo.getPrendas().size());
    }

    @Test
    public void anadirPrendas() {
        Prenda prenda1 = new Prenda(1,"lana","Remera","rojo","","Superior",8.0,"Informal",1);
        Prenda prenda2 = new Prenda(2,"jean","Pantalon","azul","","Inferior",8.0,"Formal",1);
        Prenda prenda3 = new Prenda(3,"tela","Zapatillas","rojo","negro","Calzado",8.0,"Informal",1);
        Prenda prenda4 = new Prenda(4,"tela","Zapatillas","rojo","azul","Calzado",8.0,"Informal",1);
        Prenda prenda5 = new Prenda(5, "cuero","Campera","negro","","Superior",8.0,"Informal",3);
        Prenda prenda6 = new Prenda(6,"","collar","plata","","Accesorio",0.0,"Formal",1);
        List<Prenda> listaPrendas = new ArrayList<Prenda>();
        listaPrendas.add(prenda1);
        listaPrendas.add(prenda2);
        Atuendo atuendo = new Atuendo(listaPrendas);
        List<Prenda> listaPrendas2 = new ArrayList<Prenda>();
        listaPrendas2.add(prenda3);
        listaPrendas2.add(prenda4);
        listaPrendas2.add(prenda5);
        listaPrendas2.add(prenda6);
        atuendo.anadirPrendas(listaPrendas2);
        Assert.assertEquals(6,atuendo.getPrendas().size());



    }

    @Test
    public void tieneAlgunaPrenda() {
        Prenda prenda1 = new Prenda(1,"lana","Remera","rojo","","Superior",8.0,"Informal",1);
        Prenda prenda2 = new Prenda(2,"jean","Pantalon","azul","","Inferior",8.0,"Formal",1);
        Prenda prenda3 = new Prenda(3,"tela","Zapatillas","rojo","negro","Calzado",8.0,"Informal",1);
        Prenda prenda4 = new Prenda(4,"tela","Zapatillas","rojo","azul","Calzado",8.0,"Informal",1);
        Prenda prenda5 = new Prenda(5, "cuero","Campera","negro","","Superior",8.0,"Informal",3);
        Prenda prenda6 = new Prenda(6,"","collar","plata","","Accesorio",0.0,"Formal",1);
        Prenda prenda7 = new Prenda(7,"algodon","Remera","verde","negro","Superior",8.0,"Informal",1);
        List<Prenda> listaPrendas = new ArrayList<Prenda>();
        listaPrendas.add(prenda1);
        listaPrendas.add(prenda2);
        listaPrendas.add(prenda3);
        listaPrendas.add(prenda4);
        listaPrendas.add(prenda5);
        listaPrendas.add(prenda6);
        Atuendo atuendo = new Atuendo();
        Assert.assertEquals(false,atuendo.tieneAlgunaPrenda(listaPrendas));

    }

    @Test
    public void esSuficienteAbrigado() {
        Prenda prenda1 = new Prenda(1,"lana","Remera","rojo","","Superior",8.0,"Informal",1);
        Prenda prenda2 = new Prenda(2,"jean","Pantalon","azul","","Inferior",8.0,"Formal",1);
        Prenda prenda3 = new Prenda(3,"tela","Zapatillas","rojo","negro","Calzado",8.0,"Informal",1);
        Prenda prenda5 = new Prenda(5, "cuero","Campera","negro","","Superior",8.0,"Informal",3);
        Prenda prenda6 = new Prenda(6,"","collar","plata","","Accesorio",0.0,"Formal",1);
       List<Prenda> listaPrendas = new ArrayList<Prenda>();
        listaPrendas.add(prenda1);
        listaPrendas.add(prenda2);
        listaPrendas.add(prenda3);
        listaPrendas.add(prenda5);
        listaPrendas.add(prenda6);
        Atuendo atuendo = new Atuendo(listaPrendas);
        Assert.assertEquals(        true, atuendo.esSuficienteAbrigado((float) 20.3)     );
    }

    @Test
    public void getAbrigo() {
        Prenda prenda1 = new Prenda(1,"lana","Remera","rojo","","Superior",8.0,"Informal",1);
        Prenda prenda2 = new Prenda(2,"jean","Pantalon","azul","","Inferior",8.0,"Formal",1);
        Prenda prenda3 = new Prenda(3,"tela","Zapatillas","rojo","negro","Calzado",8.0,"Informal",1);
        Prenda prenda5 = new Prenda(5, "cuero","Campera","negro","","Superior",8.0,"Informal",3);
        Prenda prenda6 = new Prenda(6,"","collar","plata","","Accesorio",0.0,"Formal",1);
        List<Prenda> listaPrendas = new ArrayList<Prenda>();
        listaPrendas.add(prenda1);
        listaPrendas.add(prenda2);
        listaPrendas.add(prenda3);
        listaPrendas.add(prenda5);
        listaPrendas.add(prenda6);
        Atuendo atuendo = new Atuendo(listaPrendas);

        Assert.assertEquals( (Double) 32.0,atuendo.getAbrigo()   );

    }

    @Test
    public void eliminarPrenda() {
        Prenda prenda1 = new Prenda(1,"lana","Remera","rojo","","Superior",8.0,"Informal",1);
        Prenda prenda2 = new Prenda(2,"jean","Pantalon","azul","","Inferior",8.0,"Formal",1);
        Prenda prenda3 = new Prenda(3,"tela","Zapatillas","rojo","negro","Calzado",8.0,"Informal",1);
        Prenda prenda5 = new Prenda(5, "cuero","Campera","negro","","Superior",8.0,"Informal",3);
        Prenda prenda6 = new Prenda(6,"","collar","plata","","Accesorio",0.0,"Formal",1);
        List<Prenda> listaPrendas = new ArrayList<Prenda>();
        listaPrendas.add(prenda1);
        listaPrendas.add(prenda2);
        listaPrendas.add(prenda3);
        listaPrendas.add(prenda5);
        listaPrendas.add(prenda6);
        Atuendo atuendo = new Atuendo(listaPrendas);
        atuendo.eliminarPrenda(prenda1);
        Assert.assertEquals(4,atuendo.getPrendas().size());

    }
}
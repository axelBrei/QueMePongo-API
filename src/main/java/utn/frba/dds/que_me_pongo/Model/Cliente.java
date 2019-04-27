package utn.frba.dds.que_me_pongo.Model;



import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String userName;
    private String nombre;


    private List<Guardarropa> guardarropas = new ArrayList<Guardarropa>();

    public Cliente(String userName, String nombre)  {
        this.nombre=nombre;
        this.userName=userName;


    }



    public List<Guardarropa> getGuardarropas() {
        return this.guardarropas;
    }

    public void setGuardarropas(List<Guardarropa> guardarropas) {
        this.guardarropas = guardarropas;
    }

    public void addGuardarropa(Guardarropa g){
        this.guardarropas.add(g);
    }

    public void anadirPrendaAlGuardarropa(Prenda prenda, int id){
        for (Guardarropa g: guardarropas) {
            if (g.getId() == id) {
                prenda.setId((int)(Math.random()*999 )+1);
                g.addPrenda(prenda);
                break;
            }
        }
    }

    public Guardarropa getGuardarropa(int id){

        for (Guardarropa g: guardarropas) {
            if (g.getId() == id) {
                return g;
            }
        }

        return null;
    }

    public void deleteGuardarropa(int id){
        int index=-1;
        for (Guardarropa g: guardarropas) {
            if (g.getId() == id) {
                index =  guardarropas.indexOf(g);
                break;
            }
        }
        if(index!=-1)
            guardarropas.remove(index);

    }

    public void deletePrendaDelGuardarropa(int idPrenda,int id){
        for (Guardarropa g: guardarropas) {
            if (g.getId() == id) {
                g.deletePrenda(idPrenda);

            }
        }
    }

    public String getUser(){return this.userName;}
    public String getName(){return this.nombre;}




}


package utn.frba.dds.que_me_pongo.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Evento {
    private String nombre;
    private Date fecha;
    private int hora;
    private Ubicacion ubicacion;
    private List<Atuendo> sugeridos = new ArrayList<Atuendo>();
    private Atuendo seleccionado;

    public Evento (String nombre,Date fecha, int hora, Ubicacion ubicacion){
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.ubicacion = ubicacion;
    }

    public void obtenerAtuendos(){

    }

    public  void rechazar(Atuendo atuendo){
        sugeridos.remove(atuendo);
    }

    public void aceptar(Atuendo atuendo){
        this.seleccionado = atuendo;
    }

}

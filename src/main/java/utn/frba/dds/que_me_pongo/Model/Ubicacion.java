package utn.frba.dds.que_me_pongo.Model;

public class Ubicacion {
    private double latitud;
    private double longitud;

    public Ubicacion(double latitud , double longitud){
        this.latitud= latitud;
        this.longitud = longitud;
    }

    public Ubicacion getUbicacion(){return this;}
}

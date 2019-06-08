package utn.frba.dds.que_me_pongo.WebServices.Responses.ResponseObjects;

public class CantidadGuardarropaResponseObject {
    private int id;
    private String descripcion;

    public CantidadGuardarropaResponseObject() {
    }

    public CantidadGuardarropaResponseObject(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

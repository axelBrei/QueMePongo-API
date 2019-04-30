package utn.frba.dds.que_me_pongo.WebServices.Responses.ResponseObjects;

public class CantidadGuardarropaResponseObject {
    private int id;
    private String desc;

    public CantidadGuardarropaResponseObject() {
    }

    public CantidadGuardarropaResponseObject(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

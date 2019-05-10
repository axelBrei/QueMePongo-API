package utn.frba.dds.que_me_pongo.WebServices.Request.Guardarropa;

public class GetPrendasRequest {
    private String uid;
    private int idGuardarropa;

    public GetPrendasRequest() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getIdGuardarropa() {
        return idGuardarropa;
    }

    public void setIdGuardarropa(int idGuardarropa) {
        this.idGuardarropa = idGuardarropa;
    }
}

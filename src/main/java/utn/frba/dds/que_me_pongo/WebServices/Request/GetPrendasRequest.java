package utn.frba.dds.que_me_pongo.WebServices.Request;

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

    public Integer getId() {
        return idGuardarropa;
    }

    public void setId(Integer idGuardarropa) {
        this.idGuardarropa = idGuardarropa;
    }
}

package utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Guardarropa;

public class GetGuardarropaRequest {
    private String uid;
    private int guardarropaId;

    public GetGuardarropaRequest() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getGuardarropaId() {
        return guardarropaId;
    }

    public void setGuardarropaId(int guardarropaId) {
        this.guardarropaId = guardarropaId;
    }
}

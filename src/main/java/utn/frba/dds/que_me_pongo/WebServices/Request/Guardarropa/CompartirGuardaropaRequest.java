package utn.frba.dds.que_me_pongo.WebServices.Request.Guardarropa;

public class CompartirGuardaropaRequest {
    private String uid;
    private String a_uid;
    private int guardarropaId;

    public CompartirGuardaropaRequest(String uid, String a_uid, int guardarropaId) {
        this.uid = uid;
        this.a_uid = a_uid;
        this.guardarropaId = guardarropaId;
    }

    public String getA_uid() {
        return a_uid;
    }

    public void setA_uid(String a_uid) {
        this.a_uid = a_uid;
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

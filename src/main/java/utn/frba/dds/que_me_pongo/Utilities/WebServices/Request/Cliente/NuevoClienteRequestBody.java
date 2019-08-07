package utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Cliente;

public class NuevoClienteRequestBody {
    private String uid;
    private String mail;
    private String name;

    public NuevoClienteRequestBody() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

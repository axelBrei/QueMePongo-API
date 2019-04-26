package utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer;

import java.util.ArrayList;
import java.util.List;

import utn.frba.dds.que_me_pongo.Model.Prenda;

public class PrendasContainer {
    private List<Prenda> prendaslist = new ArrayList<>();

    public PrendasContainer(List<Prenda> prendaslist) {
        this.prendaslist = prendaslist;
    }

    public PrendasContainer() {
    }

    public List<Prenda> getPrendaslist() {
        return prendaslist;
    }

    public void setPrendaslist(List<Prenda> prendaslist) {
        this.prendaslist = prendaslist;
    }

    public void addPrendaslist(List<Prenda> prendaslist) {
        this.prendaslist.addAll(prendaslist);
    }

}

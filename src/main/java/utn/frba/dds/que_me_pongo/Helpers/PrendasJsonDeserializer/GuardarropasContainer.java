package utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonParser;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Responses.GetPrendasResponse;
import utn.frba.dds.que_me_pongo.Responses.ResponseObjects.PrendaResponseObject;

import java.util.ArrayList;
import java.util.List;

public class GuardarropasContainer {
    private String descripcion;
    private int id;
    private List<PrendaResponseObject> prendas = new ArrayList<>();

    public GuardarropasContainer(Guardarropa guardarropa){
        this.descripcion = guardarropa.getDescripcion();
        this.id = guardarropa.getId();
        GetPrendasResponse response = new GetPrendasResponse();
        for (Prenda p :guardarropa.getPrendas()) {
            prendas.add(new PrendaResponseObject(p, p.getClass().getName()));
        }

    }



    public List<Prenda> getPrendas(){
        GetPrendasResponse response = new GetPrendasResponse();
        for (PrendaResponseObject p :prendas) {
            response.addPrenda(p);
        }

        ResponseEntity res = new ResponseEntity<>(response, HttpStatus.OK);
        Gson gson = new GsonBuilder().registerTypeAdapter(res.getBody().getClass(), new PrendasJsonParser()).create();
        String json = gson.toJson(res.getBody());
        List<Prenda> prendas = PrendasJsonParser.JsonArrayPrendaListObject(json);
        return prendas;
    }
    public int getId(){return this.id;}
    public String getDesc(){return this.descripcion;}
}

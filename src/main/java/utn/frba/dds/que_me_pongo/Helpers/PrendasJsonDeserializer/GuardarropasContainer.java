package utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonParser;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.WebServices.Responses.GetPrendasResponse;
import utn.frba.dds.que_me_pongo.WebServices.Responses.ResponseObjects.PrendaResponseObject;

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
        Gson gson = new GsonBuilder().registerTypeAdapter(Prenda.class, new PrendasJsonParser()).create();
        String json = "{prendas: " + gson.toJson(prendas) + "}";
        List<Prenda> prendasRet = PrendasJsonParser.JsonArrayPrendaListObject(json);
        return prendasRet;
    }
    public int getId(){return this.id;}
    public String getDesc(){return this.descripcion;}
}

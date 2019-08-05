package utn.frba.dds.que_me_pongo.WebServices.Responses.ResponseObjects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.HashMap;

import utn.frba.dds.que_me_pongo.Helpers.Deserializer.PrendaDeserializer;
import utn.frba.dds.que_me_pongo.Helpers.Serializer.PrendaSerializer;
import utn.frba.dds.que_me_pongo.Model.Prenda;

@JsonDeserialize(using = PrendaDeserializer.class)
public class PrendaResponseObject extends Prenda {
    private String parteQueOcupa;

    public <T extends Prenda> PrendaResponseObject(T p, String parteQueOcupa) {
        super(p.getId(), p.getTipoDeTela(), p.getDescripcion(), p.getColorP(), p.getColorS(), p.getTipoDePrenda());
        this.parteQueOcupa = parteQueOcupa.split("utn.frba.dds.que_me_pongo.Model.TiposPrenda.")[1];
    }

   /* public PrendaResponseObject(){

    }*/

    public void setParteQueOcupa(String parteQueOcupa) {
        this.parteQueOcupa = parteQueOcupa;
    }

    public String getParteQueOcupa() {
        return parteQueOcupa;
    }

    private static <T extends Prenda> HashMap<String, Object> getPrendaMap(T p, String parteQueOcupa){
        HashMap<String,Object> prendaMap = new HashMap<>();
        PrendaDeserializer.getObjectAndParentField(p).forEach(
                field -> {
                    try{
                        prendaMap.put(field.getName(), field.get(p));
                    }catch (IllegalAccessException e){
                        e.printStackTrace();
                    }
                }
        );
        prendaMap.put("parteQueOcupa", parteQueOcupa);
        return prendaMap;
    }


}
package utn.frba.dds.que_me_pongo.Helpers.Deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import utn.frba.dds.que_me_pongo.Model.Prenda;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrendaDeserializer extends JsonDeserializer<Prenda> {


    @Override
    public Prenda deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    Prenda p = new Prenda();
        for (Field f :getObjectAndParentField(p)) {
            if(node.has(f.getName())) {
                f.setAccessible(true);
                JsonNode auxNode = node.get(f.getName());
                try{
                    if(auxNode.isInt()){
                        Integer num = auxNode.asInt();
                        f.set(p, num == 0 ? null : num);
                    }
                    if(auxNode.isTextual()){
                        String val = auxNode.asText();
                        f.set(p, val);
                    }
                }catch (IllegalAccessException e){
                    e.printStackTrace();
                    return  null;
                }
            }
        }
        p.setTipoDePrenda(node.get("parteQueOcupa").asText());
        return p;

    }

    public static List<Field> getObjectAndParentField(Object p){
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(p.getClass().getDeclaredFields()));
        fields.addAll(Arrays.asList(p.getClass().getSuperclass().getDeclaredFields()));
        return fields;
    }
}



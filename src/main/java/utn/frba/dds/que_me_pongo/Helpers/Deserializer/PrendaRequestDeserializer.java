package utn.frba.dds.que_me_pongo.Helpers.Deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import utn.frba.dds.que_me_pongo.Model.Prenda;

import java.io.IOException;
import java.lang.reflect.Field;

public class PrendaRequestDeserializer extends StdDeserializer<Prenda> {


    public PrendaRequestDeserializer() {
        this(null);
    }

    public PrendaRequestDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Prenda deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        try {
            Class<?> prendaClass = Class.forName("utn.frba.dds.que_me_pongo.Model.TiposPrenda." + node.get("parteQueOcupa").asText());
            Prenda p = (Prenda) prendaClass.newInstance();
            for (Field f :Prenda.class.getDeclaredFields()) {
                try {
                    if(node.has(f.getName())) {
                        f.setAccessible(true);
                        JsonNode auxNode = node.get(f.getName());
                        f.set(p, node.get(f.getName()).isInt() ?  auxNode.asInt(0) : auxNode.asText(""));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
            return p;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}



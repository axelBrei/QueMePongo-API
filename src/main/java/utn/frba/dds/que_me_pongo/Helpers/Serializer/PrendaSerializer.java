package utn.frba.dds.que_me_pongo.Helpers.Serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import utn.frba.dds.que_me_pongo.Helpers.Deserializer.PrendaDeserializer;
import utn.frba.dds.que_me_pongo.Model.Prenda;

public class PrendaSerializer extends JsonSerializer {

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<Field> objectFields = PrendaDeserializer.getObjectAndParentField(o);
        HashMap<String, Object> prendaMap = new HashMap<>();
        objectFields.forEach( field -> {
            try {
                if(!field.isAccessible())
                    field.setAccessible(true);
                prendaMap.put(field.getName(), field.get(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        prendaMap.put("parteQueOcupa", ((Prenda)o).getTipoDePrenda());
        jsonGenerator.writeObject(prendaMap);
    }
}

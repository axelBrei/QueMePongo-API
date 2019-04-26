package utn.frba.dds.que_me_pongo.Helpers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer.PrendasContainer;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer.SinglePrendaDeserializer;
import utn.frba.dds.que_me_pongo.Model.Prenda;


public class PrendasJsonParser implements JsonDeserializer<PrendasContainer>{

    @Override
    public PrendasContainer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
        Gson gson = new GsonBuilder().registerTypeAdapter(Prenda.class, new SinglePrendaDeserializer()).create();

        List<Prenda> prendas = new ArrayList<>();
        JsonObject arrayObject = json.getAsJsonObject();
        JsonArray array = arrayObject.getAsJsonArray("prendas");
        for (JsonElement item : array) {
            Prenda prenda = gson.fromJson(item, Prenda.class);
            prendas.add(prenda);
//            prendas.add(context.deserialize(item, valueType));
        }
        return new PrendasContainer(prendas);
    }



    public static List<Prenda> getJsonPrendasJson(){
        try{
            File file = new File("src/main/resources/data.json");
            InputStream stream = new FileInputStream(file);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            Gson gson = new GsonBuilder().registerTypeAdapter(PrendasContainer.class, new PrendasJsonParser()).create();
            String json = new String(buffer);
            PrendasContainer prendasContainer = gson.fromJson(json, PrendasContainer.class);
            stream.close();
            return prendasContainer.getPrendaslist();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Prenda> sendJsonPrenda(String prendas)  {
        try{
            File file = new File("src/main/resources/data.json");
            InputStream stream = new FileInputStream(file);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            Gson gson = new GsonBuilder().registerTypeAdapter(PrendasContainer.class, new PrendasJsonParser()).create();
            String json = new String(buffer);
            PrendasContainer prendasContainer = gson.fromJson(json, PrendasContainer.class);
            PrendasContainer nuevasPrendasContainer = gson.fromJson(prendas, PrendasContainer.class);
            stream.close();
            prendasContainer.addPrendaslist(nuevasPrendasContainer.getPrendaslist());



            return prendasContainer.getPrendaslist();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

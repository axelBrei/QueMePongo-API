package utn.frba.dds.que_me_pongo.Helpers;

import com.google.gson.Gson;
import utn.frba.dds.que_me_pongo.Helpers.PrendasJsonDeserializer.ClienteContainer;
import utn.frba.dds.que_me_pongo.Model.Cliente;

import java.io.*;

public class ClienteJsonParser {
    public static ClienteContainer  getCliente(String userName) throws IOException {
        //Se agrega el cliente en un nuevo json
        File file = new File("src/main/resources/"+userName+".json");
        InputStream stream = new FileInputStream(file);

        int size = stream.available();
        byte[] buffer = new byte[size];
        stream.read(buffer);
        String json = new String(buffer);
        Gson gson = new Gson();
        System.out.println(json);
        return gson.fromJson(json,ClienteContainer.class);
    }

    public static void newJsonCliente(ClienteContainer cliente) throws IOException {
        //Se agrega el cliente en un nuevo json
        FileWriter writeFile = new FileWriter("src/main/resources/"+cliente.getUserName()+".json");
        Gson gson = new Gson();
        writeFile.write(gson.toJson(cliente));
        writeFile.close();
    }

    public static void modifyNew(ClienteContainer cliente) throws IOException {
        //Se agrega el cliente en un nuevo json
        FileWriter writeFile = new FileWriter("src/main/resources/"+cliente.getUserName()+".json");
        Gson gson = new Gson();
        writeFile.write(gson.toJson(cliente));
        writeFile.close();
    }
}

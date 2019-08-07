package utn.frba.dds.que_me_pongo.Utilities.Helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

import utn.frba.dds.que_me_pongo.Model.Cliente;

import java.io.*;

public class ClienteJsonParser {
    public static Cliente  getCliente(String userId) throws IOException {
        File file = new File("src/main/resources/Clientes/"+userId+".json");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, Cliente.class);
    }

    public static void newJsonCliente(Cliente cliente) throws IOException {
        //Se agrega el cliente en un nuevo json
        FileWriter writeFile = new FileWriter("src/main/resources/Clientes/"+cliente.getUid()+".json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(writeFile, cliente);
    }

    public static void modifyNew(Cliente cliente) throws IOException {
        //Se agrega el cliente en un nuevo json
        FileWriter writeFile = new FileWriter("src/main/resources/Clientes/"+cliente.getUid()+".json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(writeFile, cliente);
    }
}

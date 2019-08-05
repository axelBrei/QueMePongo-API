package utn.frba.dds.que_me_pongo.WebServices.Responses.ResponseObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Guardarropa;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

public class GuardarropasCompartidosResponse {
    Guardarropa guardarropa;
    Set<Cliente> clientes = new HashSet<>();

    public GuardarropasCompartidosResponse(Guardarropa guardarropa) {
        this.guardarropa = guardarropa;
    }

    public void addCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }
}

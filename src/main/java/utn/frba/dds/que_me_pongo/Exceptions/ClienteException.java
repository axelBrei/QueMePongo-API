package utn.frba.dds.que_me_pongo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClienteException extends ResponseStatusException {

    public ClienteException(HttpStatus status, String tipo) {
        super(status, "El cliente ya es " + tipo.toLowerCase() + " .");
    }
}

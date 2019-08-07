package utn.frba.dds.que_me_pongo.Utilities.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GuardarropaNotFoundException extends ResponseStatusException {


    public GuardarropaNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public GuardarropaNotFoundException(HttpStatus status, int idGuardarropa) {
        super(status, "No se ha encontado el guardarropa con id: " + idGuardarropa);
    }
}

package utn.frba.dds.que_me_pongo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PrendaNotFoundException extends ResponseStatusException {

    public PrendaNotFoundException(HttpStatus status) {
        super(status, "No se ha encontrado la prenda");
    }
}

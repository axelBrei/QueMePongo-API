package utn.frba.dds.que_me_pongo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GuardarropaPrendasException extends ResponseStatusException {


    public GuardarropaPrendasException(HttpStatus status) {
        super(status, "El guardarropa no posee atuendos para esta ocacion.");
    }
}

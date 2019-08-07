package utn.frba.dds.que_me_pongo.Utilities.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EventoNotFoundException extends ResponseStatusException {

    public EventoNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }
}

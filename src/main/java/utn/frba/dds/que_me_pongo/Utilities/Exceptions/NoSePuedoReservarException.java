package utn.frba.dds.que_me_pongo.Utilities.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSePuedoReservarException extends ResponseStatusException {


    public NoSePuedoReservarException(HttpStatus status) {
        super(status, "El atuendo no se pudo reservar para esa fecha.");
    }
}


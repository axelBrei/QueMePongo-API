package utn.frba.dds.que_me_pongo.Utilities.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSeEcuentraEvento extends ResponseStatusException {


    public NoSeEcuentraEvento(HttpStatus status) {
        super(status, "El no se encuentra el evento");
    }
}


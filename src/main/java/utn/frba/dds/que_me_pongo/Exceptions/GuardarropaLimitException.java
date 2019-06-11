package utn.frba.dds.que_me_pongo.Exceptions;

import jdk.nashorn.internal.runtime.NumberToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GuardarropaLimitException extends ResponseStatusException {


    public GuardarropaLimitException(HttpStatus status, String desc ) {
        super(status, "El guardarropa " + desc + " esta lleno. -HAZTE PREMIUM-");
    }
}

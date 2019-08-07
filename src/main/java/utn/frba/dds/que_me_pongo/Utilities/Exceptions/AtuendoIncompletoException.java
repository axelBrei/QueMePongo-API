package utn.frba.dds.que_me_pongo.Utilities.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AtuendoIncompletoException extends ResponseStatusException {

    public AtuendoIncompletoException(HttpStatus status, String classMissing) {
        super(status, "El atuendo esta incompleto, no se ha encontrado un " + classMissing.toLowerCase() + " en su guardarropa");
    }

    public AtuendoIncompletoException(HttpStatus status, String classMissing, String nivel) {
        super(status, "El atuendo esta incompleto, no se ha encontrado un " + classMissing.toLowerCase() + " en su guardarropa"+nivel+".");
    }
}

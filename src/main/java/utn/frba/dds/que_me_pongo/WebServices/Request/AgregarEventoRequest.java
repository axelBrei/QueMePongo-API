package utn.frba.dds.que_me_pongo.WebServices.Request;

import lombok.Data;
import lombok.NoArgsConstructor;
import utn.frba.dds.que_me_pongo.Model.Evento;

@Data
@NoArgsConstructor
public class AgregarEventoRequest {
    String uid;
    Evento evento;
}

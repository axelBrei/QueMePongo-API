package utn.frba.dds.que_me_pongo.WebServices.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteReservaRequest {
    String uid;
    Long idEvento;
}

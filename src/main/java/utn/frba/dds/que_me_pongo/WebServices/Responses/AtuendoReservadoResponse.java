package utn.frba.dds.que_me_pongo.WebServices.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Reserva;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class AtuendoReservadoResponse {
    private String uid;
    private Reserva reserva;

}

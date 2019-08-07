package utn.frba.dds.que_me_pongo.Helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class PrendasReservadas {
    Long prendas_id;
    Date desde;
    Date hasta;
}

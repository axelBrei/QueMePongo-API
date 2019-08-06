package utn.frba.dds.que_me_pongo.Model;

import java.util.Date;



import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrendasReservadas {
    long prendas_id;
    Date desde;
    Date hasta;
}

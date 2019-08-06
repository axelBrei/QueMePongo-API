package utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Guardarropa;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompartirGuardarropaRequest {

    String uid;
    Integer idGuardarropa;
    String uidDestino;
}

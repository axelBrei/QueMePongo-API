package utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Prenda;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeletePrendaRequest {
    String uid;
    Integer idGuardarropa;
    Integer idPrenda;

    public String getUid() {
        return uid;
    }

    public Integer getIdGuardarropa() {
        return idGuardarropa;
    }

    public Integer getIdPrenda() {
        return idPrenda;
    }
}

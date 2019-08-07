package utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.Prenda;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.frba.dds.que_me_pongo.Model.Prenda;

@Data
@NoArgsConstructor
@Setter
@Getter
public class NuevaPrendaRequest {
    private String uid;
    private String idGuardarropa;
    private Prenda prenda;

}

package utn.frba.dds.que_me_pongo.WebServices.Request.Prenda;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.frba.dds.que_me_pongo.Helpers.Deserializer.PrendaDeserializer;
import utn.frba.dds.que_me_pongo.Model.Prenda;

@Data
@NoArgsConstructor
@Setter
@Getter
public class NuevaPrendaRequest {
    private String uid;
    private String idGuardarropa;

    @JsonDeserialize(using = PrendaDeserializer.class)
    private Prenda prenda;

}

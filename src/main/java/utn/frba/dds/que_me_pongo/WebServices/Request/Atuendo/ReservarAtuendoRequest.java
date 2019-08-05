package utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.AtuendoReservado;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReservarAtuendoRequest {
    private String uid;
    private Integer idGuardarropa;
    private Atuendo atuendo;
    private Date desde;
    private Date hasta;

    public Integer getIdGuardarropa() {
        return idGuardarropa;
    }
}

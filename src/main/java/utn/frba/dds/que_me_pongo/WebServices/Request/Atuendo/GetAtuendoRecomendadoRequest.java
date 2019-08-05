package utn.frba.dds.que_me_pongo.WebServices.Request.Atuendo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utn.frba.dds.que_me_pongo.Model.Atuendo;


@Getter
@Setter
@NoArgsConstructor
public class GetAtuendoRecomendadoRequest {
    private String uid;
    private Integer idGuardarropa;
    private Atuendo atuendo;


}

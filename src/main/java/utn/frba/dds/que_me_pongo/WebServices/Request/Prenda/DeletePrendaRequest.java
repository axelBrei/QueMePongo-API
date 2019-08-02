package utn.frba.dds.que_me_pongo.WebServices.Request.Prenda;

import org.springframework.data.annotation.AccessType;

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
}

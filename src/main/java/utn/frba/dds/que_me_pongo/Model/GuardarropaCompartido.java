package utn.frba.dds.que_me_pongo.Model;

import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class GuardarropaCompartido {
    String uidCompartido;
    Integer idGuardarropa;
    String nombreGuardarropa;
    String nombreCompartido;
}

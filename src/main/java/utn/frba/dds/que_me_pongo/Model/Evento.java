package utn.frba.dds.que_me_pongo.Model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Eventos")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Transactional
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    private String uidEvento;
    private String nombre;
    private Date desde;
    private Date hasta;
    private float latitud;
    private float longitud;
}

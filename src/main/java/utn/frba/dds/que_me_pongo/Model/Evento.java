package utn.frba.dds.que_me_pongo.Model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;
import utn.frba.dds.que_me_pongo.Utilities.Helpers.DateHelper;

import javax.persistence.*;
import java.util.Date;

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

    // Por si google genera un id para el evento
    String uidEvento;
    String nombre;
    Date desde;
    Date hasta;
    Double latitud;
    Double longitud;
    String frecuencia;
    /*FORMAL O INFORMAL*/
    String formalidad;
    Boolean notificado = false;

    @OneToOne(optional = true)
    Atuendo atuendo;

    @Override
    public boolean equals(Object obj) {
        Evento evento = (Evento) obj;
        return this.id == evento.getId();
    }



    public boolean tieneReserva(){return atuendo!=null;}

}

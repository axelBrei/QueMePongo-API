package utn.frba.dds.que_me_pongo.Model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "Eventos")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Evento implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    // Por si google genera un id para el evento
    String uidEvento;
    String nombre;
    Date desde;
    Date hasta;
    String nombreUbicacion;
    Double latitud;
    Double longitud;
    String frecuencia;
    /*FORMAL O INFORMAL*/
    String formalidad;
    Boolean notificado = false;

    @OneToOne(optional = true)
    Atuendo atuendo;

    Integer id_guardarropa;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Atuendo.class)
    Set<Atuendo> generados = new HashSet<>();

    @Override
    public boolean equals(Object obj) {
        Evento evento = (Evento) obj;
        return evento.getId().equals(this.id);
    }

    public boolean tieneReserva(){return atuendo!=null;}


    @Override
    public Evento clone() throws CloneNotSupportedException {
        return (Evento) super.clone();
    }
}


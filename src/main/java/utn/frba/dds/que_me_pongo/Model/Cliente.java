package utn.frba.dds.que_me_pongo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import utn.frba.dds.que_me_pongo.Utilities.Exceptions.*;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Clientes")
@FieldDefaults( level = AccessLevel.PRIVATE)
@Setter
@Getter
@NoArgsConstructor
public class Cliente  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String uid;
    String mail;
    String firebaseToken;

    @OneToOne(targetEntity = TipoCliente.class)
    TipoCliente tipoCliente;

    @JsonProperty("nombre")
    String name;

    @ManyToMany
    Set<Guardarropa> guardarropas = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Evento.class)
    List<Evento> eventos;


    public Cliente(String uid, String mail, String name) {
        this.uid = uid;
        this.mail = mail;
        this.name = name;
        tipoCliente = new TipoCliente().setTipoClienteGratuito();
    }
    public void addGuardarropa(Guardarropa g){
        this.guardarropas.add(g);
    }

    public Guardarropa getGuardarropa(int id) throws GuardarropaNotFoundException {
        Optional<Guardarropa> guardarropaOptional = guardarropas.stream().filter( guardarropa -> guardarropa.getId() == id).findFirst();
        return guardarropaOptional.orElseThrow( () ->
            new GuardarropaNotFoundException(HttpStatus.NOT_FOUND,id)
        );
    }

    public void deleteGuardarropa(int id) throws GuardarropaNotFoundException {
        Optional<Guardarropa> guardarropaOptional = guardarropas.stream().filter( guardarropa -> guardarropa.getId() == id).findFirst();
        Guardarropa g = guardarropaOptional.orElseThrow( () ->
                new GuardarropaNotFoundException(HttpStatus.NOT_FOUND,id)
        );
        guardarropas.remove(g);
    }

    public Evento getEvento(long id){
        return this.getEventos()
                    .stream()
                    .filter(e -> e.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new NoSeEcuentraEvento(HttpStatus.NOT_FOUND));
    }
}


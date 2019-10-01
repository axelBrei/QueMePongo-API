package utn.frba.dds.que_me_pongo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import utn.frba.dds.que_me_pongo.Controller.AtuendoController;
import utn.frba.dds.que_me_pongo.Utilities.Exceptions.GuardarropaNotFoundException;
import utn.frba.dds.que_me_pongo.Utilities.Exceptions.LimiteGuardarropaException;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        this.setUid(uid);
        this.setMail(mail);
        this.setName(name);
        setTipoCliente(new TipoCliente().setTipoClienteGratuito());
    }

    public void addGuardarropa(Guardarropa g){
        this.getGuardarropas().add(g);
    }

    public void addPrendaToGuardarropa(Guardarropa g,Prenda prenda) throws LimiteGuardarropaException{
        if (tipoCliente.getPrendasMax() == g.getPrendas().size()) {
            throw new LimiteGuardarropaException("Limite alcanzado, Hazte Premium");
        }
        else{
            g.addPrenda(prenda);
        }

    }

    public Guardarropa getGuardarropa(int id) throws GuardarropaNotFoundException {
        Optional<Guardarropa> guardarropaOptional = getGuardarropas().stream().filter(guardarropa -> guardarropa.getId() == id).findFirst();
        return guardarropaOptional.orElseThrow( () ->
            new GuardarropaNotFoundException(HttpStatus.NOT_FOUND,id)
        );
    }

    public void deleteGuardarropa(int id) throws GuardarropaNotFoundException {
        Optional<Guardarropa> guardarropaOptional = getGuardarropas().stream().filter(guardarropa -> guardarropa.getId() == id).findFirst();
        Guardarropa g = guardarropaOptional.orElseThrow( () ->
                new GuardarropaNotFoundException(HttpStatus.NOT_FOUND,id)
        );
        getGuardarropas().remove(g);
    }

    public Set<Atuendo> generarAtuendoParaEvento(Evento evento,Guardarropa guardarropa){
        AtuendoController atuendoController = new AtuendoController();
       return  atuendoController.getAtuendo(guardarropa, evento);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Guardarropa> getGuardarropas() {
        return guardarropas;
    }

    public void setGuardarropas(Set<Guardarropa> guardarropas) {
        this.guardarropas = guardarropas;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
}


package utn.frba.dds.que_me_pongo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import utn.frba.dds.que_me_pongo.Utilities.Exceptions.GuardarropaLimitException;
import utn.frba.dds.que_me_pongo.Utilities.Exceptions.GuardarropaNotFoundException;
import utn.frba.dds.que_me_pongo.Utilities.Exceptions.PrendaNotFoundException;

import java.io.Serializable;
import java.util.HashSet;
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
    @Column(name = "mail")
    String mail;

    @JsonProperty("tipoCliente")
    @OneToOne(targetEntity = TipoCliente.class)
    TipoCliente tipoCliente;

    @JsonProperty("nombre")
    String name;

    @ManyToMany
    Set<Guardarropa> guardarropas = new HashSet<>();

    public Cliente(String uid, String mail, String name,TipoCliente tipoCliente) {
        this.uid = uid;
        this.mail = mail;
        this.name = name;
        this.tipoCliente = tipoCliente;
    }

    public Cliente(String uid, String mail, String name) {
        this.uid = uid;
        this.mail = mail;
        this.name = name;
        tipoCliente = new TipoCliente().setTipoClienteGratuito();
    }

    public Cliente(String mail, String name) {
        this.mail = mail;
        this.name = name;
        tipoCliente = new TipoCliente().setTipoClienteGratuito();
    }

    public void addGuardarropa(Guardarropa g){
        this.guardarropas.add(g);
    }

    public Boolean puedeAnadirPrenda(Guardarropa guardarropa){
        if(new TipoCliente().esGratuito(this.getTipoCliente()) &&
                guardarropa.getPrendas().size() >= new TipoCliente().setTipoClienteGratuito().getPrendasMax()){

                        throw new GuardarropaLimitException(HttpStatus.NOT_FOUND,guardarropa.getDescripcion());
        }

        return true;
    }

    public <T extends Prenda> void anadirPrendaAlGuardarropa(T prenda, int id) throws GuardarropaNotFoundException {

        Optional<Guardarropa> guardarropaOptional = guardarropas.stream().filter( guardarropa -> guardarropa.getId() == id).findFirst();
        if(puedeAnadirPrenda(guardarropaOptional.get())) {
            Guardarropa g = guardarropaOptional.orElseThrow(() ->
                    new GuardarropaNotFoundException(HttpStatus.NOT_FOUND, id)
            );
            g.addPrenda(prenda);
        }
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

    public void deletePrendaDelGuardarropa(Prenda prenda,int id) throws GuardarropaNotFoundException, PrendaNotFoundException{
        Optional<Guardarropa> guardarropaOptional = guardarropas.stream().filter(guardarropa -> guardarropa.getId() == id).findFirst();
        Guardarropa g = guardarropaOptional.orElseThrow( () ->
                new GuardarropaNotFoundException(HttpStatus.NOT_FOUND,id)
        );
        if(!g.deletePrenda(prenda))
            throw new PrendaNotFoundException(HttpStatus.NOT_FOUND);
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

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente= tipoCliente;
    }



    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


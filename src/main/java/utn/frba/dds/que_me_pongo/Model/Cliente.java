package utn.frba.dds.que_me_pongo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import utn.frba.dds.que_me_pongo.Exceptions.GuardarropaLimitException;
import utn.frba.dds.que_me_pongo.Exceptions.GuardarropaNotFoundException;
import utn.frba.dds.que_me_pongo.Exceptions.PrendaNotFoundException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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


    @ElementCollection(targetClass = Reserva.class)
    Set<Reserva> reservas = new HashSet<>();

    public Cliente(String uid, String mail, String name,TipoCliente tipoCliente) {
        this.uid = uid;
        this.mail = mail;
        this.name = name;
        this.tipoCliente = tipoCliente;
    }

    public Set<Reserva> getReservas(){
        return this.reservas;
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

    public void addReserva(Reserva r){
        this.reservas.add(r);
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

    /*
        Reservar atuendo: reserva cada prenda de el atuendo , agergandola a el Set de reservas de el cliente.
        Sigue en el ReservaController
     */
    public void reservarAtuendo(Atuendo a,Date desde,Date hasta){
        a.getPrendas().forEach(p -> addReserva(new Reserva(a,p,desde,hasta)));
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


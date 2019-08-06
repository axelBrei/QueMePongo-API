package utn.frba.dds.que_me_pongo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;
import utn.frba.dds.que_me_pongo.WebServices.Responses.AtuendoReservadoResponse;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "Reservas")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional
@NoArgsConstructor
public class Reserva  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @JsonProperty("prenda")
    @OneToOne(optional=true,targetEntity = Prenda.class)
    private Prenda prenda ;

    @JsonProperty("atuendo")
    @OneToOne(optional=true)
    private Atuendo atuendo ;

    private Date desde;
    private Date hasta;

    public Reserva(Atuendo atuendo,Prenda prenda, Date desde, Date hasta) {
        this.prenda = prenda;
        this.desde = desde;
        this.hasta = hasta;
        this.atuendo = atuendo;
    }

    public Set<AtuendoReservadoResponse> atuendosReservados(Set<Reserva> reservas){
        Set<AtuendoReservadoResponse> atuendosReservados = new HashSet<>();
        reservas.stream().forEach( r -> {
            AtuendoReservadoResponse nuevo = new AtuendoReservadoResponse(r.getDesde(),r.getHasta(),r.getAtuendo());
            if(atuendosReservados.stream().noneMatch( b -> b.isEqual(nuevo))){
                atuendosReservados.add(nuevo);
            }
        });
        return  atuendosReservados;
    }

    public Boolean isEqual(Prenda p , Date desde , Date hasta){
        if(p.getId()==this.getPrenda().getId()
                && desde.getTime() == this.getDesde().getTime()
                    && hasta.getTime() == this.getHasta().getTime()){
            return true;
        }
        return false;
    }

    public Boolean noEstaReservada(Date desde,Date hasta){
        return ( (desde.getTime() < this.getDesde().getTime() && hasta.getTime() < this.getDesde().getTime() ) ||
                (desde.getTime() > this.getHasta().getTime() && hasta.getTime() > this.getHasta().getTime() ));
    }

}

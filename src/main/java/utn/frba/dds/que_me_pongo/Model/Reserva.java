package utn.frba.dds.que_me_pongo.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import utn.frba.dds.que_me_pongo.WebServices.Responses.AtuendoReservadoResponse;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


@Data
@Entity
@Table(name = "Reservas")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Reserva  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @JsonProperty("prenda")
    @OneToOne(targetEntity = Prenda.class)
    private Prenda prenda ;

    @JsonProperty("atuendo")
    @OneToOne(targetEntity = Atuendo.class)
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

}

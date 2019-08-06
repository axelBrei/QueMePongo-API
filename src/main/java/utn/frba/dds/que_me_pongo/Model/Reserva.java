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
/*
    CONCEPTO:
    Esta es una nueva clase , que relaciona una prenda de un atuendo para ser reservado en un intervalo de tiempo
    (No lo hago con el atuendo porque con la entidad reserva atomaticamente sabes si una prenda esta reservada para tal horario
    en cambio si reservas atuendos, tenes que ir atuendo y de ahi a las prendas que los componen para saber si hay una prenda libre)
    Por lo tanto esto es como mesclar las dos cosas facilita saber el tiempo el atuendo y la prenda que fue reservada
    Es decir por un atuendo de 3 prendas se generan 3 reservas una por cada prenda, con el mismo atuendo, y con el mismo desde y hasta
    TABLA : ID , DESDE, HASTA, PRENDA_ID, ATUENDO_ID
    Un cliente tiene un Set de reservas . Que relacionan cada reserva con su cliente (Se puede saber quien reservo tal prenda)
    TABLA : ID ,CLIENTE_ID

    Sigue en clase Cliente -> method reservarAtuendo();
 */
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
            /*
            * Toma de la lista de reservas los atuendos sin repetir
            * (Dado que hay tantas reservas por atuendo como prendas tenga el mismo)
            * */
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

package utn.frba.dds.que_me_pongo.Model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;

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

   public  Evento(String uid,String nombre,Date desde,Date hasta,Double latitud, Double longitud,String frecuencia,String formalidad){
        this.uidEvento = uid;
        this.nombre = nombre;
        this.desde = desde;
        this.hasta = hasta;
        this.latitud = latitud;
        this.longitud = longitud;
        this.frecuencia = frecuencia;
        this.formalidad = formalidad;
    }


    @OneToOne(optional = true)
    Atuendo atuendo;

    @Override
    public boolean equals(Object obj) {
        Evento evento = (Evento) obj;
        return this.getId() == evento.getId();
    }

    public boolean tieneReserva(){return getAtuendo() !=null;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUidEvento() {
        return uidEvento;
    }

    public void setUidEvento(String uidEvento) {
        this.uidEvento = uidEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getFormalidad() {
        return formalidad;
    }

    public void setFormalidad(String formalidad) {
        this.formalidad = formalidad;
    }

    public Boolean getNotificado() {
        return notificado;
    }

    public void setNotificado(Boolean notificado) {
        this.notificado = notificado;
    }

    public Atuendo getAtuendo() {
        return atuendo;
    }

    public void setAtuendo(Atuendo atuendo) {
        this.atuendo = atuendo;
    }
}

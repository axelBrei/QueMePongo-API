package utn.frba.dds.que_me_pongo.Repository;

import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Reserva;
import utn.frba.dds.que_me_pongo.WebServices.Responses.AtuendoReservadoResponse;

import java.util.Date;

public interface ReservaPrendaRepository {
    Reserva addReservaToCliente(Cliente c, Reserva g);
    Atuendo addReservaAtuendoToCliente(Cliente c, Atuendo g, Date desde, Date hasta);
    boolean sePuedeReservar(Atuendo g, Date desde, Date hasta);
    boolean deleteReserva(Cliente c, Reserva r);
    boolean removeReservaDelCliente(Cliente c, AtuendoReservadoResponse id);
}

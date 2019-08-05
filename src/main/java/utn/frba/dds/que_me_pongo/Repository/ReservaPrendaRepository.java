package utn.frba.dds.que_me_pongo.Repository;

import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Reserva;

import java.util.Date;

public interface ReservaPrendaRepository {
    Reserva addReservaToCliente(Cliente c, Reserva g);
    Atuendo addReservaAtuendoToCliente(Cliente c, Atuendo g, Date desde, Date hasta);
    boolean removeReservaDelCliente(Cliente c, int id);
}

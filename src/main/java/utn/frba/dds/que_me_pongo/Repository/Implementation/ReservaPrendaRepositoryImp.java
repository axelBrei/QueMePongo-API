package utn.frba.dds.que_me_pongo.Repository.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Reserva;
import utn.frba.dds.que_me_pongo.Repository.*;

import java.util.Date;

@Repository
public class ReservaPrendaRepositoryImp implements ReservaPrendaRepository {
    @Autowired
    ReservasRepository reservaRepository;

    @Autowired
    ClientesRepository clientesRepository;

    @Override
    public Reserva addReservaToCliente(Cliente c, Reserva r) {
        r = reservaRepository.save(r);
        c.addReserva(r);
        clientesRepository.save(c);
        return r;
    }

    @Override
    public Atuendo addReservaAtuendoToCliente(Cliente c, Atuendo a, Date desde, Date hasta) {
        a.getPrendas().forEach(p-> addReservaToCliente(c,new Reserva(a,p,desde,hasta)));
        return  a;
    }

    @Override
    public boolean removeReservaDelCliente(Cliente c, int id) {
        return false;
    }
}

package utn.frba.dds.que_me_pongo.Repository.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Cliente;
import utn.frba.dds.que_me_pongo.Model.Prenda;
import utn.frba.dds.que_me_pongo.Model.Reserva;
import utn.frba.dds.que_me_pongo.Repository.AtuendoRepository;
import utn.frba.dds.que_me_pongo.Repository.ClientesRepository;
import utn.frba.dds.que_me_pongo.Repository.ReservaPrendaRepository;
import utn.frba.dds.que_me_pongo.Repository.ReservasRepository;
import utn.frba.dds.que_me_pongo.WebServices.Responses.AtuendoReservadoResponse;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class ReservaPrendaRepositoryImp implements ReservaPrendaRepository {
    @Autowired
    ReservasRepository reservaRepository;

    @Autowired
    AtuendoRepository atuendoRepository;

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
    public Atuendo addReservaAtuendoToCliente(Cliente c, Atuendo atuendo, Date desde, Date hasta) {
        try {
            Atuendo a = atuendoRepository.getAtuendoById(atuendo.getId());
            a.getPrendas().forEach(p -> addReservaToCliente(c, new Reserva(a, p, desde, hasta)));
            return a;
        }catch (NullPointerException e){
            throw new ResponseStatusException( HttpStatus.OK,"No existe el atuendo");
        }
    }

    @Override
    public boolean sePuedeReservar(Atuendo atuendo, Date desde, Date hasta) {
        try {
            Atuendo a = atuendoRepository.getAtuendoById(atuendo.getId());
            a.getPrendas().forEach(p -> {
                Set<Reserva> reservas =  reservaRepository.findAllByPrendaId(p.getId());
                if(reservas.stream().anyMatch(reserva -> !reserva.noEstaReservada(desde,hasta)) == true){
                    throw new ResponseStatusException( HttpStatus.OK,"Ya esta reservada la prenda "+p.getDescripcion());
                }
            });
            return true;
        }catch (NullPointerException e){
            throw new ResponseStatusException( HttpStatus.OK,"No existe el atuendo");
        }
    }

    @Override
    @Transactional
    public boolean removeReservaDelCliente(Cliente cliente, AtuendoReservadoResponse res) {
        List<Prenda> prendas = res.getAtuendo().getPrendas();
        Set<Reserva> reservas = new HashSet<>();

        prendas.forEach(p->{
            cliente.getReservas().forEach(reserva ->{
                if(reserva.isEqual(p,res.getDesde(),res.getHasta())) {
                    reservas.add(reserva);
                }
        });});
        prendas.forEach(p->{
            cliente.getReservas().removeIf(reserva -> reserva.isEqual(p,res.getDesde(),res.getHasta()));
        });
        clientesRepository.save(cliente);

        reservas.forEach(r->{
            reservaRepository.delete(r);
        });

        return  true;
    }
}

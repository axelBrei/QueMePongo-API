package utn.frba.dds.que_me_pongo.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import utn.frba.dds.que_me_pongo.Helpers.PrendasReservadas;

import java.util.List;

public interface PrendaReservadaRespository {

    List<PrendasReservadas> prendasReservadasList();
}

package utn.frba.dds.que_me_pongo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import utn.frba.dds.que_me_pongo.Model.Guardarropa;

@Repository
public interface GuardarropasRepository extends JpaRepository<Guardarropa, Integer> {

}

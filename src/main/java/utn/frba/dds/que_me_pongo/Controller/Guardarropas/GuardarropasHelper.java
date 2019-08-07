package utn.frba.dds.que_me_pongo.Controller.Guardarropas;

import org.springframework.stereotype.Service;

import utn.frba.dds.que_me_pongo.Model.Cliente;

@Service
public class GuardarropasHelper {

     public static Boolean puedeCompartir(Cliente cliente, Integer idGuardarropa){
         return cliente.getUid().equals(cliente.getGuardarropa(idGuardarropa).getUidDueno());
     }

     public static Boolean clientesDelMismoRangoSocial(Cliente cli1, Cliente cli2){
         return  cli1.getTipoCliente().equals(cli2.getTipoCliente());
     }
}

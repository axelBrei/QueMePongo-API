package utn.frba.dds.que_me_pongo.Utilities.Helpers.RecomendacionDeAtuendos;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import utn.frba.dds.que_me_pongo.Model.Atuendo;
import utn.frba.dds.que_me_pongo.Model.Prenda;

public class FiltroTamaño {

    public static Predicate<? super Atuendo> filtroPorTamano(){
        return atuendo -> {
            if(atuendo.getPrendas().size() < 3)
                return false;

            Map<String, List<Prenda>> prendasSeparadas = atuendo.getPrendas().stream().collect(Collectors.groupingBy(Prenda::getTipoDePrenda));
            for (List<Prenda> pr: prendasSeparadas.values()) {
                if(pr.size() > 3)
                    return false;
            }
            if(FIltroForma.isComposicionAuendoCorrecta(prendasSeparadas)){
                return FIltroForma.noSuperposicion(prendasSeparadas);
            }
            return false;
        };
    }
}

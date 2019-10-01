package utn.frba.dds.que_me_pongo.Helpers;

import java.util.HashMap;
import java.util.Map;

import utn.frba.dds.que_me_pongo.Controller.ClimaAPIs.ClimaApiUNO;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Services.ClimaService;

public class ClimeHelper {
    public static final String MIN_ABRIGO = "MINIMO_ABRIGO";
    public static final String MAX_ABRIGO = "MAXIMO_ABRIGO";
    private static final Double MAX_ABRIGO_D = 550.0;
    private static final Double MIN_ABRIGO_D = 100.0;

    // Cuato mas grande la temperatura mas chico tiene q ser el rango de abrigo

    public static Map<String, Double> getDeltaAbrigo(float temp) {
        Double abrigoOriginal = (45.0 - temp) * 10;
        Map<String, Double> delta = new HashMap<>();
        delta.put(MIN_ABRIGO, Math.max((abrigoOriginal - 50.0), MIN_ABRIGO_D));
        delta.put(MAX_ABRIGO, Math.min( (abrigoOriginal + 50.0) , MAX_ABRIGO_D));
        return delta;
    }


    public static Map<String, Double> getClimaParaEvento(Evento evento) {
        if(evento.getLatitud()==0.0&&evento.getLongitud()==0.0){
            return getDeltaAbrigo(24.0f);
        }
       ClimaService climaService = new ClimaApiUNO();
       return getDeltaAbrigo(climaService.getTemperatura(evento));
    }
}

package utn.frba.dds.que_me_pongo.Model.ClimaAPIs;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import utn.frba.dds.que_me_pongo.Helpers.ClimaJsonDeserializer.ResponseWeather;
import utn.frba.dds.que_me_pongo.Model.ClimaService;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.WebServices.ClimaRequest.RequestOpenWeather;


public class ClimaApiUNO implements ClimaService {


    @Override
    public float getTemperatura(Evento evento) {

        RequestOpenWeather openWeather = new RequestOpenWeather();

        String latitud = String.format ("% .2f", evento.getUbicacion().getLatitud());
        String longitud = String.format ("% .2f", evento.getUbicacion().getLongitud());

        ResponseWeather response = openWeather.getWeather(latitud,longitud);

        return (float) kelvinToC(response.getDataNow().getTemp());
    }

    private float kelvinToC(float kelvin){
        float cero = (float) 273.15;
        return (kelvin-cero);
    }


}

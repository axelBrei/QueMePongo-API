package utn.frba.dds.que_me_pongo.Model.ClimaAPIs;

import com.google.gson.Gson;
import com.sun.tools.javac.util.List;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import utn.frba.dds.que_me_pongo.Helpers.PronosticoClassOpenWeather.Clima;
import utn.frba.dds.que_me_pongo.Helpers.PronosticoClassOpenWeather.ResponseWeather;
import utn.frba.dds.que_me_pongo.Helpers.PronosticoClassOpenWeather.Sys;
import utn.frba.dds.que_me_pongo.Model.ClimaService;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.WebServices.ClimaRequest.RequestOpenWeather;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.stream.Collectors;


public class ClimaApiUNO implements ClimaService {


    @Override
    public float getTemperatura(Evento evento) {

        RequestOpenWeather openWeather = new RequestOpenWeather();

        String latitud = String.format ("% .2f", evento.getUbicacion().getLatitud());
        String longitud = String.format ("% .2f", evento.getUbicacion().getLongitud());

        ResponseWeather response = openWeather.getWeather(latitud,longitud);

        //response.getClimaList().get(0).getMain().getTemp();
        getClimaDate(response,evento.getDate());

        return (float) kelvinToC(response.getClimaList().get(0).getMain().getTemp());
    }

    private float kelvinToC(float kelvin){
        float cero = (float) 273.15;
        return (kelvin-cero);
    }

    private Clima getClimaDate(ResponseWeather responseWeather, Date date){

        responseWeather.getClimaList().removeIf(w -> Math.abs(w.getDate().getTime()-date.getTime())/1000 > 60*60*1.5 ) ;

        return responseWeather.getClimaList().get(0);
    }

}

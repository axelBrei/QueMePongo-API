package utn.frba.dds.que_me_pongo.Controller.ClimaAPIs;

import utn.frba.dds.que_me_pongo.Helpers.PronosticoClassOpenWeather.Clima;
import utn.frba.dds.que_me_pongo.Helpers.PronosticoClassOpenWeather.ResponseWeather;
import utn.frba.dds.que_me_pongo.Model.ClimaService;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.WebServices.Request.ClimaRequest.RequestOpenWeather;

import java.util.Date;


public class ClimaApiUNO implements ClimaService {


    @Override
    public float getTemperatura(Evento evento) {

        RequestOpenWeather openWeather = new RequestOpenWeather();

        String latitud = String.format ("% .2f", evento.getUbicacion().getLatitud());
        String longitud = String.format ("% .2f", evento.getUbicacion().getLongitud());

        ResponseWeather response = openWeather.getWeather(latitud,longitud);
        return (float) kelvinToC( getClimaDate(response,evento.getDate()).getMain().getTemp());
    }

    private float kelvinToC(float kelvin){
        float cero = (float) 273.15;
        return (kelvin-cero);
    }

    private Clima getClimaDate(ResponseWeather responseWeather, Date date){
        responseWeather.getClimaList().removeIf(w -> Math.abs(w.getDate().getTime()-date.getTime()-(60*60*1.5*1000))/1000 > 60*60*1.5 ) ;


        return responseWeather.getClimaList().get(0);
    }

}

package utn.frba.dds.que_me_pongo.Controller.ClimaAPIs;

import utn.frba.dds.que_me_pongo.Helpers.PronosticoClassDarkSkyWeather.DarkSkyResponse;
import utn.frba.dds.que_me_pongo.Helpers.PronosticoClassDarkSkyWeather.Weather;
import utn.frba.dds.que_me_pongo.Model.ClimaService;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.WebServices.Request.ClimaRequest.RequestDarkSkyWeather;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class ClimaApiDOS implements ClimaService {
    @Override
    public float getTemperatura(Evento evento) {

        RequestDarkSkyWeather openWeather = new RequestDarkSkyWeather();

        String latitud = String.format ("% .2f", evento.getUbicacion().getLatitud());
        String longitud = String.format ("% .2f", evento.getUbicacion().getLongitud());

        DarkSkyResponse response = openWeather.getWeather(latitud,longitud);

        getClimaDate(response,evento.getDate());

        return (float) fahrenheitToC(getClimaDate(response,evento.getDate()).getTemperature());
    }

    private float kelvinToC(float kelvin){
        float cero = (float) 273.15;
        return (kelvin-cero);
    }

    private float fahrenheitToC(float f){
        return  ((f - 32)*5)/9;
    }

    private Weather getClimaDate(DarkSkyResponse responseWeather, Date date){

        ArrayList<Weather> list = responseWeather.getClimaList();

        Iterator<Weather> iterator = list.iterator();
        Weather masCercano = list.get(0) ;
        Weather uno ;
        while (iterator.hasNext()){
            uno = iterator.next();
            if(Math.abs(uno.getTime()-date.getTime()) <= Math.abs(masCercano.getTime()-date.getTime())){
                masCercano=uno;
            }

        }
        return masCercano;
    }


}



package utn.frba.dds.que_me_pongo.Controller.ClimaAPIs;

import utn.frba.dds.que_me_pongo.WebServices.PronosticoClassDarkSkyWeather.DarkSkyResponse;
import utn.frba.dds.que_me_pongo.WebServices.PronosticoClassDarkSkyWeather.Weather;
import utn.frba.dds.que_me_pongo.Model.ClimaService;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.WebServices.Request.ClimaRequest.RequestDarkSkyWeather;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class ClimaApiDOS implements ClimaService {
    @Override
    public float getTemperatura(Evento evento) throws NullPointerException  {
        Date now = new Date();
        Date cincoDias = new Date();
        cincoDias.setTime(now.getTime() + 5*24*60*60*1000);

        if(evento.getDate().getTime()<now.getTime()){
            throw new NullPointerException("Fecha menor a la actual");
        }else if(evento.getDate().getTime()>cincoDias.getTime()){
            throw new NullPointerException("Fecha más de 5 dias mayor a la actual");
        }


        RequestDarkSkyWeather openWeather = new RequestDarkSkyWeather();

        DecimalFormatSymbols symbol = new DecimalFormatSymbols();
        symbol.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#.00",symbol);
        DarkSkyResponse response = openWeather.getWeather(decimalFormat.format(evento.getUbicacion().getLatitud()),
                                                        decimalFormat.format(evento.getUbicacion().getLongitud()));

        getClimaDate(response,evento.getDate());

        return (float) fahrenheitToC(getClimaDate(response, evento.getDate()).getTemperature());

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
        if(list.size()>0) {
            Iterator<Weather> iterator = list.iterator();
            Weather masCercano = list.get(0);
            Weather uno;
            while (iterator.hasNext()) {
                uno = iterator.next();
                if (Math.abs(uno.getTime() - date.getTime()) <= Math.abs(masCercano.getTime() - date.getTime())) {
                    masCercano = uno;
                }

            }
            return masCercano;
        }

        return null;
    }


}



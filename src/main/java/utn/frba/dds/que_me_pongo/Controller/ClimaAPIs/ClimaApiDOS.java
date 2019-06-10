package utn.frba.dds.que_me_pongo.Controller.ClimaAPIs;

import utn.frba.dds.que_me_pongo.Helpers.PronosticoClassDarkSkyWeather.DarkSkyResponse;
import utn.frba.dds.que_me_pongo.Helpers.PronosticoClassDarkSkyWeather.Weather;
import utn.frba.dds.que_me_pongo.Model.ClimaService;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.WebServices.Request.ClimaRequest.RequestDarkSkyWeather;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class ClimaApiDOS implements ClimaService {
    @Override
    public Float getTemperatura(Evento evento) throws NullPointerException  {
        Date now = new Date();
        Date cincoDias = new Date();
        cincoDias.setTime(now.getTime() + 5*24*60*60*1000);

        if(evento.getDate().getTime()<now.getTime()){
            throw new NullPointerException("\033[0;1m"+"Fecha menor a la actual"+"\033[0;0m");
        }else if(evento.getDate().getTime()>cincoDias.getTime()){
            throw new NullPointerException("\033[0;1m"+"Fecha más de 5 dias mayor a la actual"+"\033[0;0m");
        }


        RequestDarkSkyWeather openWeather = new RequestDarkSkyWeather();

        DecimalFormatSymbols symbol = new DecimalFormatSymbols();
        symbol.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#.00",symbol);
        DarkSkyResponse response = openWeather.getWeather(decimalFormat.format(evento.getUbicacion().getLatitud()),
                                                        decimalFormat.format(evento.getUbicacion().getLongitud()));

        getClimaDate(response,evento.getDate());

        return (Float) fahrenheitToC(getClimaDate(response, evento.getDate()).getTemperature());

    }

    private Float kelvinToC(float kelvin){
        float cero = (float) 273.15;
        return (kelvin-cero);
    }

    private Float fahrenheitToC(float f){
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



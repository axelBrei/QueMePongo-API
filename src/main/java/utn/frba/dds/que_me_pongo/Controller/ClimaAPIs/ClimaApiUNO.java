package utn.frba.dds.que_me_pongo.Controller.ClimaAPIs;

import utn.frba.dds.que_me_pongo.Helpers.PronosticoClassDarkSkyWeather.DarkSkyResponse;
import utn.frba.dds.que_me_pongo.Helpers.PronosticoClassOpenWeather.Clima;
import utn.frba.dds.que_me_pongo.Helpers.PronosticoClassOpenWeather.ResponseWeather;
import utn.frba.dds.que_me_pongo.Model.ClimaService;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.WebServices.Request.ClimaRequest.RequestOpenWeather;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class ClimaApiUNO implements ClimaService {


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


        RequestOpenWeather openWeather = new RequestOpenWeather();

        DecimalFormatSymbols symbol = new DecimalFormatSymbols();
        symbol.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#.00",symbol);
        ResponseWeather response = openWeather.getWeather(decimalFormat.format(evento.getUbicacion().getLatitud()),
                decimalFormat.format(evento.getUbicacion().getLongitud()));

        return (Float) kelvinToC( getClimaDate(response,evento.getDate()).getMain().getTemp());
    }

    private Float kelvinToC(float kelvin){
        float cero = (float) 273.15;
        return (kelvin-cero);
    }

    private Clima getClimaDate(ResponseWeather responseWeather, Date date){
        responseWeather.getClimaList().removeIf(w -> Math.abs(w.getDate().getTime()-date.getTime()-(60*60*1.5*1000))/1000 > 60*60*1.5 ) ;

        if(responseWeather.getClimaList().size()>0)
            return responseWeather.getClimaList().get(0);

        return null;
    }

}

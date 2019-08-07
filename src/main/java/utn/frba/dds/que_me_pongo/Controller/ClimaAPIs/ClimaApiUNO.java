package utn.frba.dds.que_me_pongo.Controller.ClimaAPIs;

import utn.frba.dds.que_me_pongo.Utilities.WebServices.PronosticoClassOpenWeather.Clima;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.PronosticoClassOpenWeather.ResponseWeather;
import utn.frba.dds.que_me_pongo.Services.ClimaService;
import utn.frba.dds.que_me_pongo.Model.Evento;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.ClimaRequest.RequestOpenWeather;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;


public class ClimaApiUNO implements ClimaService {


    @Override
    public float getTemperatura(Evento evento) throws NullPointerException  {
        Date now = new Date();
        Date cincoDias = new Date();
        cincoDias.setTime(now.getTime() + 5*24*60*60*1000);

        if(evento.getDesde().getTime()<now.getTime()){
            throw new NullPointerException("Fecha menor a la actual");
        }else if(evento.getDesde().getTime()>cincoDias.getTime()){
            throw new NullPointerException("Fecha más de 5 dias mayor a la actual");
        }


        RequestOpenWeather openWeather = new RequestOpenWeather();

        DecimalFormatSymbols symbol = new DecimalFormatSymbols();
        symbol.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#.00",symbol);
        ResponseWeather response = openWeather.getWeather(decimalFormat.format(evento.getLatitud()),
                decimalFormat.format(evento.getLongitud()));

        return (float) kelvinToC( getClimaDate(response,evento.getDesde()).getMain().getTemp());
    }

    private float kelvinToC(float kelvin){
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

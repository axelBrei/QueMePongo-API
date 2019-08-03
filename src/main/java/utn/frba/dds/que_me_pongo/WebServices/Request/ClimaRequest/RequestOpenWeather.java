package utn.frba.dds.que_me_pongo.WebServices.Request.ClimaRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import utn.frba.dds.que_me_pongo.WebServices.PronosticoClassOpenWeather.ResponseWeather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestOpenWeather {

    private static String urlDay = "https://api.openweathermap.org/data/2.5/forecast?";
    private static String KEY = "24eeb3e4dd9b09325402534ed3462006";


    //weather?lat=35&lon=139&appid=b6907d289e10d714a6e88b30761fae22
    public static ResponseWeather getWeather(String lat, String lon) {
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        ResponseWeather response = new ResponseWeather();

        try {

            URL url = new URL(urlDay+"lat="+lat+"&lon="+lon+"&appid="+KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            String respuesta = "";
            
            while ((output = br.readLine()) != null) {
                respuesta+=output;
            }

            conn.disconnect();


            return gson.fromJson(respuesta.trim() , ResponseWeather.class);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return response;

    }

}
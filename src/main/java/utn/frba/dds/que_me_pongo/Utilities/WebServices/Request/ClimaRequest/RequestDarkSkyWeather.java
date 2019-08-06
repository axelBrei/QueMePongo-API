package utn.frba.dds.que_me_pongo.Utilities.WebServices.Request.ClimaRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utn.frba.dds.que_me_pongo.Utilities.WebServices.PronosticoClassDarkSkyWeather.DarkSkyResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestDarkSkyWeather {

    private static String urlDay = "https://api.darksky.net/forecast/";
    private static String KEY = "3c5c3769a79e1c7961dda13bd3af9a6f";


    public static DarkSkyResponse getWeather(String lat, String lon) {
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        DarkSkyResponse response = new DarkSkyResponse();

        try {

            URL url = new URL(urlDay+KEY+"/"+lat+","+lon);

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

            return gson.fromJson(respuesta.trim() , DarkSkyResponse.class);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return response;

    }

}

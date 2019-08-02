package utn.frba.dds.que_me_pongo.WebServices.PronosticoClassDarkSkyWeather;

import java.util.ArrayList;

public class DarkSkyResponse {
    private float latitude;
    private float longitude;
    private String timezone;
    Currently currently;
    Minutely minutely;
    Hourly hourly;
    Daily daily;

    private float offset;


    // Getter Methods

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public Currently getCurrently() {
        return currently;
    }

    public Minutely getMinutely() {
        return minutely;
    }

    public Hourly getHourly() {
        return hourly;
    }

    public Daily getDaily() {
        return daily;
    }

    public ArrayList<Weather> getClimaList(){
        ArrayList<Weather> list = this.getHourly().getWeather();
        list.addAll(getDaily().getWeather());

        return list;
    }



    public float getOffset() {
        return offset;
    }

    // Setter Methods

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setCurrently(Currently currently) {
        this.currently = currently;
    }

    public void setMinutely(Minutely minutely) {
        this.minutely = minutely;
    }

    public void setHourly(Hourly hourly) {
        this.hourly = hourly;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }


    public void setOffset(float offset) {
        this.offset = offset;
    }
}



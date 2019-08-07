package utn.frba.dds.que_me_pongo.Utilities.WebServices.PronosticoClassOpenWeather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Clima {
    private float dt;
    Main main;
    ArrayList< Object > weather = new ArrayList < Object > ();
    Clouds clouds;
    Wind wind;
    Sys sys;
    private String dt_txt;


    // Getter Methods

    public float getDt() {
        return dt;
    }

    public Main getMain() {
        return main;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public Sys getSys() {
        return sys;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public Date getDate() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
        try {
            //return format.parse(this.dt_txt);
            Date d = format.parse(this.dt_txt);

            return  d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Setter Methods

    public void setDt(float dt) {
        this.dt = dt;
    }

    public void setMain(Main mainObject) {
        this.main = mainObject;
    }

    public void setClouds(Clouds cloudsObject) {
        this.clouds = cloudsObject;
    }

    public void setWind(Wind windObject) {
        this.wind = windObject;
    }

    public void setSys(Sys sysObject) {
        this.sys = sysObject;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }
}


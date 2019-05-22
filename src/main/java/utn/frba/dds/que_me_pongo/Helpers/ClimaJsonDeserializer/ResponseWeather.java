package utn.frba.dds.que_me_pongo.Helpers.ClimaJsonDeserializer;

import java.util.ArrayList;

public class ResponseWeather {
    private Coord coord;
    ArrayList < Object > weather = new ArrayList < Object > ();
    private String base;
    private Main main;
    private float visibility;
    private Wind wind;
    private Clouds clouds;
    private float dt;
    private Sys sys;
    private float id;
    private String name;
    private float cod;


    // Getter Methods

    public Coord getCoord() {
        return this.coord;
    }

    public String getBase() {
        return base;
    }

    public Main getDataNow() {
        return this.main;
    }

    public float getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return this.wind;
    }

    public Clouds getClouds() {
        return this.clouds;
    }

    public float getDt() {
        return dt;
    }

    public Sys getSys() {
        return this.sys;
    }

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getCod() {
        return cod;
    }

    // Setter Methods

    public void setCoord(Coord coordObject) {
        this.coord = coordObject;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setMain(Main mainObject) {
        this.main = mainObject;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public void setWind(Wind windObject) {
        this.wind = windObject;
    }

    public void setClouds(Clouds cloudsObject) {
        this.clouds = cloudsObject;
    }

    public void setDt(float dt) {
        this.dt = dt;
    }

    public void setSys(Sys sysObject) {
        this.sys = sysObject;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCod(float cod) {
        this.cod = cod;
    }
}


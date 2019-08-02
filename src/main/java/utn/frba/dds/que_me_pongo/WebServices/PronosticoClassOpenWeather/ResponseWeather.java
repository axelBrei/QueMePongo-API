package utn.frba.dds.que_me_pongo.WebServices.PronosticoClassOpenWeather;

import java.util.ArrayList;

public class ResponseWeather {
    private String cod;
    private float message;
    private float cnt;
    ArrayList< Clima > list = new ArrayList < Clima > ();
    City city;


    // Getter Methods

    public String getCod() {
        return cod;
    }

    public float getMessage() {
        return message;
    }

    public float getCnt() {
        return cnt;
    }

    public City getCity() {
        return city;
    }

    public ArrayList<Clima> getClimaList() {
        return this.list;
    }

    // Setter Methods

    public void setCod(String cod) {
        this.cod = cod;
    }

    public void setMessage(float message) {
        this.message = message;
    }

    public void setCnt(float cnt) {
        this.cnt = cnt;
    }

    public void setCity(City cityObject) {
        this.city = cityObject;
    }
}


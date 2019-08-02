package utn.frba.dds.que_me_pongo.WebServices.PronosticoClassOpenWeather;

public class City {
    private float id;
    private String name;
    Coord coord;
    private String country;
    private float timezone;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coord getCoord() {
        return coord;
    }

    public String getCountry() {
        return country;
    }

    public float getTimezone() {
        return timezone;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoord(Coord coordObject) {
        this.coord = coordObject;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTimezone(float timezone) {
        this.timezone = timezone;
    }
}

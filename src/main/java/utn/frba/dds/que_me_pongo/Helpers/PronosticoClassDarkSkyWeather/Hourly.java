package utn.frba.dds.que_me_pongo.Helpers.PronosticoClassDarkSkyWeather;

import java.util.ArrayList;

public class Hourly {
    private String summary;
    private String icon;
    ArrayList< Weather > data = new ArrayList < Weather>();


    // Getter Methods

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public ArrayList<Weather> getWeather () {
        return data ;
    }

    // Setter Methods

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

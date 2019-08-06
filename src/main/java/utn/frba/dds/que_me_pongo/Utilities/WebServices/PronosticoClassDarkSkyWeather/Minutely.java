package utn.frba.dds.que_me_pongo.Utilities.WebServices.PronosticoClassDarkSkyWeather;

import java.util.ArrayList;

public class Minutely {
    private String summary;
    private String icon;
    ArrayList< Object > data = new ArrayList < Object > ();


    // Getter Methods

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    // Setter Methods

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

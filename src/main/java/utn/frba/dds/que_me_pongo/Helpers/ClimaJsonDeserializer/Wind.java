package utn.frba.dds.que_me_pongo.Helpers.ClimaJsonDeserializer;

public class Wind {
    private float speed;
    private float deg;
    private float gust;


    // Getter Methods

    public float getSpeed() {
        return speed;
    }

    public float getDeg() {
        return deg;
    }

    public float getGust() {
        return gust;
    }

    // Setter Methods

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setDeg(float deg) {
        this.deg = deg;
    }

    public void setGust(float gust) {
        this.gust = gust;
    }
}

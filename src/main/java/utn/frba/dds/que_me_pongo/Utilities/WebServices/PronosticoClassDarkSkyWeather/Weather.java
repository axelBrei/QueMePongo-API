package utn.frba.dds.que_me_pongo.Utilities.WebServices.PronosticoClassDarkSkyWeather;

public class Weather {
    private long time;
    private String summary;
    private String icon;
    private float sunriseTime;
    private float sunsetTime;
    private float moonPhase;
    private float precipIntensity;
    private float precipIntensityMax;
    private float precipIntensityMaxTime;
    private float precipProbability;
    private String precipType;
    private float temperatureHigh;
    private float temperatureHighTime;
    private float temperatureLow;
    private float temperatureLowTime;
    private float apparentTemperatureHigh;
    private float apparentTemperatureHighTime;
    private float apparentTemperatureLow;
    private float apparentTemperatureLowTime;
    private float dewPoint;
    private float humidity;
    private float pressure;
    private float windSpeed;
    private float windGust;
    private float windGustTime;
    private float windBearing;
    private float cloudCover;
    private float uvIndex;
    private float uvIndexTime;
    private float visibility;
    private float ozone;
    private float temperature=-100;
    private float temperatureMin;
    private float temperatureMinTime;
    private float temperatureMax;
    private float temperatureMaxTime;
    private float apparentTemperatureMin;
    private float apparentTemperatureMinTime;
    private float apparentTemperatureMax;
    private float apparentTemperatureMaxTime;


    // Getter Methods

    public long getTime() {
        return time*1000;
    }

    public float getTemperature() {
        if(temperature!=-100)
            return temperature;

        return (temperatureMin+temperatureMax)/2;
    }

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public float getSunriseTime() {
        return sunriseTime;
    }

    public float getSunsetTime() {
        return sunsetTime;
    }

    public float getMoonPhase() {
        return moonPhase;
    }

    public float getPrecipIntensity() {
        return precipIntensity;
    }

    public float getPrecipIntensityMax() {
        return precipIntensityMax;
    }

    public float getPrecipIntensityMaxTime() {
        return precipIntensityMaxTime;
    }

    public float getPrecipProbability() {
        return precipProbability;
    }

    public String getPrecipType() {
        return precipType;
    }

    public float getTemperatureHigh() {
        return temperatureHigh;
    }

    public float getTemperatureHighTime() {
        return temperatureHighTime;
    }

    public float getTemperatureLow() {
        return temperatureLow;
    }

    public float getTemperatureLowTime() {
        return temperatureLowTime;
    }

    public float getApparentTemperatureHigh() {
        return apparentTemperatureHigh;
    }

    public float getApparentTemperatureHighTime() {
        return apparentTemperatureHighTime;
    }

    public float getApparentTemperatureLow() {
        return apparentTemperatureLow;
    }

    public float getApparentTemperatureLowTime() {
        return apparentTemperatureLowTime;
    }

    public float getDewPoint() {
        return dewPoint;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public float getWindGust() {
        return windGust;
    }

    public float getWindGustTime() {
        return windGustTime;
    }

    public float getWindBearing() {
        return windBearing;
    }

    public float getCloudCover() {
        return cloudCover;
    }

    public float getUvIndex() {
        return uvIndex;
    }

    public float getUvIndexTime() {
        return uvIndexTime;
    }

    public float getVisibility() {
        return visibility;
    }

    public float getOzone() {
        return ozone;
    }

    public float getTemperatureMin() {
        return temperatureMin;
    }

    public float getTemperatureMinTime() {
        return temperatureMinTime;
    }

    public float getTemperatureMax() {
        return temperatureMax;
    }

    public float getTemperatureMaxTime() {
        return temperatureMaxTime;
    }

    public float getApparentTemperatureMin() {
        return apparentTemperatureMin;
    }

    public float getApparentTemperatureMinTime() {
        return apparentTemperatureMinTime;
    }

    public float getApparentTemperatureMax() {
        return apparentTemperatureMax;
    }

    public float getApparentTemperatureMaxTime() {
        return apparentTemperatureMaxTime;
    }

    // Setter Methods

    public void setTime(long time) {
        this.time = time;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setSunriseTime(float sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public void setSunsetTime(float sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public void setMoonPhase(float moonPhase) {
        this.moonPhase = moonPhase;
    }

    public void setPrecipIntensity(float precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    public void setPrecipIntensityMax(float precipIntensityMax) {
        this.precipIntensityMax = precipIntensityMax;
    }

    public void setPrecipIntensityMaxTime(float precipIntensityMaxTime) {
        this.precipIntensityMaxTime = precipIntensityMaxTime;
    }

    public void setPrecipProbability(float precipProbability) {
        this.precipProbability = precipProbability;
    }

    public void setPrecipType(String precipType) {
        this.precipType = precipType;
    }

    public void setTemperatureHigh(float temperatureHigh) {
        this.temperatureHigh = temperatureHigh;
    }

    public void setTemperatureHighTime(float temperatureHighTime) {
        this.temperatureHighTime = temperatureHighTime;
    }

    public void setTemperatureLow(float temperatureLow) {
        this.temperatureLow = temperatureLow;
    }

    public void setTemperatureLowTime(float temperatureLowTime) {
        this.temperatureLowTime = temperatureLowTime;
    }

    public void setApparentTemperatureHigh(float apparentTemperatureHigh) {
        this.apparentTemperatureHigh = apparentTemperatureHigh;
    }

    public void setApparentTemperatureHighTime(float apparentTemperatureHighTime) {
        this.apparentTemperatureHighTime = apparentTemperatureHighTime;
    }

    public void setApparentTemperatureLow(float apparentTemperatureLow) {
        this.apparentTemperatureLow = apparentTemperatureLow;
    }

    public void setApparentTemperatureLowTime(float apparentTemperatureLowTime) {
        this.apparentTemperatureLowTime = apparentTemperatureLowTime;
    }

    public void setDewPoint(float dewPoint) {
        this.dewPoint = dewPoint;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindGust(float windGust) {
        this.windGust = windGust;
    }

    public void setWindGustTime(float windGustTime) {
        this.windGustTime = windGustTime;
    }

    public void setWindBearing(float windBearing) {
        this.windBearing = windBearing;
    }

    public void setCloudCover(float cloudCover) {
        this.cloudCover = cloudCover;
    }

    public void setUvIndex(float uvIndex) {
        this.uvIndex = uvIndex;
    }

    public void setUvIndexTime(float uvIndexTime) {
        this.uvIndexTime = uvIndexTime;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public void setOzone(float ozone) {
        this.ozone = ozone;
    }

    public void setTemperatureMin(float temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public void setTemperatureMinTime(float temperatureMinTime) {
        this.temperatureMinTime = temperatureMinTime;
    }

    public void setTemperatureMax(float temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public void setTemperatureMaxTime(float temperatureMaxTime) {
        this.temperatureMaxTime = temperatureMaxTime;
    }

    public void setApparentTemperatureMin(float apparentTemperatureMin) {
        this.apparentTemperatureMin = apparentTemperatureMin;
    }

    public void setApparentTemperatureMinTime(float apparentTemperatureMinTime) {
        this.apparentTemperatureMinTime = apparentTemperatureMinTime;
    }

    public void setApparentTemperatureMax(float apparentTemperatureMax) {
        this.apparentTemperatureMax = apparentTemperatureMax;
    }

    public void setApparentTemperatureMaxTime(float apparentTemperatureMaxTime) {
        this.apparentTemperatureMaxTime = apparentTemperatureMaxTime;
    }
}

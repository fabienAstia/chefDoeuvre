package co.simplon.personalities.dtos;

public class CoordinatesView {
    double lon;
    double lat;

    public CoordinatesView(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "CoordinatesView{" +
                "lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }

}

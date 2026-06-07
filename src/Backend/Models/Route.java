package Backend.Models;

public class Route {
    
    private int origenCityId;
    private int destinationCityId;
    private int distance;

    public Route(int origenCityId, int destinationCityId, int distance) {
        this.origenCityId = origenCityId;
        this.destinationCityId = destinationCityId;
        this.distance = distance;
    }

    public int getOrigenCityId() {
        return origenCityId;
    }

    public int getDestinationCityId() {
        return destinationCityId;
    }

    public int getDistance() {
        return distance;
    }

    public void setOrigenCityId(int origenCityId) {
        this.origenCityId = origenCityId;
    }

    public void setDestinationCityId(int destinationCityId) {
        this.destinationCityId = destinationCityId;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}

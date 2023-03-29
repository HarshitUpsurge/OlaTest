package vehicle.models;


public class Vehicle {

    public String getCarNumber() {
        return this.carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public Double getLat() {
        return this.lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return this.lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isIsAvailable() {
        return this.isAvailable;
    }

    public Boolean getIsAvailable() {
        return this.isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getDriverId() {
        return this.driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public boolean isNull() 
    {
        if(this.getCarNumber() != null || this.getLat() != null || this.getLon() != null || this.getType() != null || this.getIsAvailable() != null || this.getDriverId() != null){
            return false;
        }
        return true;
    }

    private String carNumber;
    private Double lat;
    private Double lon;
    private String type;
    private Boolean isAvailable;
    private String driverId;
}

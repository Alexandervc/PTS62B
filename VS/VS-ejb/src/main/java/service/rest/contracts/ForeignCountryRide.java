package service.rest.contracts;

public class ForeignCountryRide {
    private Long foreignCountryRideId;
    private double totalPrice;

    public Long getForeignCountryRideId() {
        return foreignCountryRideId;
    }

    public void setForeignCountryRideId(Long foreignCountryRideId) {
        this.foreignCountryRideId = foreignCountryRideId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
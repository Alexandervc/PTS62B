package business;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import dao.BillDao;
import dao.RateDao;
import domain.Bill;
import domain.ForeignCountryRide;
import domain.Person;
import domain.Rate;
import dto.BillRoadUsage;
import java.util.ArrayList;

/**
 * Manager for BillDao.
 *
 * @author Melanie.
 */
@Stateless
public class BillManager {
    
    @Inject
    private BillDao billDao;

    @Inject
    private RateDao rateDAO;

    @Inject
    private ForeignCountryManager foreignCountryManager;
    
    /**
     * Find all bills in Database from person.
     *
     * @param person Type Person.
     */
    public void findBills(Person person) {
        this.billDao.findAllForUser(person);
    }
    
    /**
     * find bill in database with cartrackerId, month and year.
     * @param cartrackerId Long.
     * @param month integer.
     * @param year integer.
     * @return Bill, if no bill found return null;
     */
    public Bill findBillWithCartracker(String cartrackerId,
                                       int month,
                                       int year) {
        return this.billDao.findBillWithCartracker(cartrackerId, month, year);
    }

    /**
     * Generate bill.
     *
     * @param person Type Person.
     * @param roadUsages List of IRoadUsage.
     * @param cartrackerId.
     * @param month.
     * @param year.
     * @return new Bill Type Bill.
     */
    public Bill generateBill(Person person, List<BillRoadUsage> roadUsages,
            String cartrackerId, int month, int year)
            throws EntityNotFoundException {
        double totalPrice = 0.0;
        double ruPrice;

        for (BillRoadUsage ru : roadUsages) {
            // If the BillRoadUsage contains a ForeignCountryRideId, the
            // BillRoadUsage's origin is not from this country. The price should
            // be retrieved from the RAD database. If the price could not be 
            // found, an exception is thrown.
            // If the BillRoadUsage does not contain a ForeignCountryRideId,the 
            // BillRoadUsage's origin is from this country. Calculate the cost
            // by the distance multiplied by the cost of the RoadType's rate.
            if (ru.getForeignCountryRideId() != null) {
                ForeignCountryRide foreignCountryRide;
                foreignCountryRide = this.foreignCountryManager.
                        findRideByForeignCountryRideId(
                                ru.getForeignCountryRideId());

                // If the foreignCountryRide is null, the query did not return
                // exactly one result. This means that the price was not stored 
                // or multiple entries with the same foreignCountryRideId exist.
                if (foreignCountryRide != null) {
                    ruPrice = foreignCountryRide.getTotalPrice();
                } else {
                    throw new EntityNotFoundException("Cost of the "
                            + "ForeignCountryRide was not found in the RAD "
                            + "database.");
                }
            } else {
                Rate rate = this.rateDAO.find(ru.getRoadType());
                ru.setRate(rate.getPrice());
                ruPrice = ru.getKm() * rate.getPrice();
            }
            
            ru.setPrice(ruPrice);
            totalPrice += ruPrice;
        }
        
        // Remove duplicate ForeignCountryRides
        List<BillRoadUsage> billRoadUsages = new ArrayList<>();
        for (BillRoadUsage ru : roadUsages) {
            // Check if the billRoadUsages list contains the road.
            if (!this.roadUsagesContainsRoad(billRoadUsages, ru.getRoadName())) {
                // If the billRoadUsages list contains the road, get all the
                // roads with the same name as ru.getRoadName().
                List<BillRoadUsage> containedRoadUsages 
                    = this.containedRoadUsages(roadUsages, ru.getRoadName());
            
                BillRoadUsage roadUsage = null;
                for (BillRoadUsage billRoadUsage : containedRoadUsages) {
                    if (roadUsage == null) {
                        roadUsage = billRoadUsage;
                    } else {
                        // Add the price of the duplicate RoadUsage to the
                        // roadUsage.
                        roadUsage.setPrice(roadUsage.getPrice() 
                                           + billRoadUsage.getPrice());
                    }
                }
                
                billRoadUsages.add(roadUsage);
            }
        }

        // Check if bill exicts in the database.
        Bill bill = this.findBillWithCartracker(cartrackerId, month, year);
        
        // Round totalPrice
        totalPrice = Math.round(totalPrice * 100.0) / 100.0;
        
        if(bill == null){            
            // If the bill was not found, create a new bill entry.
            bill = new Bill(person,
                            billRoadUsages,
                            totalPrice,
                            cartrackerId,
                            month,
                            year);
            
            this.billDao.create(bill);
        } else {
            // If the bill already exists in the database, update the RoadUsages
            // and the Total price.
            bill.setRoadUsages(billRoadUsages);
            bill.setTotalPrice(totalPrice);
            
            this.billDao.edit(bill);
        }
        
        return bill;
    }
    
    /**
     * Checks if the RoadUsages list contains the Road by RoadName.
     * @param roadUsages The list in which to check if it contains the Road.
     * @param roadName The road to check for.
     * @return true if the list contains the road, otherwise false.
     */
    private boolean roadUsagesContainsRoad(List<BillRoadUsage> roadUsages,
                                           String roadName) {
        for (BillRoadUsage roadUsage : roadUsages) {
            if (roadName.equals(roadUsage.getRoadName())) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Checks if the BillRoadUsages list contains the Road by RoadName.
     * @param roadUsages The list in which to check if it contains the Road.
     * @param roadName The road to check for.
     * @return A list of BillRoadUsages which contains every BillRoadUsage which
     *      contain the given RoadName.
     */
    private List<BillRoadUsage> containedRoadUsages(
            List<BillRoadUsage> roadUsages,
            String roadName) {
        List<BillRoadUsage> billRoadUsages = new ArrayList<>();
        
        for (BillRoadUsage roadUsage : roadUsages) {
            if (roadName.equals(roadUsage.getRoadName())) {
                billRoadUsages.add(roadUsage);
            }
        }
        
        return billRoadUsages;
    }
    
    /**
     * Calculate the price for the given roadUsages.
     *
     * @param roadUsages The roadUsages to calculate the price for.
     * @return The price.
     */
    public Double calculatePrice(List<BillRoadUsage> roadUsages) {
        double totalPrice = 0;

        for (BillRoadUsage ru : roadUsages) {
            totalPrice += this.calculatePrice(ru);
        }

        return totalPrice;
    }

    /**
     * Calculate the price for the given roadUsage.
     *
     * @param roadUsage The roadUsage to calculate the price for.
     * @return The price.
     */
    private Double calculatePrice(BillRoadUsage roadUsage) {
        Rate rate = this.rateDAO.find(roadUsage.getRoadType());
        return roadUsage.getKm() * rate.getPrice();
    }

    /**
     * Setter BillDao.
     *
     * @param billDao object.
     */
    public void setBillDao(BillDao billDao) {
        this.billDao = billDao;
    }

    /**
     * Setter RateDao.
     *
     * @param rateDAO object.
     */
    public void setRateDAO(RateDao rateDAO) {
        this.rateDAO = rateDAO;
    }

    /**
     * Setter ForeignCountryManager.
     *
     * @param foreignCountryManager object.
     */
    public void setForeignCountryManager(
            ForeignCountryManager foreignCountryManager) {
        this.foreignCountryManager = foreignCountryManager;
    }

}

package business;

import dao.BillDao;
import dao.RateDao;
import domain.Bill;
import domain.Person;
import domain.Rate;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.RoadUsage;

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

    /**
     * Find all bills in Database from person.
     *
     * @param person Type Person.
     */
    public void findBills(Person person) {
        this.billDao.findAllForUser(person);
    }

    /**
     * Create bill in Database.
     *
     * @param bill Type Bill.
     */
    public void createBill(Bill bill) {
        this.billDao.create(bill);
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
    public Bill generateBill(Person person, List<RoadUsage> roadUsages,
            String cartrackerId, String month, String year) {
        double totalPrice = this.calculatePrice(roadUsages);

        Bill bill = new Bill(person, roadUsages, totalPrice, cartrackerId,
                month, year);
        return bill;
    }

    /**
     * Calculate the price for the given roadUsages.
     * @param roadUsages The roadUsages to calculate the price for.
     * @return The price.
     */
    public Double calculatePrice(List<RoadUsage> roadUsages) {
        double totalPrice = 0;

        for (RoadUsage ru : roadUsages) {
            totalPrice += this.calculatePrice(ru);
        }

        return totalPrice;
    }

    /**
     * Calculate the price for the given roadUsage.
     * @param roadUsage The roadUsage to calculate the price for.
     * @return The price.
     */
    private Double calculatePrice(RoadUsage roadUsage) {
        Rate rate = this.rateDAO.find(roadUsage.getRoadType());
        double price = roadUsage.getKm() * rate.getRate();
        return price;
    }
}

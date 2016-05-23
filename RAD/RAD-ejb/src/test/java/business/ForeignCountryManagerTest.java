/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dao.ForeignCountryRideDao;
import domain.ForeignCountryRide;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import static org.mockito.Matchers.argThat;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests the ForeignCountryRide.
 *
 * @author Jesse
 */
@RunWith(MockitoJUnitRunner.class)
public class ForeignCountryManagerTest {

    private ForeignCountryManager foreignCountryManager;

    @Mock
    private ForeignCountryRideDao foreignCountryRideDao;

    /**
     * Initializes the test class before testing.
     */
    @Before
    public void setUp() {
        this.foreignCountryManager = new ForeignCountryManager();

        // Set the mocked ForeignCountryRideDao.
        this.foreignCountryManager.setForeignCountryRideDao(
                foreignCountryRideDao);
    }

    /**
     * Tests the createForeignCountryRide method.
     */
    @Test
    public void createForeignCountryRideTest() {
        ForeignCountryRide foreignCountryRide = new ForeignCountryRide("PT29", 13.37);

        this.foreignCountryManager.createForeignCountryRide("PT29", 13.37);
        verify(this.foreignCountryManager.getForeignCountryRideDao())
                .create(argThat(
                        new IsSameForeignCountryRide(foreignCountryRide)));
    }

    /**
     * Matcher for ForeignCountryRide.
     */
    private class IsSameForeignCountryRide
            extends ArgumentMatcher<ForeignCountryRide> {

        private final ForeignCountryRide foreignCountryRide;

        public IsSameForeignCountryRide(ForeignCountryRide foreignCountryRide) {
            this.foreignCountryRide = foreignCountryRide;
        }

        @Override
        public boolean matches(Object argument) {
            ForeignCountryRide other = (ForeignCountryRide) argument;
            return this.foreignCountryRide.getForeignCountryRideId()
                    .equals(other.getForeignCountryRideId())
                    && this.foreignCountryRide.getTotalPrice()
                    == other.getTotalPrice();
        }
    }
}

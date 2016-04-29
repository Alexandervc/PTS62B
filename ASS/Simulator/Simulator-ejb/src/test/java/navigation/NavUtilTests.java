package navigation;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import model.DirectionInput;
import model.Point;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import service.IPathService;
import service.PathService;
import service.jms.SendPositionBean;
import support.NavUtils;

/**
 * NavUtilTests Class.
 * @author Melanie
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(value = PathService.class)
public class NavUtilTests {
    private static final Logger LOGGER =
            Logger.getLogger(NavUtilTests.class.getCanonicalName());

    @Inject
    private IPathService pathService;
    
    @Mock
    @Produces
    private SendPositionBean sendPositionBean;
    
    /**
     * Test method for
     * {@link frk.gpssimulator.service.impl.DefaultKmlService
     * #getCoordinates(java.io.File)}.
     *
     * @throws JAXBException .
     * @throws NumberFormatException .
     */
    @Test
    public void testTotalDistance() 
            throws NumberFormatException, JAXBException {
        DirectionInput directionInput = new DirectionInput();
        directionInput.setFrom("73-2020 Kaloko Dr, Kailua-Kona, HI 96740");
        directionInput.setTo("73-1249 Kaloko Dr, Kailua-Kona, HI 96740");

        List<Point> points = this.pathService.
                getCoordinatesFromGoogle(directionInput);

        double totalDistance = NavUtils.getTotalDistance(points);

        String output = "totalDistance: " + totalDistance;
        LOGGER.log(Level.INFO, output);
    }

    /**
     * Test method for
     * {@link frk.gpssimulator.service.impl.DefaultKmlService
     * #getCoordinates(java.io.File)}.
     * 
     * @throws NumberFormatException .
     * @throws JAXBException . 
     */
    @Test
    public void testTotalDistance2() 
            throws NumberFormatException, JAXBException {
        DirectionInput directionInput = new DirectionInput();
        directionInput.setFrom("Polderzicht 5, 4261 KK Wijk en Aalburg");
        directionInput.setTo("Rachelsmolen 1, Eindhoven");

        List<Point> points = this.pathService.
                getCoordinatesFromGoogle(directionInput);
        
        String output1 = "Number of points: " + points.size();
        LOGGER.log(Level.INFO, output1);

        Double totalDistance = NavUtils.getTotalDistance(points);

        String output2 = "totalDistance: " + totalDistance;
        LOGGER.log(Level.INFO, output2);
        
        int distance = totalDistance.intValue();
        int distanceKM = distance / 1000;
        
        assertEquals(51, distanceKM);
    }
    
    /**
     * Test method for
     * {@link frk.gpssimulator.service.impl.DefaultKmlService
     * #getCoordinates(java.io.File)}.
     * 
     * @throws NumberFormatException .
     * @throws JAXBException .
     */
    @Test
    public void testTotalDistance3() 
            throws NumberFormatException, JAXBException {
        DirectionInput directionInput = new DirectionInput();
        directionInput.setFrom("Parallelweg 88, 4283 GS Giessen");
        directionInput.setTo("Veensesteeg 19, 4264 KG Veen");

        List<Point> points = this.pathService.
                getCoordinatesFromGoogle(directionInput);
        
        String output1 = "Number of points: " + points.size();
        LOGGER.log(Level.INFO, output1);

        Double totalDistance = NavUtils.getTotalDistance(points);

        String output2 = "totalDistance: " + totalDistance;
        LOGGER.log(Level.INFO, output2);
        
        int distance = totalDistance.intValue();
        int distanceKM = distance / 1000;
        
        assertEquals(4, distanceKM);
    }
}

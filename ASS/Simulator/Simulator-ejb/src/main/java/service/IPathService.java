package service;

import java.util.List;
import model.DirectionInput;
import model.GpsSimulatorInstance;
import model.Point;
import simulator.GpsSimulator;

/**
 * PathService interface.
 * @author Melanie.
 */
public interface IPathService {
    List<Point> getCoordinatesFromGoogle(DirectionInput directionInput);
    DirectionInput getRandomDirectioninput();
    void generateFile();
    GpsSimulatorInstance generate();
    GpsSimulator prepareGpsSimulator(GpsSimulator gpsSimulator, List<Point> points);
}

package service;

import model.PositionInfo;

/**
 *
 * @author Melanie
 */
public interface IGpsdService {
    /**
     * Sends NMEA RMC report to linux gps daemon, gpsd via predetermined pipe.
     *
     * @param position
     */
    void updatePosition(PositionInfo position);
}

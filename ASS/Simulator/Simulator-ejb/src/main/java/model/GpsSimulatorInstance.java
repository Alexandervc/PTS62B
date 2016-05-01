package model;

import java.util.concurrent.Future;
import simulator.GpsSimulator;

/**
 * GpsSimulatorInstance Class.
 * @author Melanie.
 */
public class GpsSimulatorInstance {
    private long instanceId;
    private GpsSimulator gpsSimulator;
    private Future<?> gpsSimulatorTask;

    /**
     * GpsSimulatorInstance constructor.
     * 
     * @param instanceId.
     * @param gpsSimulator.
     * @param gpsSimulatorTask.
     */
    public GpsSimulatorInstance(long instanceId, GpsSimulator gpsSimulator, 
            Future<?> gpsSimulatorTask) {
        super();
        this.instanceId = instanceId;
        this.gpsSimulator = gpsSimulator;
        this.gpsSimulatorTask = gpsSimulatorTask;
    }

    public long getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(long instanceId) {
        this.instanceId = instanceId;
    }

    public GpsSimulator getGpsSimulator() {
        return this.gpsSimulator;
    }

    public void setGpsSimulator(GpsSimulator gpsSimulator) {
        this.gpsSimulator = gpsSimulator;
    }

    public Future<?> getGpsSimulatorTask() {
        return this.gpsSimulatorTask;
    }

    public void setGpsSimulatorTask(Future<?> gpsSimulatorTask) {
        this.gpsSimulatorTask = gpsSimulatorTask;
    }

    /**
     * ToString method.
     * 
     * @return String.
     */
    @Override
    public String toString() {
        return "GpsSimulatorInstance [instanceId=" + this.instanceId + ", "
                + "gpsSimulator=" + this.gpsSimulator
                + ", gpsSimulatorTask=" + this.gpsSimulatorTask + "]";
    }
}

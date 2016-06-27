/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Timer task class.
 * @author Linda.
 */
@Startup
@Singleton
public class SendTask extends TimerTask {

    private static final Logger LOGGER
            = Logger.getLogger(SendTask.class.getName());
    private static final Long MINUTE = new Long(60 * 1000);

    @Inject
    private CarPositionManager manager;

    private Timer timer;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    /**
     * Constructor for this class.
     * Must run when application is started.
     */
    @PostConstruct
    public void startTimer() {
        if (isRunning.compareAndSet(false, true)) {
            LOGGER.log(Level.INFO, "Timer is start");
            this.timer = new Timer();
            this.timer.schedule(this, MINUTE, MINUTE);
        }
    }

    /**
     * Task for timer.
     */
    @Override
    public void run() {
        this.manager.searchForMissingPositions();
    }
}

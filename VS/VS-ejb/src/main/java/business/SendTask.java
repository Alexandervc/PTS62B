/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author Linda
 */
@Startup
@Singleton
public class SendTask extends TimerTask {

    private static final Long MINUTE = new Long(60 * 1000);

    @Inject
    private CarPositionManager manager;

    private Timer timer;
    private AtomicBoolean isRunning = new AtomicBoolean(false);

    @PostConstruct
    public void startTimer() {
        if (isRunning.compareAndSet(false, true)) {
            this.timer = new Timer();
            this.timer.schedule(this, MINUTE, MINUTE);
        }
    }

    @Override
    public void run() {
        this.manager.searchForMissingPositions();
    }
}

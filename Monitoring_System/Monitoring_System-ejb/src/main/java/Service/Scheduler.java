/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.util.Properties;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Schedule;
import javax.inject.Singleton;

/**
 *
 * @author Edwin
 */
 
public class Scheduler implements Runnable {
 
    public void run() {
        BatchRuntime.getJobOperator().start("ExecuteTests", new Properties());
    }
 
}
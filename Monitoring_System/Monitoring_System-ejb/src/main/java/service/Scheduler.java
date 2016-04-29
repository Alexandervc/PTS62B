/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Properties;
import javax.batch.runtime.BatchRuntime;

/**
 * A class that is run on a schedule so that tests will be executed.
 * @author Edwin.
 */
 
public class Scheduler implements Runnable {
 
    /**
     * Runs the batch job that executes the tests.
     */
    @Override
    public void run() {
        System.out.println("HELLO SCHEDULER");
        //BatchRuntime.getJobOperator().start("ExecuteTests", new Properties());
    }
 
}
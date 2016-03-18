/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author Alexander
 */
@Singleton
@Startup
public class RmiServer {

    private static final int portnumber = 1099;

    private Registry registry;

    // TODO?
    private static final String bindingname = "VS_MovementService";
    // @Inject
    private IMovementService movementService;

    @PostConstruct
    private void start() {
        try {
            System.out.println("Server: portnumber " + portnumber);

            // TODO inject!!!
            this.registry = LocateRegistry.getRegistry("localhost", portnumber);
            //System.out.println(this.registry.list());
            if (registry == null) {
            System.out.println("------------------------------------------------------------------ Create registry");
                this.registry = LocateRegistry.createRegistry(portnumber);
             
            
                if (this.registry != null) {
                    movementService = new MovementService();
                    //this.registry.unbind(bindingname);
                    System.out.println("----------------------------------------------------------------------- bind service");
                    this.registry.rebind(bindingname, movementService);
                }}
            else {
                System.out.println("---------------------------------------------------- registry already exists");
            }
        } catch (RemoteException ex) {
            // ExportException niet erg??
            ex.printStackTrace();
        } 
    }

    /*
private static void printIPadresses() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Server: IP-adres: " + localhost.getHostAddress());
            // Just in case this host has multiple IP addresses....
            InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
            if (allMyIps != null && allMyIps.length > 1) {
                System.out.println("Server: Full list of IP addresses:");
                for (InetAddress allMyIp : allMyIps){
                    System.out.println("     " + allMyIp);
                }
            }
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        
        try {
            System.out.println("Server: Full list of network interfaces:");
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                System.out.println("    " + intf.getName() + " " + intf.getDisplayName());
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    System.out.println("        " + enumIpAddr.nextElement().toString());
                }
            }
        } catch (SocketException ex) {
            System.out.println("Server: Cannot retrieve network interface list");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }*/
}

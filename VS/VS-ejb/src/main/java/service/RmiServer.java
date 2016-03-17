/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.rmi.registry.Registry;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Alexander
 */
@Singleton
@Startup
public class RmiServer {
    private static final int portnumber = 1099;
    private static final String bindingname = "VS";
    
    private static Registry registry;
    
    
    
    @PostConstruct
    private void start() {
    
    /*
    
    public ServerController() {
        try {
            System.out.println("Server: portnumber " + portnumber);
            
            this.admin = Administratie.getAdministratie();
            
            this.registry = LocateRegistry.createRegistry(portnumber);
            if(this.registry != null){
                this.registry.rebind(bindingname, admin);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    }
    
    public static void main(String[] args) {
        System.out.println("SERVER UITSTAPJESAPPLICATIE");
        printIPadresses();
        new ServerController();
    }*/
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

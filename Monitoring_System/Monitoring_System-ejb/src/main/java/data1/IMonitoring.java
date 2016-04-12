/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data1;

import java.rmi.RemoteException;

/**
 *
 * @author Edwin
 */
public interface IMonitoring {
    public void getServerStatus() throws RemoteException;
    public void getFunctionalStatus() throws RemoteException;
}

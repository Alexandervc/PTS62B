/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Alexander
 */
public class NewClass extends UnicastRemoteObject implements INewClass {
    public NewClass() throws RemoteException {
        
    }
}

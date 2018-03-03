package edu.ufp.sd.calculator.server;

import edu.ufp.sd.calculator.server.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <p>Title: Projecto SD</p>
 * <p>Description: Projecto apoio aulas SD</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: UFP </p>
 * @author Rui Moreira
 * @version 1.0
 */
public interface CalculatorRI extends Remote {
    public void print(String msg) throws RemoteException;
    
    
    /*
    * Declaration of functions
    */
    public double addNumber(int a, int b) throws RemoteException;
    
    public double subNumber(int a, int b) throws RemoteException;
    
    public double mulNumber(int a, int b) throws RemoteException;
    
    public double divNumber(int a, int b) throws RemoteException;
}




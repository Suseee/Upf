package edu.ufp.sd.calculator.server;

import edu.ufp.sd.calculator.server.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Title: Projecto SD</p>
 * <p>Description: Projecto apoio aulas SD</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: UFP </p>
 * @author Rui S. Moreira
 * @version 3.0
 */
public class CalculatorImpl extends UnicastRemoteObject implements CalculatorRI {

    // Uses RMI-default sockets-based transport
    // Runs forever (do not passivates) - Do not needs rmid (activation deamon)
    // Constructor must throw RemoteException due to export()
    public CalculatorImpl() throws RemoteException {
        // Invokes UnicastRemoteObject constructor which exports remote object
        super();
    }

    @Override
    public void print(String msg) throws RemoteException {
        //System.out.println("HelloWorldImpl - print(): someone called me with msg = "+ msg);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "someone called me with msg = {0}", new Object[]{msg});
    }
    
    
    @Override
    /*
     Function Add Number 
    */
    public double addNumber(int a, int b) throws RemoteException{
        return a+b;
    }
    
    
    @Override 
    /*
     Function Subtract Number 
    */
    public double subNumber(int a, int b) throws RemoteException{
        return a-b;
    }
    
    @Override
    /*
     Function Multiply Number 
    */
    public double mulNumber(int a, int b) throws RemoteException{
        return a*b;
    }
    
    @Override
    /*
     Function Dividor Number 
    */
    public double divNumber(int a, int b) throws RemoteException{
        if(b == 0){
            throw new RemoteException("Cant be possible div by 0");
        }
    
        return a/b;
    }
        
}

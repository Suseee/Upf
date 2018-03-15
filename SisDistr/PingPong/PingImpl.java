package edu.ufp.sd.pingpong.server;

import edu.ufp.sd.pingpong.client.PongRI;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Title: Projecto SD</p>
 * <p>
 * Description: Projecto apoio aulas SD</p>
 * <p>
 * Copyright: Copyright (c) 2017</p>
 * <p>
 * Company: UFP </p>
 *
 * @author Rui S. Moreira
 * @version 3.0
 */
public class PingImpl extends UnicastRemoteObject implements PingRI {

    // Uses RMI-default sockets-based transport
    // Runs forever (do not passivates) - Do not needs rmid (activation deamon)
    // Constructor must throw RemoteException due to export()
    public PingImpl() throws RemoteException {
        // Invokes UnicastRemoteObject constructor which exports remote object
        super();
    }

    
    
    
    
    
    @Override
    public void ping(Ball b, PongRI pong) throws RemoteException {
        
        PingThread p = new PingThread (b,pong);
        p.start();
        System.out.println("PingImpl: ping(): client called me with id = " +b.getId());
        pong.pong(b);
    }

}

// Criação da classe para thread
   class PingThread extends Thread{
 
       PongRI pongRI;
       Ball ball;

    PingThread(Ball b, PongRI pong) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
       
       public void run(){
           
           
           try {
               pongRI.pong(this.ball);
           } catch (RemoteException ex) {
               Logger.getLogger(PingThread.class.getName()).log(Level.SEVERE, null, ex);
           }
           
       }
       
       
   }
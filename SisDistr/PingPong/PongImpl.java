package edu.ufp.sd.pingpong.client;

import edu.ufp.sd.pingpong.server.Ball;
import edu.ufp.sd.pingpong.server.PingRI;
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
public class PongImpl implements PongRI {

    PingRI pingRI;
    Ball ball;

    public PongImpl(PingRI pingRI, Ball ball) throws RemoteException {
        this.pingRI = pingRI;
        this.ball = ball;
        this.exportObject();
    }

    @Override
    public void pong(Ball b) throws RemoteException {
        System.out.println("PongImpl- starPlaying(): id" +b.getId());
        pingRI.ping(b, this);
    }

    private void exportObject() throws RemoteException{
        UnicastRemoteObject.exportObject(this, 0);
    }
    
    public void startplay() {
        try {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "startplay():... :)");
            this.pingRI.ping(ball, this);
        } catch (RemoteException ex) {
            Logger.getLogger(PongImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package edu.ufp.sd.pingpong.client;

import edu.ufp.sd.util.rmisetup.SetupContextRMI;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.ufp.sd.pingpong.server.Ball;
import edu.ufp.sd.pingpong.server.PingRI;

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
public class PongClient {

    /**
     * Context for connecting a RMI client to a RMI Servant
     */
    private SetupContextRMI contextRMI;
    /**
     * Remote interface that will hold the Servant proxy
     */
    private PingRI pingRI;
    private Ball ball;

    public static void main(String[] args) {
        if (args != null && args.length < 2) {
            System.err.println("usage: java [options] edu.ufp.sd.helloworld.server.HelloWorldClient <rmi_registry_ip> <rmi_registry_port> <service_name>");
            System.exit(-1);
        } else {
            //1. ============ Setup client RMI context ============
            PongClient hwc = new PongClient(args);
            //2. ============ Lookup service ============
            hwc.lookupService();
            //3. ============ Play with service ============
            hwc.playService();
        }
    }

    public PongClient(String args[]) {
        try {
            //List ans set args
            printArgs(args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            ball= new Ball(Integer.parseInt(args[3]));
            
            //Create a context for RMI setup
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(PongClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private PingRI lookupService() {
        try {
            //Get proxy to rmiregistry
            Registry registry = contextRMI.getRegistry();
            //Lookup service on rmiregistry and wait for calls
            if (registry != null) {
                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going to lookup service @ {0}", serviceUrl);

                //============ Get proxy to HelloWorld service ============
                pingRI = (PingRI) registry.lookup(serviceUrl);

            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return pingRI;
    }

    private void playService() {
        try {
            PongRI pongRI = new PongImpl(pingRI, ball);
            
            ((PongImpl)pongRI).startplay();
            
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Playing... :)");
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void printArgs(String args[]) {
        for (int i = 0; args != null && i < args.length; i++) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "args[{0}] = {1}", new Object[]{i, args[i]});
        }
    }
}

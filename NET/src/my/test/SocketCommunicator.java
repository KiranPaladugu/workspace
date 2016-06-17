/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package my.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketCommunicator {
    private static ExecutorService exeService= Executors.newFixedThreadPool(5);
    private ServerSocket server;
    public void init(int port){
        try {
            System.out.println("Starting server...");
            server = new ServerSocket(port);
            System.out.println("Started server...");
            exeService.submit(new ServerConnectHandler(server));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static ExecutorService getExecutorServive(){
        return exeService;
    }
    
    public static void main(String[] args){
        SocketCommunicator comm  = new SocketCommunicator();
        comm.init(830);
    }
}

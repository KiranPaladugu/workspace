/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package my.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerConnectHandler implements Runnable{

    private ServerSocket server;
    private AtomicBoolean stop = new AtomicBoolean(false);
    
    /**
     * 
     */
    public ServerConnectHandler(final ServerSocket server) {
        this.server = server;
    }
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        Thread.currentThread().setName("ServerConnectionHandler");
        if(server!=null){
            while(!stop.get()){
                System.out.println("Server accepting connections..");
                try {
                    Socket client = server.accept();
                    System.out.println("Server got connection..");
                    SocketCommunicator.getExecutorServive().submit(new ClientConnectionHandler(client));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package my.test;

import java.io.IOException;
import java.net.Socket;

public class ClientConnectionHandler implements Runnable{
    private Socket socket;
    private String name;
    private static int count;

    /**
     * 
     */
    public ClientConnectionHandler(final Socket socket) {
        this.socket = socket;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        this.name = this.getClass().getSimpleName()+""+count++;
        Thread.currentThread().setName(name);
        if(socket.isConnected()){
            try {
                System.out.println("Handling connection Requstion and Response....");
                SocketCommunicator.getExecutorServive().submit(new SocketInputstreamHandler(socket.getInputStream(), socket));
                SocketCommunicator.getExecutorServive().submit(new SocketOutputstreamHandler(socket.getOutputStream(), socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }
    
    
}

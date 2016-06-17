/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package my.test.out;

import java.io.IOException;
import java.net.Socket;

import my.test.*;

public class ServerCommunicator {
    private int port;
    private String hostName;

    /**
    * 
    */
    public ServerCommunicator(final String host, int port) {
        this.hostName = host;
        this.port = port;
        try {
            Socket socket = new Socket(hostName, port);
            SocketCommunicator.getExecutorServive().submit(new SocketInputstreamHandler(socket.getInputStream(), socket));
            SocketCommunicator.getExecutorServive().submit(new SocketOutputstreamHandler(socket.getOutputStream(), socket));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        ServerCommunicator comm = new ServerCommunicator("10.170.117.194", 830);
    }
}

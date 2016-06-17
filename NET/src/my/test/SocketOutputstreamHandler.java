/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package my.test;

import java.io.*;
import java.net.Socket;

public class SocketOutputstreamHandler implements Runnable {

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    
    private BufferedOutputStream bufferOut ;
    private Socket socket ;
    private static int count;
    private String name = this.getClass().getName();
    
    /**
     * @param bufferOut
     * @param socket
     */
    public SocketOutputstreamHandler(OutputStream outStream, Socket socket) {
        super();
        this.bufferOut = new BufferedOutputStream(outStream);
        this.socket = socket;
    }

    @Override
    public void run() {
        this.name = this.getClass().getSimpleName()+"-"+count++;
        Thread.currentThread().setName(name);
        byte[] b = "SSH-2.0-OpenSSH_6.6.1".getBytes();
        try {
            System.out.println("Writing to stream..");
            bufferOut.write(b);
            bufferOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
